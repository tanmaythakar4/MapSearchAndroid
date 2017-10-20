package com.example.tanut.mapsearch.ui.map;

import android.content.Context;

import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.RuntimeRemoteException;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by tanut on 10/18/2017.
 */

public class MapPresenterImpl implements MapPresenter {

    private MapMvpView view;
    private Context mContext;
    private List<MyItem> items = null;
    private MyItemReader myItemReader;

    public MapPresenterImpl(MapMvpView view, MyItemReader myItemReader) {
        this.view = view;
        this.myItemReader = myItemReader;

    }


    @Override
    public void init(Object view) {

    }

    @Override
    public void getGeoPlaceData(String place,InputStream inputStream) {
        //Search through API

        //for example I am taking data from db

        try {
            items = myItemReader.read(inputStream);
        }
        catch (Exception e){
            view.onError("ON ERROR");
        }



        if(!items.isEmpty()){
            view.showMarkerCluster(items);
        }
        else{
            view.showMessage("NO ITEM");
        }


    }

}
