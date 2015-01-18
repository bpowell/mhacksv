package edu.oakland.images.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.oakland.images.R;
import edu.oakland.images.activities.AddArticleActivity_;
import edu.oakland.images.adapters.OutfitGridAdapter;

/**
 * Created by steven on 1/17/15.
 */
@EFragment
public class WhatToWearFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_IMAGE_GALLERY = 101;

    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_what_to_wear, container, false);
        setUpGridView(v);
        return v;
    }

    @Click(R.id.camera_action)
    protected void addPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            uri = getUriFromOutputFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Click(R.id.gallery_action)
    protected void addPictureFromGallery() {
        Intent getFromGalleryIntent = new Intent();
        getFromGalleryIntent.setType("image/*");
        getFromGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(getFromGalleryIntent, "Select Picture"), 101);
    }

    private Uri getUriFromOutputFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getActivity().getResources().getString(R.string.app_name));
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Toast.makeText(getActivity(), "Could not create directory...", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        return Uri.fromFile(mediaFile);
    }

    private void setUpGridView(View v) {
        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        gridView.setAdapter(new OutfitGridAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Image selection:", "" + position);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                savePictureFromCamera();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // TODO: handle user cancelling?
            }
        } else if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // TODO: handle user cancelling?
            } else {
                AddArticleActivity_
                        .intent(this)
                        .uri(data.getData())
                        .start();
            }
        }
    }

    private void savePictureFromCamera() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        getActivity().sendBroadcast(mediaScanIntent);
        AddArticleActivity_
                .intent(this)
                .uri(uri)
                .start();
    }

}
