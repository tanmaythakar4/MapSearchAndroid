package com.example.tanut.mapsearch.dagger;

import android.content.Context;

import com.example.tanut.mapsearch.MapSearchApp;
import com.example.tanut.mapsearch.data.db.realm.RealmController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanut on 10/24/2017.
 */

@Module
public class RealmModule {

    private final MapSearchApp app;
    private RealmController realmController;


    public RealmModule(MapSearchApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    RealmController getRealmController() {
        return realmController.with(app);
    }
}
