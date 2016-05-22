package com.vml.listofthings.app.settings;

import com.vml.listofthings.app.base.BasePresenter;
import com.vml.listofthings.core.environment.Environment;
import com.vml.listofthings.core.environment.GetEnvironmentInteractor;
import com.vml.listofthings.core.environment.GetEnvironmentsInteractor;
import com.vml.listofthings.core.environment.SetEnvironmentInteractor;

import java.util.List;

/**
 * Created by tway on 5/22/16.
 */
public class SettingsPresenter extends BasePresenter<SettingsView> {

    GetEnvironmentsInteractor getEnvironmentsInteractor;
    SetEnvironmentInteractor setEnvironmentInteractor;
    GetEnvironmentInteractor getEnvironmentInteractor;
    Environment[] environments;

    public SettingsPresenter(GetEnvironmentsInteractor getEnvironmentsInteractor, SetEnvironmentInteractor setEnvironmentInteractor, GetEnvironmentInteractor getEnvironmentInteractor) {
        this.getEnvironmentsInteractor = getEnvironmentsInteractor;
        this.setEnvironmentInteractor = setEnvironmentInteractor;
        this.getEnvironmentInteractor = getEnvironmentInteractor;
        environments = listToArray(getEnvironmentsInteractor.get());
    }

    @Override
    public void attachView(SettingsView view) {
        super.attachView(view);
        if (environments.length > 0) {
            view.showDevSettings();
            view.populateEnvironmentLabel(getEnvironmentInteractor.get().getLabel());
        } else {
            view.hideDevSettings();
        }
    }


    public void switchEnvironment(int selectedIndex) {
        if (selectedIndex == getSelectedPosition()) return;
        setEnvironmentInteractor.set(environments[selectedIndex].getKey());
        view.populateEnvironmentLabel(environments[selectedIndex].getLabel());
        view.appReset();
    }


    public int getSelectedPosition() {
        for (int i = 0; i < environments.length; i++)
            if (environments[i].getKey().equals(getEnvironmentInteractor.get().getKey()))
                return i;
        return 0;
    }

    private static Environment[] listToArray(List<Environment> environments) {
        return environments.toArray(new Environment[environments.size()]);
    }


    public String[] getEnvironmentLabels() {
        String[] labels = new String[environments.length];
        int i = 0;
        for (Environment environment : environments) {
            String label = environment.getLabel();
            if (environment.getBaseServiceUrl() != null) label += " - " + environment.getBaseServiceUrl();
            labels[i++] = label;
        }
        return labels;
    }
}
