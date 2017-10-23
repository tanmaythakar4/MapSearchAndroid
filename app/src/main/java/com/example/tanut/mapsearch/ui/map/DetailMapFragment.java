package com.example.tanut.mapsearch.ui.map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by rishi on 10/22/2017.
 */

public class DetailMapFragment extends Fragment {

    public static DetailMapFragment newInstance(Marker marker) {
        //we can use this if we are getting argument from Avtivity

      /*  Bundle args = new Bundle();
        args.putParcelable("image",marker.);
        DetailMapFragment detailMapFragment = new DetailMapFragment();
        detailMapFragment.setArguments();  */

        return new DetailMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
