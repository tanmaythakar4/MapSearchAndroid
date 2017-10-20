package com.example.tanut.mapsearch;

import android.app.Application;

import com.example.tanut.mapsearch.dagger.AppComponent;
import com.example.tanut.mapsearch.dagger.AppModule;
import com.example.tanut.mapsearch.dagger.DaggerAppComponent;
import com.example.tanut.mapsearch.dagger.MapModule;

/**
 * Created by tanut on 10/17/2017.
 */

public class MapSearchApp extends Application {

    // Dagger Component
    private AppComponent mapComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger Initialize
        mapComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).mapModule(new MapModule(this)).build();
        mapComponent.inject(this);

    }


    public AppComponent getMapComponent() {
        return mapComponent;
    }

}
