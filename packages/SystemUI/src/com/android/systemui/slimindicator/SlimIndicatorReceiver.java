package com.android.systemui.slimindicator;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SlimIndicatorReceiver extends BroadcastReceiver {
    public IntentFilter mFilter;
    public final SlimIndicatorSettingsBackUpManager mSettingsBackUpManager;

    public SlimIndicatorReceiver(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager) {
        this.mSettingsBackUpManager = slimIndicatorSettingsBackUpManager;
        setFilter();
    }

    public abstract void register();

    public abstract void setFilter();

    public abstract void unregister();
}
