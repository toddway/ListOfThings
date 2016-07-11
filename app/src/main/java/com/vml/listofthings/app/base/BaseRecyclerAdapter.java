package com.vml.listofthings.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tway on 5/22/16.
 */
public abstract class BaseRecyclerAdapter<V>  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<V> items = new ArrayList<>();
    int selectedPosition = -1;

    public BaseRecyclerAdapter(List<V> items, RecyclerView recyclerView) {
        if (items != null) setItems(items);
        if (recyclerView != null) {
            recyclerView.setAdapter(this);
        }

    }

    public void removeItem(int position) {
        V item = getItem(position);
        items.remove(item);
        notifyItemRemoved(position);
    }

    public void removeItem(V item) {
        removeItem(findAdapterPosition(item));
    }

    public int findAdapterPosition(V item) {
        return items.indexOf(item);
    }

    public abstract View createItemView(ViewGroup parent);
    public abstract void populateItemView(View itemView, V item, int adapterPosition);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(createItemView(parent)){};
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    public void notifyItemClicked(int clickedPosition) {
        int oldPosition = selectedPosition;
        selectedPosition = clickedPosition;
        notifyItemChanged(oldPosition);
        notifyItemChanged(selectedPosition);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        populateItemView(holder.itemView, getItem(position), holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<V> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public V getItem(int position) {
        return items.get(position);
    }

    public void addItem(V model, int position) {
        items.add(position, model);
        notifyItemInserted(position);
    }
}
