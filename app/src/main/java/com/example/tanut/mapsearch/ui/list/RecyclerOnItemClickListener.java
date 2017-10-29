package com.example.tanut.mapsearch.ui.list;

import android.view.View;

/**
 * Created by tanut on 10/24/2017.
 */

public interface RecyclerOnItemClickListener {

    /**
     * Called when an item is clicked.
     *
     * @param childView View of the item that was clicked.
     * @param position  Position of the item that was clicked.
     */
    public void onItemClick(View childView, int position);

}
