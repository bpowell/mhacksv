package edu.oakland.images.processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.oakland.images.database.OutfitDataSource;
import edu.oakland.images.models.ArticleType;
import edu.oakland.images.models.ClothingType;
import edu.oakland.images.models.Item;

/**
 * Created by brandon on 1/17/15.
 */

public class ImageUtils {
    public static final double PERCENT_TO_CUT = 0.10;
    public static final int SHRINK_FACTOR = 2;
    public static final int NUMBER_OF_COLORS = 5;

    public static ArrayList<ColorInfo> getTopColors(String placeholder) {
        HashMap<Integer, Integer> usedColors = new HashMap<Integer, Integer>();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = SHRINK_FACTOR;

        Bitmap image = BitmapFactory.decodeFile(placeholder, opts);
        int width = image.getWidth();
        int height = image.getHeight();

        int width_start = (int) (width * PERCENT_TO_CUT);
        int height_start = (int) (height * PERCENT_TO_CUT);
        int width_end = width - width_start;
        int height_end = height - height_start;

        for(int i=width_start; i<width_end; i++) {
            for(int j=height_start; j<height_end; j++) {
                int pixel = image.getPixel(i, j);
                if (usedColors.containsKey(pixel)) {
                    int t = usedColors.get(pixel) + 1;
                    usedColors.put(pixel, t);
                } else {
                    usedColors.put(pixel, 1);
                }
            }
        }

        Iterator it = usedColors.entrySet().iterator();
        ArrayList<ColorInfo> colors = new ArrayList<ColorInfo>();
        for(int i=0; i<NUMBER_OF_COLORS; i++) {
            colors.add(new ColorInfo(0,0));
        }


        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pairs = (Map.Entry) it.next();
            int size = pairs.getValue();

            if (size > colors.get(0).size) {
                colors.set(0, new ColorInfo(pairs.getKey(), size));
            }

            for(int i=1; i<colors.size(); i++) {
                if (size > colors.get(i).size && size < colors.get(i-1).size) {
                    colors.set(i, new ColorInfo(pairs.getKey(), size));
                }
            }
        }

        return colors;
    }

    public static void saveImage(String path, String name, ClothingType clothingType, ArticleType articleType, ArrayList<ColorInfo> colors, Context context) {
        Item item = new Item(name, clothingType, articleType, colors, path);
        OutfitDataSource dataSource = new OutfitDataSource(context);
        try {
            dataSource.open();
            dataSource.insertItem(item);
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ColorInfo similarColor(String color) {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://api.wolframalpha.com/v2/query?appid=PG9HJR-24H87X8K9Q&output=xml&input=%23" + color);

        String response = "";
        try {
            response = getResponse(client, httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int start = response.indexOf("red  |  green  |  blue");
        start = response.indexOf("#", start);
        String data = response.substring(start, start+7);

        ColorInfo colorInfo = new ColorInfo(Color.parseColor(data), 0);
        return colorInfo;
    }

    public static ArrayList<ColorInfo> triad(String color) {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://api.wolframalpha.com/v2/query?appid=PG9HJR-24H87X8K9Q&output=xml&input=triad%20%23" + color);

        String response = "";
        try {
            response = getResponse(client, httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int end = response.lastIndexOf("</plaintext>");
        int start = response.lastIndexOf("<plaintext>");
        String data = response.substring(response.indexOf("HSV", start), end);

        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        start = data.indexOf("hue") + 4;
        end = data.indexOf(" ", data.indexOf(" ", start)+1);
        hsv1[0] = Float.parseFloat(data.substring(start, end)) * 360;

        start = data.indexOf("hue", end) + 4;
        end = data.indexOf(" ", data.indexOf(" ", start)+1);
        hsv2[0] = Float.parseFloat(data.substring(start, end)) * 360;

        start = data.indexOf("saturation") + 11;
        end = data.indexOf(" ", data.indexOf(" ", start)+1);
        hsv1[1] = Float.parseFloat(data.substring(start, end));

        start = data.indexOf("saturation", end) + 11;
        end = data.indexOf(" ", data.indexOf(" ", start)+1);
        hsv2[1] = Float.parseFloat(data.substring(start, end));

        start = data.indexOf("value") + 6;
        end = data.indexOf(" ", data.indexOf(" ", start)+1);
        hsv1[2] = Float.parseFloat(data.substring(start, end));

        start = data.indexOf("value", end) + 6;
        hsv2[2] = Float.parseFloat(data.substring(start));

        ArrayList<ColorInfo> colors = new ArrayList<>();
        colors.add(new ColorInfo(Color.HSVToColor(hsv1), 0));
        colors.add(new ColorInfo(Color.HSVToColor(hsv2), 0));
        return colors;
    }

    private static String getResponse(HttpClient client, HttpGet httpGet) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpResponse response = client.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } else {
            throw new Exception("Not a 200 status code");
        }

        return builder.toString();
    }
}
