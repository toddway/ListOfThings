package com.vml.listofthings.app.thingdetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vml.listofthings.R;
import com.vml.listofthings.app.base.App;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.app.base.TransitionHelper;
import com.vml.listofthings.core.things.ThingEntity;

import javax.inject.Inject;

import butterknife.Bind;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ThingDetailActivity extends BaseActivity implements ThingDetailView {

    @Inject ThingDetailPresenter presenter;
    @Bind(R.id.summary) TextView summaryTextView;
    @Bind(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.image_view) ImageView imageView;
    @Bind(R.id.app_bar) AppBarLayout appBarLayout;
    @Bind(R.id.scroll_view) NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        App.of(this).component().inject(this);
        presenter.attachView(this, ThingDetailActivity.id(getIntent()));

        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpStaticOverScroll(coordinatorLayout, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        decor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            int translationThreshold = 150;

            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                if (Math.abs(offset) > translationThreshold) { //passed threshold
                    if (state == IOverScrollState.STATE_BOUNCE_BACK) {
                        onBeforeBack();
                        finishAfterTransition();
                    }
                }
            }
        });
    }

    @Override
    public void onBeforeEnter(View contentView) {
        super.onBeforeEnter(contentView);
        ViewCompat.setTransitionName(findViewById(R.id.coordinator_layout), "shared_element");
        appBarLayout.setAlpha(0);
        appBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAfterEnter() {
        super.onAfterEnter();
        appBarLayout.animate().alpha(1).start();

        //TODO: this is only necessary if we're doing an overscroll reveal, remove otherwise
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("bitmap_id")) {
            BitmapDrawable b = new BitmapDrawable(getResources(), BitmapUtil.fetchBitmapFromIntent(bundle));
            findViewById(android.R.id.content).setBackground(b);
        }
    }

    @Override
    public boolean onBeforeBack() {
        appBarLayout.setVisibility(View.INVISIBLE);
        return super.onBeforeBack();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void populateDetails(ThingEntity thingEntity) {
        setToolbarTitle(thingEntity.getTitle());
        summaryTextView.setText(summaryTextView.getText());
        Picasso.with(this).load("http://lorempixel.com/400/600/").into(imageView);
    }

    public static void launch(Activity fromActivity, String id, View fromView) {
        Intent intent = new Intent(fromActivity, ThingDetailActivity.class);
        intent.putExtra("id", id);

        View backgroundView = fromActivity.findViewById(android.R.id.content);
        if (backgroundView != null) BitmapUtil.storeBitmapInIntent(BitmapUtil.createBitmap(backgroundView), intent);

        ActivityOptionsCompat options = TransitionHelper.makeOptionsCompat(
                fromActivity,
                Pair.create(fromView, "shared_element")
        );

        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());
    }

    public static String id(Intent intent) {
        return intent.getStringExtra("id");
    }
}
