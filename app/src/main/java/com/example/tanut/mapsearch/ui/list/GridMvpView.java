package com.example.tanut.mapsearch.ui.list;

import com.example.tanut.mapsearch.data.db.model.MyItem;
import com.example.tanut.mapsearch.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhimanyu on 10/22/2017.
 */

public interface GridMvpView extends MvpView {

    public void showMarkerClusterLocal(List<MyItem> items);
}
