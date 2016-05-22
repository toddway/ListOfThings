package com.vml.listofthings.app.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class SmartHeaderRecyclerAdapter<V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<V> items = new ArrayList<>();
    View headerView;
    static int HEADER_TYPE = 100;
    static int ITEM_TYPE = 200;

    public abstract View createItemView(ViewGroup viewGroup);
    public abstract void populateItemView(View itemView, V item);

    @Override
    public int getItemViewType(int position) {
        return position == 0 && headerView != null ? HEADER_TYPE : ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = HEADER_TYPE == viewType ? createHeaderShim(headerView) : createItemView(viewGroup);
        return new RecyclerView.ViewHolder(v){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (HEADER_TYPE != getItemViewType(position))
            populateItemView(viewHolder.itemView, items.get(position-(headerView == null ? 0 : 1)));
    }

    @Override
    public int getItemCount() {
        return headerView != null ? items.size()+1 : items.size();
    }

    public void setItems(List<V> items) {
        this.items = items;
        if (headerView != null) headerView.setTranslationY(0);
        notifyDataSetChanged();
    }

    public void initWithQuickReturnHeader(final View headerView, final RecyclerView recyclerView) {
        this.headerView = headerView;
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (headerView != null) applyYTranslation(headerView, dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private static void applyYTranslation(View headerView, int dy) {
        float newY = headerView.getTranslationY() - dy;
        if (newY > 0) newY = 0; //anchor top of header
        else if (newY <= -headerView.getHeight()) newY = -headerView.getHeight(); //anchor bottom of header
        headerView.setTranslationY(newY);
    }

    private static View createHeaderShim(View headerView) {
        View v = new View(headerView.getContext());
        v.setLayoutParams(new ViewGroup.LayoutParams(1, headerView.getMeasuredHeight()));
        return v;
    }
}
