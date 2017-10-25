package com.example.tanut.mapsearch.data.db.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.tanut.mapsearch.data.db.network.model.MapItem;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.tag;
import static android.R.id.list;

/**
 * Created by tanut on 10/24/2017.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();

        //realm.clear(MapItem.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<MapItem> getBooks() {

        return realm.where(MapItem.class).findAll();
    }
/*
    //query a single item with the given id
    public RealmResults<MapItem> getBooks(String tag) {

        return realm.where(MapItem.class).equalTo("tag", tag).findAll();
    }*/


    //query a single item with the given id
    public ArrayList<MapItem> getBooks(String tag) {

        ArrayList<MapItem> items = new ArrayList<>();
        RealmResults<MapItem> results = realm.where(MapItem.class).equalTo("tag", tag).findAll();

        items.addAll(realm.copyFromRealm(results));
        return  items;
    }
    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.isEmpty();
        //return !realm.allObjects(MapItem.class).isEmpty();
    }

//    //query example
//    public RealmResults<MapItem> queryedBooks() {
//
//        return realm.where(MapItem.class)
//                .contains("tag", "BBVA")
//                .or()
//                .contains("name", "Realm")
//                .findAll();
//
//    }
//



}
