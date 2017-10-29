package com.example.tanut.mapsearch.ui.main;

import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItemRepository;
import com.example.tanut.mapsearch.data.db.network.model.MapResult;
import com.example.tanut.mapsearch.services.NetworkError;

import java.util.List;

import rx.Subscription;

/**
 * Created by tanut on 10/22/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainMvpView view;
    private List<MyItem> items = null;
    private List<MapItem> mapItems = null;
    private MapItemRepository mapItemRepository;
    public MainPresenterImpl(MainMvpView view, MapItemRepository mapItemRepository) {
        this.view = view;
        this.mapItemRepository = mapItemRepository;

    }

    @Override
    public void getDataFromService(String place) {

        view.showLoading();
        try {
            mapItems = mapItemRepository.getMapItemRealm(place);
        }catch (Exception e){
            view.hideLoading();
            view.onError("ERRO WHILE GETING DATA FROM DATABASE");
        }

        if(mapItems.isEmpty()){
            Subscription subscription = mapItemRepository.getMapItemRetrofit(place, new MapItemRepository.GetListCallback() {
                @Override
                public void onSuccess(MapResult cityListResponse) {
                    view.hideLoading();
                    mapItems = cityListResponse.getResults();

                    if(mapItems.isEmpty()){
                        view.showMessage("No DATA FROM RETROFIT");
                    }
                    else{
                        view.manageData(mapItems);
                    }

                }

                @Override
                public void onError(NetworkError networkError) {
                    view.hideLoading();
                    view.onError("ERRO WHILE GETING DATA FROM Retrofit");
                }
            });

        }
        else{
            view.hideLoading();
            view.manageData(mapItems);
        }

    }


    // Tanmay Thakar
   /* @Override
    public void getDataFromService(GoogleMapWebService mapWebService, final String querry) {

        try {
            mapItems = database.itemModel().getItemForTag(querry);
            view.showMessage("DATA From DATABASE");
        }
        catch (Exception f){
            view.onError("ON ERROR FROM DATABASE");
        }

        if(mapItems.isEmpty()) {
            Call<MapResult> call = mapWebService.getNearbyPlaces(querry, Integer.parseInt(Utils.RADIUS), Utils.KEY);


            call.enqueue(new Callback<MapResult>() {
                @Override
                public void onResponse(Call<MapResult> call, Response<MapResult> response) {
                    if (response.isSuccessful()) {
                        mapItems = response.body().getResults();
                        for(MapItem item:mapItems){
                            item.setTag(querry);
                        }

                        if(!mapItems.isEmpty()) {
                            view.showMessage("DATA from SEVICE");

                            database.itemModel().addItemList(mapItems);
                            view.manageData(mapItems);
                        }
                        else{
                            view.showMessage("NO DATA");
                        }
                    } else {
                        Log.d("failure", "ON ERROR isFail");
                        view.onError("ON isFail");
                    }
                }


                @Override
                public void onFailure(Call<MapResult> call, Throwable t) {
                    Log.d("failure", t.getCause().getMessage());
                    view.onError("ON ERROR");
                }
            });
        }

        else{

            Log.d("LOCAL",mapItems.size()+" ");

            view.showMessage("DATA from DATABASE");
            view.manageData(mapItems);
        }
    }*/


  /*  @Override
    public void getDataFromService(GoogleMapWebService mapWebService, final String querry) {

        try {
            mapItems = realmController.getItems(querry);
            view.showMessage("DATA From DATABASE");
        }
        catch (Exception f){
            view.onError("ON ERROR FROM DATABASE");
        }

        if(mapItems.isEmpty()) {
            Call<MapResult> call = mapWebService.getNearbyPlaces(querry, Integer.parseInt(Utils.RADIUS), Utils.KEY);


            call.enqueue(new Callback<MapResult>() {
                @Override
                public void onResponse(Call<MapResult> call, Response<MapResult> response) {
                    if (response.isSuccessful()) {
                        mapItems = response.body().getResults();

                        if(!mapItems.isEmpty()) {

                            for(MapItem item:mapItems){
                                item.setTag(querry);
                                //realmController.setItem(item);
                                realm.beginTransaction();
                                realm.copyToRealm(item);
                                realm.commitTransaction();
                            }
                            view.showMessage("DATA from SEVICE");

                            view.manageData(mapItems);
                        }
                        else{
                            view.showMessage("NO DATA");
                        }
                    } else {
                        Log.d("failure", "ON ERROR isFail");
                        view.onError("ON isFail");
                    }
                }


                @Override
                public void onFailure(Call<MapResult> call, Throwable t) {
                    Log.d("failure", t.getCause().getMessage());
                    view.onError("ON ERROR");
                }
            });
        }

        else{

            Log.d("LOCAL",mapItems.size()+" ");

            view.showMessage("DATA from DATABASE");
            view.manageData(mapItems);
        }
    }*/



}
