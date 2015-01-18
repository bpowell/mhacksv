package edu.oakland.images.activities;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import edu.oakland.images.R;

/**
 * Created by steven on 1/17/15.
 */
@EActivity(R.layout.activity_add_article)
public class AddArticleActivity extends Activity {

    @Extra
    Uri uri;

    @AfterViews
    void init() {
        Log.d(":ASJDLASKJD", uri.toString());
    }

}
