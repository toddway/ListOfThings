package com.vml.listofthings.app.thingdetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ThingDetailActivity extends BaseActivity implements ThingDetailView {

    public static final String SHARED_ELEMENT = "shared_element";
    public static final String ID = "id";
    public static final String SHARED_IMAGE = "shared_image";
    @Inject ThingDetailPresenter presenter;
    @Bind(R.id.summary) TextView summaryTextView;
    @Bind(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.image_view) ImageView imageView;
    @Bind(R.id.app_bar) AppBarLayout appBarLayout;
    @Bind(R.id.scroll_view) NestedScrollView scrollView;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;

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
        initOverscroll();
    }

    @Override
    public void onBeforeEnter(View contentView) {
        ViewCompat.setTransitionName(coordinatorLayout, SHARED_ELEMENT);
        if (isSharedImage(getIntent())) ViewCompat.setTransitionName(imageView, SHARED_IMAGE);
        hideNonTransitionals();
    }

    @Override
    public void onAfterEnter() {
        showNonTransitionals();
    }

    @Override
    public boolean onBeforeBack() {
        hideNonTransitionals();
        return false;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void populateDetails(ThingEntity thingEntity) {
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(thingEntity.getTitle());
        summaryTextView.setText(summaryTextView.getText());
        Picasso.with(this).load(thingEntity.getImageUrl()).into(imageView);

        //fix weird sizing bug during shared element transition
        imageView.setLayoutParams(
                new CollapsingToolbarLayout.LayoutParams(
                        getWindow().getDecorView().getWidth(),
                        imageView.getHeight()
                )
        );
    }

    private void hideNonTransitionals() {
        scrollView.setVisibility(View.INVISIBLE);
        if (!isSharedImage(getIntent())) {
            appBarLayout.setVisibility(View.INVISIBLE);
            appBarLayout.setAlpha(0);
        }
    }

    private void showNonTransitionals() {
        scrollView.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.VISIBLE);
        if (!isSharedImage(getIntent())) appBarLayout.animate().alpha(1).start();
    }

    private void initOverscroll() {
        //this is only necessary if we're doing an overscroll
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("bitmap_id")) {
            BitmapDrawable b = new BitmapDrawable(getResources(), BitmapUtil.fetchBitmapFromIntent(bundle));
            findViewById(android.R.id.content).setBackground(b);
        }

        OverScrollDecoratorHelper
                .setUpStaticOverScroll(
                        coordinatorLayout,
                        OverScrollDecoratorHelper.ORIENTATION_VERTICAL
                )
                .setOverScrollUpdateListener(
                        (decor, state, offset) -> {
                            if (Math.abs(offset) > 150) { //passed threshold
                                if (state == IOverScrollState.STATE_BOUNCE_BACK) {
                                    onBeforeBack();
                                    finishAfterTransition();
                                }
                            }
                        }
                );
    }

    public static void launch(Activity fromActivity, String id, View fromView, boolean isSharedImage) {
        Intent intent = new Intent(fromActivity, ThingDetailActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(SHARED_IMAGE, isSharedImage);
        View backgroundView = fromActivity.findViewById(android.R.id.content);
        BitmapUtil.storeBitmapInIntent(BitmapUtil.createBitmap(backgroundView), intent);

        ActivityOptionsCompat options;
        if (isSharedImage) {
            options = TransitionHelper.makeOptionsCompat(
                    fromActivity
                    , Pair.create(fromView.findViewById(R.id.image_tall), SHARED_IMAGE)
                    , Pair.create(fromView, SHARED_ELEMENT)
            );
        } else {
            options = TransitionHelper.makeOptionsCompat(
                    fromActivity
                    , Pair.create(fromView, SHARED_ELEMENT)
            );
        }

        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());
    }

    public static String id(Intent intent) {
        return intent.getStringExtra(ID);
    }

    public static boolean isSharedImage(Intent intent) {
        return intent.getBooleanExtra(SHARED_IMAGE, false);
    }
}
