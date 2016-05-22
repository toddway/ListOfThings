package com.vml.listofthings.app.settings;

import com.vml.listofthings.app.base.BaseView;

/**
 * Created by tway on 5/22/16.
 */
public interface SettingsView extends BaseView {
    void populateEnvironmentLabel(String label);
    void appReset();
    void showDevSettings();
    void hideDevSettings();
}
