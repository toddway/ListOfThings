package com.vml.listofthings.app.thinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.App;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.app.settings.SettingsActivity;
import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ThingListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject ThingListPresenter presenter;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.recycler_view) ThingListLayout thingListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.of(this).component().inject(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHomeAsUpEnabled(false);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        presenter.attachView(this);
        presenter.getThingList();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggle_view_mode: toggleViewMode(item); break;
        }
        return true;
    }

    private void toggleViewMode(MenuItem item) {
        boolean showMore = thingListLayout.getSpanCount() == 1;
        if (showMore) {
            item.setIcon(R.drawable.ic_view_stream_white_24px);
            thingListLayout.setSpanCount(3);
        } else {
            item.setIcon(R.drawable.ic_view_module_white_24px);
            thingListLayout.setSpanCount(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void populateThings(List<ThingEntity> thingEntities) {
        thingListLayout.setItems(thingEntities);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            thingListLayout.startLayoutAnimation();
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        presenter.getNewThingList();
    }

    @OnClick(R.id.fab)
    public void navigateToSettings() {
        SettingsActivity.launch(this, SettingsActivity.class);
    }

    public static void launch(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivityCompat.startActivity(fromActivity, intent, null);
    }
}
