package edu.oakland.images.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.oakland.images.R;
import edu.oakland.images.database.OutfitDataSource;
import edu.oakland.images.models.ArticleType;
import edu.oakland.images.models.ClothingType;
import edu.oakland.images.models.Item;
import edu.oakland.images.processing.ColorInfo;
import edu.oakland.images.processing.ImageUtils;

/**
 * Created by steven on 1/17/15.
 */
@EActivity(R.layout.activity_add_article)
public class AddArticleActivity extends ActionBarActivity {

    @ViewById(R.id.clothing_name)
    EditText clothingName;

    @ViewById(R.id.article_type)
    Spinner clothingArticleSpinner;

    @ViewById(R.id.clothing_type)
    Spinner clothingTypeSpinner;

    @Extra
    Uri uri;

    private String articleName;
    private int clothingArticle = -1;
    private int clothingType = -1;
    private boolean hasArticle = false;
    private boolean hasType = false;

    @AfterViews
    void init() {
        setUpSpinners();
    }

    private void setUpSpinners() {
        ArrayAdapter<CharSequence> nameAdapter = ArrayAdapter.createFromResource(this,
                R.array.article_types, android.R.layout.simple_spinner_dropdown_item);
        clothingArticleSpinner.setAdapter(nameAdapter);
        clothingArticleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clothingArticle = position;
                hasArticle = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothing_types, android.R.layout.simple_spinner_dropdown_item);
        clothingTypeSpinner.setAdapter(typeAdapter);
        clothingTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clothingType = position;
                hasType = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });
    }

    @Click(R.id.no_button)
    void cancel() {
        MainActivity_.intent(this).start();
    }

    @Click(R.id.yes_button)
    void accept() {
        if (!clothingName.getText().equals("") &&
                hasArticle &&
                hasType) {
            String path = uri.toString().replaceFirst("file:", "");
            ImageUtils.saveImage(
                    path,
                    clothingName.getText().toString(),
                    ClothingType.values()[clothingType],
                    ArticleType.values()[clothingArticle],
                    ImageUtils.getTopColors(path),
                    getApplicationContext());
            OutfitDataSource outfitDataSource = new OutfitDataSource(getApplicationContext());
            try {
                outfitDataSource.open();
                Item item = outfitDataSource.getItemByName(clothingName.getText().toString());
                Log.d("ALKSJDLAKSJ", item.name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Did you miss something?", Toast.LENGTH_LONG).show();
        }

    }
}

