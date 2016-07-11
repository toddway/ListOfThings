package com.vml.listofthings.app.thinglist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.BaseRecyclerAdapter;
import com.vml.listofthings.app.base.GridRecyclerView;
import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by tway on 7/11/16.
 */
public class ThingListLayout extends GridRecyclerView {
    @BindString(R.string.deleted_item) String deletedItemString;
    BaseRecyclerAdapter<ThingEntity> recyclerAdapter;

    public ThingListLayout(Context context) {
        super(context);
    }

    public ThingListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThingListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        initAdapter();
        initItemTouchHelper();
    }

    private void initAdapter() {
        recyclerAdapter = new BaseRecyclerAdapter<ThingEntity>(null, this) {
            @Override
            public View createItemView(ViewGroup viewGroup) {
                return ThingListItemView.inflate(viewGroup, false);
            }

            @Override
            public void populateItemView(View itemView, ThingEntity item, int adapterPosition) {
                ThingListItemView.of(itemView).populate(item);
            }
        };
    }

    private void initItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItemWithUndo(recyclerAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(this);
    }

    private void removeItemWithUndo(final ThingEntity thing) {
        final int position = recyclerAdapter.findAdapterPosition(thing);
        recyclerAdapter.removeItem(position);
        Snackbar.make(this,  deletedItemString.replace("LABEL", thing.getTitle()), Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItem(thing, position);
                    }
                })
                .show();
    }

    private void addItem(final ThingEntity thing, final int position) {
        if (!isItemPositionVisible(position)) scrollToPosition(position == 0 ? 0 : position-1);
        recyclerAdapter.addItem(thing, position);
    }

    public void setItems(List<ThingEntity> items) {
        recyclerAdapter.setItems(items);
    }

//    View headerView;
//    public void attachQuickReturnHeader(final View headerView) {
//        this.headerView = headerView;
//        addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (headerView != null) applyYTranslation(headerView, dy);
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//    }
//
//    private static void applyYTranslation(View headerView, int dy) {
//        float newY = headerView.getTranslationY() - dy;
//        if (newY > 0) newY = 0; //anchor top of header
//        else if (newY <= -headerView.getHeight()) newY = -headerView.getHeight(); //anchor bottom of header
//        headerView.setTranslationY(newY);
//    }
}
