package com.example.tanut.mapsearch.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.data.db.network.model.MapItem;
import com.example.tanut.mapsearch.ui.base.BaseActivity;
import com.example.tanut.mapsearch.ui.map.Adapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Abhimanyu on 10/22/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private List<MapItem> mapItems;
    private Context context;
    RecyclerOnItemClickListener mItemClickListener;

    public ListAdapter(List<MapItem> items,RecyclerOnItemClickListener recyclerOnItemClickListener) {

        this.mapItems = items;
        mItemClickListener = recyclerOnItemClickListener;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item,null);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        MapItem myItem = mapItems.get(position);
        Log.e("item",myItem.getTitle());

        holder.textViewHeading.setText(myItem.getTitle());
        holder.textViewAddress.setText(myItem.getFormattedAddress());
        Picasso.with(context).load(myItem.getIcon()).into(holder.imageView);
        holder.textViewLatContent.setText((myItem.getLat().toString()));
        holder.textViewLongContent.setText((myItem.getLng().toString()));
        holder.textViewRating.setText(myItem.getTitle());
    }

    @Override
    public int getItemCount() {

        return mapItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textViewHeading,textViewLat,textViewLatContent,textViewLong,textViewLongContent,textViewAddress,textViewRating;
        public Context mContext;


        public ListViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            //itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
            textViewLat = (TextView)itemView.findViewById(R.id.textViewLat);
            textViewLatContent = (TextView)itemView.findViewById(R.id.textViewLatContent);
            textViewLong = (TextView)itemView.findViewById(R.id.textViewLong);
            textViewLongContent = (TextView)itemView.findViewById(R.id.textViewLongContent);
            textViewAddress = (TextView)itemView.findViewById(R.id.textViewAddress);
            textViewRating = (TextView)itemView.findViewById(R.id.textViewRating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // Load ListDetailsFragment
            if (mItemClickListener != null)
            {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }


        }

    }
}


