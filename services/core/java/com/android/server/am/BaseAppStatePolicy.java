package com.android.server.am;

import android.provider.DeviceConfig;
import com.android.server.am.BaseAppStateTracker;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseAppStatePolicy {
    public final boolean mDefaultTrackerEnabled;
    public final BaseAppStateTracker.Injector mInjector;
    public final String mKeyTrackerEnabled;
    public final BaseAppStateTracker mTracker;
    public volatile boolean mTrackerEnabled;

    public BaseAppStatePolicy(BaseAppStateTracker.Injector injector, BaseAppStateTracker baseAppStateTracker, String str, boolean z) {
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
        boolean z = DeviceConfig.getBoolean("activity_manager", this.mKeyTrackerEnabled, this.mDefaultTrackerEnabled);
        if (z != this.mTrackerEnabled) {
            this.mTrackerEnabled = z;
            onTrackerEnabled(z);
        }
    }
}
