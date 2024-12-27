package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConfigurationState {
    public final ConfigurationController configurationController;
    public final Context context;
    public final LayoutInflater layoutInflater;

    public ConfigurationState(ConfigurationController configurationController, Context context, LayoutInflater layoutInflater) {
        this.configurationController = configurationController;
        this.layoutInflater = layoutInflater;
    }
}
