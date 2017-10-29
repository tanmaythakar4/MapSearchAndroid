package com.example.tanut.mapsearch.ui.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.services.ApiClient;
import com.example.tanut.mapsearch.services.GoogleMapWebService;
import com.example.tanut.mapsearch.ui.base.BaseFragment;
import com.example.tanut.mapsearch.ui.main.MainFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import static android.R.attr.fragment;

/**
 * Created by Abhimanyu on 10/22/2017.
 */

public class ListDetailsFragment extends DialogFragment {
    //implements} View.OnClickListener{

    public static final String TAG = "ListDetailsFragment";
    public ImageView imageView;
    public TextView textViewHeading, textViewLatLong, textViewAddress;
    public Button button;
    private static ListDetailsFragment fragment;
    public static final String MAPITEM_KEY = "MAPITEM";


    public static ListDetailsFragment newInstance(MapItem item) {

        //we can use this if we are getting argument from Fragment
        Bundle args = new Bundle();
        args.putParcelable(MAPITEM_KEY, item);

        if (fragment == null) {
            fragment = new ListDetailsFragment();
        }

        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textViewHeading = (TextView) view.findViewById(R.id.textViewHeading);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        textViewLatLong = (TextView) view.findViewById(R.id.textViewLatLong);

        MapItem curItem = getArguments().getParcelable(MAPITEM_KEY);
        Picasso.with(getContext()).load(curItem.getIcon()).into(imageView);
        textViewHeading.setText(curItem.getTitle());
        textViewAddress.setText(curItem.getFormattedAddress());
        textViewLatLong.setText(curItem.getLat() + "," + curItem.getLng());

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + textViewLatLong.getText());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        gmmIntentUri);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }


}
