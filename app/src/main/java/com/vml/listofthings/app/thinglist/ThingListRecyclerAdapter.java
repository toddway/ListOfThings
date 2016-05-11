package com.vml.listofthings.app.thinglist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

/**
 * Created by tway on 5/10/16.
 */
public class ThingListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ThingEntity> items;

    public void setItems(List<ThingEntity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(ThingListItemView.inflate(parent, false)) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ThingListItemView.of(holder.itemView).populate(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
