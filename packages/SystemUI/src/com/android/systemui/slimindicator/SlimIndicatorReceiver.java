package com.android.systemui.slimindicator;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

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
