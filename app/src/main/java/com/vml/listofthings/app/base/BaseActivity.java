package com.vml.listofthings.app.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/**
 * Created by tway on 5/10/16.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    protected ProgressDialog progressDialog;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void handleGlobalError(Throwable e) {

    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
    }

    @Override
    public void showProgressDialog(String message) {
        if (progressDialog == null) progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    public static void launch(Activity fromActivity, Class launchType) {
        fromActivity.startActivity(new Intent(fromActivity, launchType));
    }
}
