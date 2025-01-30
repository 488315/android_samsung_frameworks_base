package com.android.systemui.slimindicator;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
