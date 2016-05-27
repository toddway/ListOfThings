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

    int selectedPosition = -1;
    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onItemSelected(int position);
    }

    public abstract View createItemView(ViewGroup parent);
    public abstract void populateItemView(View itemView, V item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(createItemView(parent)){};
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            if (listener != null) listener.onItemSelected(selectedPosition);
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
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

    public V getItem(int position) {
        return items.get(position);
    }
}
