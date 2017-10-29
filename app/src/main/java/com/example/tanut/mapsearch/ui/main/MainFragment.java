package com.example.tanut.mapsearch.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tanut.mapsearch.MapSearchApp;
import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.backend.AppDatabase;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItemRepository;
import com.example.tanut.mapsearch.data.db.realm.RealmController;
import com.example.tanut.mapsearch.services.ApiClient;
import com.example.tanut.mapsearch.services.GoogleMapWebService;
import com.example.tanut.mapsearch.ui.base.BaseFragment;
import com.example.tanut.mapsearch.ui.list.ListFragment;
import com.example.tanut.mapsearch.ui.map.MapFragment;
import com.example.tanut.mapsearch.utils.Utils;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by tanut on 10/22/2017.
 */

public class MainFragment extends BaseFragment implements MainMvpView {


    private FloatingActionButton floatingActionButton;
    private SearchView searchView;
    public static final String TAG = "MainFragment";
    private MainPresenterImpl mPresenter;
    private final String DEFAULT_SEARCH = "bofa";
    private MapFragment mapFragment = null;
    private ListFragment listFragment = null;
    private static MainFragment mainFragment;
    private List<MapItem> mapList;


    @Inject
    InputStream inputStream;

    @Inject
    ApiClient apiClient;

    @Inject
    GoogleMapWebService mGoogleMapWebService;

/*    @Inject
    AppDatabase database;*/

AppDatabase database;
    @Inject
    RealmController realmController;

    public interface onDataLoadedListener {
        public void onDataLoaded(List<MapItem> receivedData);

        public void onLocalDataLoaded(List<MyItem> receivedData);

    }

   // onDataLoadedListener onDataLoadedMapListener;
   // onDataLoadedListener onDataLoadedListListener;


    public static MainFragment newInstance() {
        //we can use this if we are getting argument from Avtivity
       /*
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);*/
        if(mainFragment == null){
            mainFragment = new MainFragment();
        }

        return mainFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dagger
        ((MapSearchApp) getActivity().getApplication()).getMapComponent().inject(this);
        MapItemRepository mapItemRepository = new MapItemRepository(realmController,mGoogleMapWebService);
        mPresenter = new MainPresenterImpl(this, mapItemRepository);
        mPresenter.getDataFromService(Utils.QUERY);

      //  onDataLoadedMapListener = mapFragment;
      //  onDataLoadedListListener = listFragment;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentByTag(MapFragment.TAG);
        listFragment = (ListFragment) getChildFragmentManager().findFragmentByTag(ListFragment.TAG);
        if(mapFragment==null){
            mapFragment = MapFragment.newInstance();
            // Load MapFragment
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, mapFragment, MapFragment.TAG).commit();
            //addToBackStack(MapFragment.TAG)

        }
        if(listFragment==null){
            listFragment = ListFragment.newInstance();
        }
        if(mapList!=null && !mapList.isEmpty()){
            manageData(mapList);
        }
        super.onViewCreated(view, savedInstanceState);


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
                .attachTo(actionButton)
                .build();


        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, mapFragment, MapFragment.TAG).addToBackStack(null).commit();

            }
        });

        searchView = (SearchView)view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.getDataFromService(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;

            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, listFragment, ListFragment.TAG).addToBackStack(null).commit();

            }
        });

    }



    @Override
    protected void setUp(View view) {

       // mPresenter.getDataFromService(mGoogleMapWebService, Utils.QUERY);

    }

    @Override
    public void manageLocalData(List<MyItem> items) {


      //  onDataLoadedMapListener.onLocalDataLoaded(items);
      //  onDataLoadedListListener.onLocalDataLoaded(items);
    }

    @Override
    public void manageData(List<MapItem> items) {
        mapList = items;
        if(mapFragment!=null){
            mapFragment.updateWithData(mapList);
        }

        if(listFragment!=null){
            listFragment.onDataLoaded(mapList);
        }
      //  onDataLoadedMapListener.onDataLoaded(items);
       // onDataLoadedListListener.onDataLoaded(items);
    }
}
