package com.vml.listofthings.app.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.App;
import com.vml.listofthings.app.base.BaseActivity;
import com.vml.listofthings.app.thinglist.MainActivity;
import com.vml.listofthings.core.errors.NetworkUnavailableError;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @Bind(R.id.about_label) TextView aboutLabelTextView;
    @Bind(R.id.dev_settings_wrapper) View devSettingsWrapper;
    @Bind(R.id.backend_value) TextView backendValueTextView;
    @Inject @Named("aboutSummary") String aboutSummary;
    @Inject SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        App.of(this).component().inject(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.action_settings));
        populateAboutSummary();
        presenter.attachView(this);
    }

    @OnClick(R.id.simulate_unknown_error)
    public void simulateUnknownError() {
        handleGlobalError(new UnknownError());
    }

    @OnClick(R.id.simulate_network_unavailable)
    public void simulateNetworkUnavailableError() {
        handleGlobalError(new NetworkUnavailableError());
    }

    @Override
    public void populateEnvironmentLabel(String label) {
        backendValueTextView.setText(label);
    }

    public void populateAboutSummary() {
        aboutLabelTextView.setText(getString(R.string.app_name) + " " + aboutSummary);
    }

    @Override
    public void appReset() {
        showMessage("Switching environments...");
        App.of(this).buildComponent(this);
        App.of(this).component().inject(this); //reinject
        MainActivity.launch(this);
    }

    @Override
    public void showDevSettings() {
        devSettingsWrapper.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDevSettings() {
        devSettingsWrapper.setVisibility(View.GONE);
    }


    @OnClick(R.id.backend_wrapper)
    public void showEnvironmentDialog() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.select_dialog_singlechoice,
                presenter.getEnvironmentLabels()
        );

        new AlertDialog.Builder(this)
                .setTitle("Switch Environment")
                .setSingleChoiceItems(adapter, presenter.getSelectedPosition(), (dialog, position) -> {
                    presenter.switchEnvironment(position);
                    dialog.dismiss();
                })
                .show();
    }
}
