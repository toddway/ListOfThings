package com.vml.listofthings.app.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends BaseView> {
    protected V view;
    protected CompositeSubscription subscriptions = new CompositeSubscription();

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
        this.subscriptions.unsubscribe();
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }
}