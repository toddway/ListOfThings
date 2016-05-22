package com.vml.listofthings.app.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.vml.listofthings.R;
import com.vml.listofthings.core.errors.NetworkUnavailableError;
import com.vml.listofthings.core.errors.ServerError;

import butterknife.ButterKnife;

/**
 * Created by tway on 5/10/16.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    protected ProgressDialog progressDialog;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        ViewUtil.initToolbar(this, R.id.toolbar, -1);
        ButterKnife.bind(this);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void handleGlobalError(Throwable e) {
        if (e instanceof NetworkUnavailableError) {
            showNetworkUnavailableMessage();
        } else if (e instanceof ServerError) {
            ServerError serverError = ((ServerError) e);
            showErrorMessage(serverError.getFriendlyMessage(), e, serverError.isUnknown());
        } else {
            showErrorMessage(null, e, true);
        }
    }

    public void showNetworkUnavailableMessage() {
        showErrorMessage(getString(R.string.network_unavailable_error), null, false);
    }

    public void showMessage(String message) {
        if (message == null) return;
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        //toast.getView().setBackgroundResource(R.drawable.toast_bg);
        toast.show();
    }

    public void showErrorMessage(String message, Throwable e, boolean isLoggable) {
        message = message == null ? getString(R.string.unknown_error) : message;

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        //toast.getView().setBackgroundResource(R.drawable.toast_bg_error);
        toast.show();

        if (e != null && isLoggable) {
            e.printStackTrace();
            // HockeyLog.error(message, e);
            // Crashlytics.log(e);
        }
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

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    public void setHomeAsUpEnabled(boolean isEnabled) {
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launch(Activity fromActivity, Class launchType) {
        fromActivity.startActivity(new Intent(fromActivity, launchType));
    }

}
