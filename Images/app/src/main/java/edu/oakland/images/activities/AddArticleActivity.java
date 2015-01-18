package edu.oakland.images.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    @ViewById(R.id.article_type)
    Spinner clothingArticleSpinner;

    @ViewById(R.id.clothing_type)
    Spinner clothingTypeSpinner;

    @Extra
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = uri.toString().replaceFirst("file:", "");
        ArrayList<ColorInfo> colors = ImageUtils.getTopColors(path);
        ImageUtils.saveImage(path, "test", ClothingType.ACCESSORIES, ArticleType.BELTS, colors, getApplicationContext());
    }

    @AfterViews
    void init() {
        setUpSpinners();
    }

    private void setUpSpinners() {
        ArrayAdapter<CharSequence> nameAdapter = ArrayAdapter.createFromResource(this,
                R.array.article_types, android.R.layout.simple_spinner_dropdown_item);
        clothingArticleSpinner.setAdapter(nameAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothing_types, android.R.layout.simple_spinner_dropdown_item);
        clothingTypeSpinner.setAdapter(typeAdapter);
    }
}

