package com.example.tanut.mapsearch.ui.map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tanut.mapsearch.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by rishi on 10/22/2017.
 */

public class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInflater mInflater;

    public CustomInfoViewAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        final View popup = mInflater.inflate(R.layout.info_window_layout, null);

        ((TextView) popup.findViewById(R.id.NameTv)).setText(marker.getTitle());

        ((TextView) popup.findViewById(R.id.locationGeo)).setText(marker.getPosition().toString());

        ((TextView) popup.findViewById(R.id.locationAdd)).setText(marker.getSnippet());

        return popup;
    }
}
