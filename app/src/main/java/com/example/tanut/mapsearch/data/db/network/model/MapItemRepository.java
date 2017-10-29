package com.example.tanut.mapsearch.data.db.network.model;

import com.example.tanut.mapsearch.data.db.realm.RealmController;
import com.example.tanut.mapsearch.services.GoogleMapWebService;
import com.example.tanut.mapsearch.services.NetworkError;
import com.example.tanut.mapsearch.utils.Utils;


import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by tanut on 10/28/2017.
 */

public class MapItemRepository {

    private RealmController realmController;
    private Realm realm;
    private GoogleMapWebService mapWebService;
    private List<MapItem> mapItems;

    public MapItemRepository(RealmController realmController,GoogleMapWebService mapWebService) {
        this.realmController = realmController;
        this.mapWebService = mapWebService;
        this.realm = realmController.getRealm();
    }


    public List<MapItem> getMapItemRealm(String querry){

        try {
            mapItems = realmController.getItems(querry);
        }
        catch (Exception f){
            new RuntimeException(f);
        }

      return mapItems;
    }


    public Subscription getMapItemRetrofit(final String querry, final GetListCallback callback){

        return mapWebService.getNearbyPlaces(querry,Integer.parseInt(Utils.RADIUS), Utils.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MapResult>>() {
                    @Override
                    public Observable<? extends MapResult> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MapResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MapResult mapListResponse) {
                        for(MapItem item:mapListResponse.getResults()){
                            item.setTag(querry);
                            realmController.setItem(item);
                        }
                        callback.onSuccess(mapListResponse);

                    }
                });

    }




    public interface GetListCallback{
        void onSuccess(MapResult cityListResponse);

        void onError(NetworkError networkError);
    }
}
