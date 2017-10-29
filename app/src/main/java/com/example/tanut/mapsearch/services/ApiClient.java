package com.example.tanut.mapsearch.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.tanut.mapsearch.BuildConfig;
import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abhimanyu on 10/20/2017.
 */

public class ApiClient {

    public static final String TAG = "ApiClient";
    public static final String MY_SERVICE_MESSAGE ="myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD ="myPayloadMessage";

    private GoogleMapWebService webService = null;
    private Context context;
    private Retrofit retrofit;

    public ApiClient(Context context) {
        this.context = context;
    }

    public GoogleMapWebService getMapService() {

        webService = getRetrofit().create(GoogleMapWebService.class);
        return webService;


    }

    public Retrofit getRetrofit(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(MapItem.class, new MapItemDeserializer());
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();


        String BASE_URL ="";
        String FEED ="";
         retrofit = new Retrofit.Builder().client(client.build())
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

}


class MapItemDeserializer implements JsonDeserializer<MapItem> {

    @Override
    public MapItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
         final MapItem.ItemBuilder item = MapItem.builder();

        if(jsonObject.has("formatted_address")){
            final String formattedAddress = jsonObject.get("formatted_address").getAsString();
            item.setAdress(formattedAddress);
        }
        if(jsonObject.has("icon")){
            final String icon = jsonObject.get("icon").getAsString();
            item.setIcon(icon);
        }

        if(jsonObject.has("name")){
            final String name = jsonObject.get("name").getAsString();
            item.setName(name);
        }
        if(jsonObject.has("geometry")){
            final JsonObject geometry = jsonObject.get("geometry").getAsJsonObject();
            final JsonObject location = geometry.get("location").getAsJsonObject();
            final Double lat = location.get("lat").getAsDouble();
            final Double lng = location.get("lng").getAsDouble();
            item.setLatitude(lat);
            item.setLongitude(lng);
        }


        return item.build();
    }
}
