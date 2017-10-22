package com.example.tanut.mapsearch.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanut.mapsearch.MapSearchApp;
import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.services.ApiClient;
import com.example.tanut.mapsearch.services.GoogleMapWebService;
import com.example.tanut.mapsearch.ui.base.BaseFragment;
import com.example.tanut.mapsearch.ui.list.ListFragment;
import com.example.tanut.mapsearch.ui.map.MapFragment;
import com.example.tanut.mapsearch.ui.map.MapMvpView;
import com.example.tanut.mapsearch.ui.map.MapPresenterImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by tanut on 10/22/2017.
 */

public class MainFragment extends BaseFragment implements MainMvpView {


    private FloatingActionButton floatingActionButton;
    public static final String TAG = "MainFragment";
    private MainPresenterImpl mPresenter;
    private final String DEFAULT_SEARCH = "bofa";
    private MapFragment mapFragment = null;
    private ListFragment listFragment = null;


    @Inject
    InputStream inputStream;

    @Inject
    ApiClient apiClient;

    @Inject
    GoogleMapWebService mGoogleMapWebService;


    public interface onDataLoadedListener {
        public void onDataLoaded(List<MapItem> receivedData);

        public void onLocalDataLoaded(List<MyItem> receivedData);

    }

    onDataLoadedListener onDataLoadedMapListener;
    onDataLoadedListener onDataLoadedListListener;


    public static MainFragment newInstance() {
        //we can use this if we are getting argument from Avtivity
       /*
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);*/

        return new MainFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        //dagger
        ((MapSearchApp) getActivity().getApplication()).getMapComponent().inject(this);
        super.onViewCreated(view, savedInstanceState);

        mapFragment = MapFragment.newInstance();
        listFragment = ListFragment.newInstance();

        onDataLoadedMapListener = mapFragment;
        onDataLoadedListListener = listFragment;

        // Load MapFragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, mapFragment, MainFragment.TAG).commit();
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load ListFragment
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.container, listFragment, ListFragment.TAG).commit();
            }
        });


    }

    @Override
    protected void setUp(View view) {
        mPresenter = new MainPresenterImpl(this, new MyItemReader());

       /* // for static data
        mPresenter.getGeoPlaceData(DEFAULT_SEARCH,inputStream);*/
        mPresenter.getDataFromService(mGoogleMapWebService);

    }

    @Override
    public void manageLocalData(List<MyItem> items) {
        onDataLoadedMapListener.onLocalDataLoaded(items);
        onDataLoadedListListener.onLocalDataLoaded(items);
    }

    @Override
    public void manageData(List<MapItem> items) {
        onDataLoadedMapListener.onDataLoaded(items);
        onDataLoadedListListener.onDataLoaded(items);
    }
}
