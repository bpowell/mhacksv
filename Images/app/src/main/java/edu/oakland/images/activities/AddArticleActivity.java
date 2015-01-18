package edu.oakland.images.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.oakland.images.R;
import edu.oakland.images.models.ArticleType;
import edu.oakland.images.models.ClothingType;
import edu.oakland.images.processing.ColorInfo;
import edu.oakland.images.processing.ImageUtils;

/**
 * Created by steven on 1/17/15.
 */
@EActivity(R.layout.activity_add_article)
public class AddArticleActivity extends ActionBarActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Extra
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = uri.toString().replaceFirst("file:", "");
        ArrayList<ColorInfo> colors = ImageUtils.getTopColors(path);
        ImageUtils.saveImage(path, "test", ClothingType.ACCESSORIES, ArticleType.BELTS, colors, getApplicationContext());
    }
}

