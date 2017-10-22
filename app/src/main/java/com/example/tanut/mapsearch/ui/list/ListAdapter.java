package com.example.tanut.mapsearch.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Abhimanyu on 10/22/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListHolder>{

    Context c;
    private String titles;
    private String descriptions;

    public ListAdapter(Context c) {
        this.c = c;
    }
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
