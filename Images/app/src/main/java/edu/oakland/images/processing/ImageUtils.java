package edu.oakland.images.processing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
}
