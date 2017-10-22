package com.example.tanut.mapsearch.ui.list;

import com.example.tanut.mapsearch.data.db.MyItemReader;
import com.example.tanut.mapsearch.data.db.model.MyItem;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Abhimanyu on 10/21/2017.
 */

public class ListPresenterImpl implements ListPresenter {

    private GridMvpView view;
    private List<MyItem> items = null;
    private MyItemReader myItemReader;

    public ListPresenterImpl(GridMvpView view, MyItemReader myItemReader) {
        this.view = view;
        this.myItemReader = myItemReader;

    }
    @Override
    public void init(Object view) {

    }

    @Override
    public void getGeoPlaceData(String place, InputStream inputStream) {
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

}
