package com.example.tanut.mapsearch;

import android.app.Application;

import com.example.tanut.mapsearch.dagger.AppComponent;
import com.example.tanut.mapsearch.dagger.AppModule;
import com.example.tanut.mapsearch.dagger.DaggerAppComponent;
import com.example.tanut.mapsearch.dagger.MapModule;
import com.example.tanut.mapsearch.dagger.RealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        mapComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).mapModule(new MapModule(this)).realmModule(new RealmModule(this)).build();
        mapComponent.inject(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }


    public AppComponent getMapComponent() {
        return mapComponent;
    }

}
