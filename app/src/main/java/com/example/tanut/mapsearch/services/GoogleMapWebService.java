package com.example.tanut.mapsearch.services;

import com.example.tanut.mapsearch.data.db.network.model.MapResult;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Abhimanyu on 10/20/2017.
 */

public interface GoogleMapWebService {

    // Google maps places Text Search API call
    @GET("api/place/textsearch/json")
    Observable<MapResult> getNearbyPlaces(@Query("query") String query, @Query("radius") int radius, @Query("key") String key);

}
