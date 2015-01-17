package edu.oakland.images.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.androidannotations.annotations.EFragment;

import edu.oakland.images.R;
import edu.oakland.images.adapters.OutfitGridAdapter;

/**
 * Created by steven on 1/17/15.
 */
@EFragment
public class WhatToWearFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_what_to_wear, container, false);

        setUpGridView(v);
        setUpFab();

        return v;
    }

    private void setUpFab() {

    }

    private void setUpGridView(View v) {
        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        gridView.setAdapter(new OutfitGridAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SLKJA:SLKJ", "" + position);
            }
        });
    }

}
