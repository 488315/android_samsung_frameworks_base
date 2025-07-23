package com.android.systemui.slimindicator;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
