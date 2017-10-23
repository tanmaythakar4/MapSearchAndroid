package com.example.tanut.mapsearch.ui.main;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import static android.R.id.button1;
import static android.R.id.button2;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;

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
                .replace(R.id.container, mapFragment, MapFragment.TAG).addToBackStack(null).commit();

        //floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);



        ImageView icon = new ImageView(getActivity()); // Create an icon
        icon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_action_add));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(icon)
                .build();


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
// repeat many times:
        ImageView mapIcon = new ImageView(getActivity());
        ImageView listIcon = new ImageView(getActivity());
        mapIcon.setImageDrawable(getActivity().getDrawable(R.drawable.google));
        listIcon.setImageDrawable(getActivity().getDrawable(R.drawable.ic_apps));

        final SubActionButton mapButton = itemBuilder.setContentView(mapIcon).build();
        SubActionButton listButton = itemBuilder.setContentView(listIcon).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(mapButton)
                .addSubActionView(listButton)
                // ...
                .attachTo(actionButton)
                .build();
     //    M

        mapButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Log.i(TAG, "Found fragment: " + fm.getBackStackEntryAt(entry).getName());
                }

                MapFragment mapFrag = (MapFragment) getChildFragmentManager().findFragmentByTag(MapFragment.TAG);
                if(mapFrag != null){
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container,mapFrag, MapFragment.TAG).commit();
                }
                else {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, mapFragment, MapFragment.TAG).addToBackStack(null).commit();
                }
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Log.i(TAG, "Found fragment: " + fm.getBackStackEntryAt(entry).getName());
                }

                ListFragment listFrag = (ListFragment) getChildFragmentManager().findFragmentByTag(ListFragment.TAG);
                if(listFrag != null){
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, listFrag, ListFragment.TAG).commit();
                }
                else {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, listFragment, ListFragment.TAG).addToBackStack(null).commit();
                }
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
    public void showMessage(String message) {
        Log.d(TAG,message);
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
