package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.statusbar.policy.ConfigurationController;

public final class ConfigurationState {
    public final ConfigurationController configurationController;
    public final Context context;
    public final LayoutInflater layoutInflater;

    public ConfigurationState(ConfigurationController configurationController, Context context, LayoutInflater layoutInflater) {
        this.configurationController = configurationController;
        this.layoutInflater = layoutInflater;
    }
}
