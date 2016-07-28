package com.vml.listofthings.app.base;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;

import com.vml.listofthings.R;


public class GridRecyclerView extends RecyclerView {

    public GridRecyclerView(Context context) {
        super(context);
    }

    public GridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayoutManager(new GridLayoutManager(getContext(), 1));
        if (!isInEditMode()) {
            setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.grid_layout_animation));
            startLayoutAnimation();
        }
    }

    public void setSpanCount(int count) {
        ((GridLayoutManager) getLayoutManager()).setSpanCount(count);
        getAdapter().notifyItemRangeChanged(0, getAdapter().getItemCount());
    }

    public int getSpanCount() {
        return ((GridLayoutManager) getLayoutManager()).getSpanCount();
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {

        if (getAdapter() != null && getLayoutManager() instanceof GridLayoutManager){

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            int columns = ((GridLayoutManager) getLayoutManager()).getSpanCount();

            animationParams.count = count;
            animationParams.index = index;
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;

        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }

    public boolean isItemPositionVisible(int position) {
        GridLayoutManager manager = ((GridLayoutManager) getLayoutManager());
        return position > manager.findFirstVisibleItemPosition() && position < manager.findLastVisibleItemPosition();
    }
}