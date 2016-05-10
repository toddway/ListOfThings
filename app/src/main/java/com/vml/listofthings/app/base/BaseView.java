package com.vml.listofthings.app.base;

public interface BaseView {
    void handleGlobalError(Throwable e);

    void hideKeyboard();

    void showProgressDialog(String message);

    void hideProgressDialog();
}
