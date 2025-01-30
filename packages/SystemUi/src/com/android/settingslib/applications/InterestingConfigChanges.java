package com.android.settingslib.applications;

import android.content.res.Configuration;
import android.content.res.Resources;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InterestingConfigChanges {
    public static String mCachedAppIconPkg;
    public final int mFlags;
    public final Configuration mLastConfiguration;
    public int mLastDensity;

    public InterestingConfigChanges() {
        this(-2147474940);
    }

    public final boolean applyNewConfig(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        Configuration configuration2 = this.mLastConfiguration;
        int updateFrom = configuration2.updateFrom(Configuration.generateDelta(configuration2, configuration));
        if (!(this.mLastDensity != resources.getDisplayMetrics().densityDpi) && (updateFrom & this.mFlags) == 0) {
            return false;
        }
        this.mLastDensity = resources.getDisplayMetrics().densityDpi;
        return true;
    }

    public InterestingConfigChanges(int i) {
        this.mLastConfiguration = new Configuration();
        this.mFlags = i;
    }
}
