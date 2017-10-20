package com.example.tanut.mapsearch.dagger;

import android.content.Context;

import com.example.tanut.mapsearch.R;
import com.google.android.gms.maps.MapView;

import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanut on 10/18/2017.
 */

@Module
public class MapModule {

    private Context mContext;

    public MapModule(Context mContext) {
        this.mContext = mContext;
    }


    @Provides
    @Singleton
    InputStream getInputStream() {
        return mContext.getResources().openRawResource(R.raw.radar_search);
    }
}
