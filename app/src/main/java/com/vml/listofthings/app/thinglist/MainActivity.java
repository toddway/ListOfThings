package com.vml.listofthings.app.thinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.App;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.app.base.ItemsRecyclerAdapter;
import com.vml.listofthings.app.settings.SettingsActivity;
import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ThingListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject ThingListPresenter presenter;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    ItemsRecyclerAdapter<ThingEntity> recyclerAdapter;

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

        recyclerAdapter = new ItemsRecyclerAdapter<ThingEntity>() {
            @Override
            public View createItemView(ViewGroup viewGroup) {
                return ThingListItemView.inflate(viewGroup, false);
            }

            @Override
            public void populateItemView(View itemView, ThingEntity item) {
                ThingListItemView.of(itemView).populate(item);
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        presenter.attachView(this);
        presenter.getThingList();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void populateThings(List<ThingEntity> thingEntities) {
        recyclerAdapter.setItems(thingEntities);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
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
