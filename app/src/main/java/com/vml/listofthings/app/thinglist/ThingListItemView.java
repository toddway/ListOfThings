package com.vml.listofthings.app.thinglist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vml.listofthings.R;
import com.vml.listofthings.core.things.ThingEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tway on 5/10/16.
 */
public class ThingListItemView extends LinearLayout {

    @Bind(R.id.summary) TextView summaryTextView;
    @Bind(R.id.title) TextView titleTextView;

    public ThingListItemView(Context context) {
        super(context);
    }

    public ThingListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThingListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public static ThingListItemView inflate(ViewGroup rootView, boolean attachToRoot) {
        return of(LayoutInflater.from(rootView.getContext()).inflate(R.layout.thing_list_item, rootView, attachToRoot));
    }

    public static ThingListItemView of (View view) {return (ThingListItemView) view; }

    public void populate(ThingEntity thingEntity) {
        titleTextView.setText(thingEntity.getTitle());
        summaryTextView.setText(thingEntity.getSummary());
    }
}
