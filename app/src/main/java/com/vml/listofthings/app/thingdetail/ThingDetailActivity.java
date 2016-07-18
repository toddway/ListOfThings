package com.vml.listofthings.app.thingdetail;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.App;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.core.things.ThingEntity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThingDetailActivity extends BaseActivity implements ThingDetailView {

    @Inject ThingDetailPresenter presenter;
    @Bind(R.id.title) TextView titleTextView;
    @Bind(R.id.summary) TextView summaryTextView;
    @Bind(R.id.content_wrapper)
    LinearLayout contentWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        App.of(this).component().inject(this);
        presenter.attachView(this, ThingDetailUtil.id(getIntent()));
        contentWrapper.setAlpha(0);
        contentWrapper.setScaleX(.8f);
        contentWrapper.setScaleY(.8f);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void populateDetails(ThingEntity thingEntity) {
        setToolbarTitle(thingEntity.getTitle());
        titleTextView.setText(thingEntity.getTitle());
        summaryTextView.setText(thingEntity.getSummary());
        contentWrapper.animate().scaleX(1).scaleY(1).alpha(1).setStartDelay(200).start();
    }
}
