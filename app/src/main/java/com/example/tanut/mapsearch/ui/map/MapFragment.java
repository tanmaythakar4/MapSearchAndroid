package com.example.tanut.mapsearch.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanut.mapsearch.MapSearchApp;
import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.ui.base.BaseFragment;
import com.example.tanut.mapsearch.ui.base.MvpView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by tanut on 10/18/2017.
 */

public class MapFragment extends BaseFragment implements OnMapReadyCallback,MapMvpView {

    public static final String TAG = "AboutFragment";

    private GoogleMap googleMap = null;
    private MapView mapView;
    private ClusterManager<MyItem> mClusterManager;
    private MapPresenterImpl mPresenter;
    private final String DEFAULT_SEARCH = "bofa";

    @Inject
    InputStream inputStream;


    public static MapFragment newInstance() {
        //we can use this if we are getting argument from Avtivity
       /*
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);*/

        return new MapFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MapSearchApp) getActivity().getApplication()).getMapComponent().inject(this);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        mPresenter = new MapPresenterImpl(this,new MyItemReader());
        mPresenter.getGeoPlaceData(DEFAULT_SEARCH,inputStream);
    }

    @Override
    public void removeMarkers() {

    }

    @Override
    public void showMarkerAt(float latitude, float longitude) {

    }

    @Override
    public void showMarkerCluster(List<MyItem> items) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
        mClusterManager = new ClusterManager<MyItem>(getActivity(),googleMap);
        googleMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.addItems(items);
    }

    @Override
    public void showSnippet() {

    }
}
