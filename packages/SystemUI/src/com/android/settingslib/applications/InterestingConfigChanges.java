package com.android.settingslib.applications;

import android.content.res.Configuration;
import android.content.res.Resources;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class InterestingConfigChanges {
    public final int mFlags;
    public final Configuration mLastConfiguration;

    public InterestingConfigChanges() {
        this(-2147470844);
    }

    public final boolean applyNewConfig(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        Configuration configuration2 = this.mLastConfiguration;
        return (this.mFlags & configuration2.updateFrom(Configuration.generateDelta(configuration2, configuration))) != 0;
    }

    public InterestingConfigChanges(int i) {
        this.mLastConfiguration = new Configuration();
        this.mFlags = i;
    }
}
