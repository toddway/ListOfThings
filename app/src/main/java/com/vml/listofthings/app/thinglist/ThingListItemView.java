package com.vml.listofthings.app.thinglist;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vml.listofthings.R;
import com.vml.listofthings.app.thingdetail.ThingDetailActivity;
import com.vml.listofthings.core.things.ThingEntity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tway on 5/10/16.
 */
public class ThingListItemView extends FrameLayout {

    @Bind(R.id.summary_tall) TextView summaryTall;
    @Bind(R.id.title_tall) TextView titleTall;
    @Bind(R.id.image_tall) ImageView imageTall;
    @Bind(R.id.summary_wide) TextView summaryWide;
    @Bind(R.id.title_wide) TextView titleWide;
    @Bind(R.id.image_wide) ImageView imageWide;
    @Bind(R.id.layout_tall) ViewGroup layoutTall;
    @Bind(R.id.layout_wide) ViewGroup layoutWide;
    ThingEntity thingEntity;

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
        this.thingEntity = thingEntity;
        titleTall.setText(thingEntity.getTitle());
        titleWide.setText(thingEntity.getTitle());
        summaryTall.setText(thingEntity.getSummary());
        summaryWide.setText(thingEntity.getSummary());
        Picasso.with(getContext()).load(thingEntity.getImageUrl()).into(imageTall);
        Picasso.with(getContext()).load(thingEntity.getImageUrl()).into(imageWide);
    }

    @OnClick(R.id.item_frame)
    public void onClick(View view) {
        boolean shareImage = layoutTall.getVisibility() == VISIBLE;
        ThingDetailActivity.launch((Activity) getContext(), thingEntity.getId(), view, shareImage);
    }

    public void setSize(boolean isTall) {
        if (isTall) {
            layoutTall.setVisibility(VISIBLE);
            layoutWide.setVisibility(GONE);
        } else {
            layoutTall.setVisibility(GONE);
            layoutWide.setVisibility(VISIBLE);
        }
    }
}
