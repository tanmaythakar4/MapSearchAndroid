package com.example.tanut.mapsearch.ui.map;

import android.util.Log;

import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapResult;
import com.example.tanut.mapsearch.services.GoogleMapWebService;
import com.example.tanut.mapsearch.utils.Utils;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tanut on 10/18/2017.
 */

public class MapPresenterImpl implements MapPresenter {

    private MapMvpView view;
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

        try {
            items = myItemReader.read(inputStream);
        }
        catch (Exception f){
            view.onError("ON ERROR");
        }

        if(!items.isEmpty()){
            view.showMarkerClusterLocal(items);
        }
        else{
            view.showMessage("NO ITEM");
        }
    }

    @Override
    public void getDataFromService(GoogleMapWebService mapWebService) {


        Call<MapResult> call = mapWebService.getNearbyPlaces(Utils.QUERY,Integer.parseInt(Utils.RADIUS),Utils.KEY);

        call.enqueue(new Callback<MapResult>() {
            @Override
            public void onResponse(Call<MapResult> call, Response<MapResult> response) {
                if(response.isSuccessful()){
                    Log.e(Utils.LOGTAG1,"SUCCESS");

                    view.showMarkerCluster(response.body().getResults());
                }
                else{
                    view.onError("ON ERROR");
                }
            }
            @Override
            public void onFailure(Call<MapResult> call, Throwable t) {
                Log.e(Utils.LOGTAG2,"FAILURE");
                Log.d("failure",t.getCause().getMessage());
                view.onError("ON ERROR");
            }
        });

    }

}
