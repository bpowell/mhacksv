package edu.oakland.images.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.oakland.images.R;

/**
 * Created by steven on 1/18/15.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;

    private String[] listItemTitles;
    private int[] listItemIcons;

    public NavDrawerListAdapter(Context context, String[] listItemTitles, int[] listItemIcons){
        this.context = context;
        this.listItemTitles = listItemTitles;
        this.listItemIcons = listItemIcons;
    }

    @Override
    public int getCount() {
        return listItemTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return listItemTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        // super haxorz
        switch (position) {
            case 0:
                imgIcon.setImageDrawable(context.getDrawable(R.drawable.ic_tshirt_crew_grey600_24dp));
                break;
            case 1:
                imgIcon.setImageDrawable(context.getDrawable(R.drawable.ic_hanger_grey600_24dp));
                break;
            case 2:
                imgIcon.setImageDrawable(context.getDrawable(R.drawable.ic_shopping_grey600_24dp));
        }

        txtTitle.setText(listItemTitles[position]);

        return convertView;
    }

}
