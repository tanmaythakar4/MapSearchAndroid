package com.example.tanut.mapsearch.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by tanut on 10/18/2017.
 */

public class MapFragment extends BaseFragment implements OnMapReadyCallback,MapMvpView {

    public static final String TAG = "AboutFragment";

    private GoogleMap googleMap = null;
    private MapView mapView;
    private ClusterManager<MyItem> mClusterManagerLocal;
    private ClusterManager<MapItem> mClusterManager;
    private MapPresenterImpl mPresenter;
    private final String DEFAULT_SEARCH = "bofa";

    @Inject
    InputStream inputStream;

    @Inject
    ApiClient apiClient;

    @Inject
    GoogleMapWebService mGoogleMapWebService;


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
        //dagger
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

       /* // for static data
        mPresenter.getGeoPlaceData(DEFAULT_SEARCH,inputStream);*/
       mPresenter.getDataFromService(mGoogleMapWebService);


    }

    @Override
    public void removeMarkers() {

    }

    @Override
    public void showMarkerAt(float latitude, float longitude) {

    }

    // clustering for local data
    @Override
    public void showMarkerClusterLocal(List<MyItem> items) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
        mClusterManagerLocal = new ClusterManager<MyItem>(getActivity(),googleMap);
        googleMap.setOnCameraIdleListener(mClusterManagerLocal);
        mClusterManagerLocal.addItems(items);
    }

    // clusternig for network data
    @Override
    public void showMarkerCluster(List<MapItem> items) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
        mClusterManager = new ClusterManager<MapItem>(getActivity(),googleMap);
        googleMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.addItems(items);
    }

    @Override
    public void showSnippet() {

    }

    private void requestData(){
        Intent intent = new Intent(getActivity(), GoogleMapWebService.class);
        startService(intent);
    }

/*    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }*/

    private void startService(Intent intent) {
    }

    //private Object mItemList;

//    private BroadcastReceiver mBroadcastReceiver = (context, intent) -> {
//        MyItem[] myitems = (MyItem[])intent.getParcelableArrayExtra(ApiClient.MY_SERVICE_PAYLOAD);
//       // mItemList = ArrayList.asList(myitems);
//        displayData();
//    };

    private void displayData() {
        //Displaying the data
    }
}
