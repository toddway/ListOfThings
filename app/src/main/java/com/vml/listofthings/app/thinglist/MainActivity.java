package com.vml.listofthings.app.thinglist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.AppComponent;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements ThingListView {

    @Inject ThingListPresenter presenter;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    ThingListRecyclerAdapter recyclerAdapter = new ThingListRecyclerAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHomeAsUpEnabled(false);
        AppComponent.Builder.get(this).inject(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.attachView(this);
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
}
