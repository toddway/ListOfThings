package com.vml.listofthings.data;

import com.vml.listofthings.core.errors.InvalidSessionError;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by tway on 3/10/16.
 */
public class RxUtil {

    Scheduler subscribeOnScheduler;
    Scheduler observeOnScheduler;
    SessionRefresher sessionRefresher;

    public RxUtil() {
        this(Schedulers.immediate(), Schedulers.immediate());
    }

    public RxUtil(Scheduler subscribeOnScheduler, Scheduler observeOnScheduler) {
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public interface SessionRefresher {
        boolean isSessionRefreshed();
    }

    public void setSessionRefresher(SessionRefresher sessionRefresher) {
        this.sessionRefresher = sessionRefresher;
    }

    public <T> Observable.Transformer<T, T> applySessionRefresher() {
        return tObservable -> tObservable
                .retry(this::shouldRetry)
                .skipWhile(t -> t == null);
    }

    private boolean shouldRetry(Integer attempts, Throwable throwable) {
        return attempts <= 1
                && throwable instanceof InvalidSessionError
                && sessionRefresher != null
                && sessionRefresher.isSessionRefreshed();
    }


    public <T> Observable.Transformer<T, T> applyCommonSchedulers() {
        return tObservable -> tObservable
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }

    public <T> Observable.Transformer<T, T> applyAllUtils() {
        return tObservable -> tObservable
                .compose(RxUtil.this.<T>applySessionRefresher())
                .compose(RxUtil.this.<T>applyCommonSchedulers());
    }
}
