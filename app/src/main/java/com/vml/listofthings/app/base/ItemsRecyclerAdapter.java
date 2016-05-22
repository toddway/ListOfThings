package com.vml.listofthings.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tway on 5/22/16.
 */
public abstract class ItemsRecyclerAdapter<V>  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<V> items = new ArrayList<>();

    public abstract View createItemView(ViewGroup parent);
    public abstract void populateItemView(View itemView, V item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(createItemView(parent)){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        populateItemView(holder.itemView, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<V> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
