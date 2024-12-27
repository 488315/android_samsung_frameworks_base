package com.android.server.am;

import android.provider.DeviceConfig;

import java.io.PrintWriter;

public abstract class BaseAppStatePolicy {
    public final boolean mDefaultTrackerEnabled;
    public final BaseAppStateTracker.Injector mInjector;
    public final String mKeyTrackerEnabled;
    public final BaseAppStateTracker mTracker;
    public volatile boolean mTrackerEnabled;

    public BaseAppStatePolicy(
            BaseAppStateTracker.Injector injector,
            BaseAppStateTracker baseAppStateTracker,
            String str,
            boolean z) {
        this.mInjector = injector;
        this.mTracker = baseAppStateTracker;
        this.mKeyTrackerEnabled = str;
        this.mDefaultTrackerEnabled = z;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print(this.mKeyTrackerEnabled);
        printWriter.print('=');
        printWriter.println(this.mTrackerEnabled);
    }

    public int getProposedRestrictionLevel(int i, int i2, String str) {
        return 0;
    }

    public abstract void onPropertiesChanged(String str);

    public abstract void onSystemReady();

    public abstract void onTrackerEnabled(boolean z);

    public void updateTrackerEnabled() {
        boolean z =
                DeviceConfig.getBoolean(
                        "activity_manager", this.mKeyTrackerEnabled, this.mDefaultTrackerEnabled);
        if (z != this.mTrackerEnabled) {
            this.mTrackerEnabled = z;
            onTrackerEnabled(z);
        }
    }
}
