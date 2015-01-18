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

import edu.oakland.images.R;

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
    }

    @AfterViews
    void init() {
        Log.d(":ASJDLASKJD", uri.toString());
    }

}
