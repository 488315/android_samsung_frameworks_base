package com.android.server.am;

import android.content.ComponentName;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HostingRecord {
    public final String mAction;
    public final String mDefiningPackageName;
    public final String mDefiningProcessName;
    public final int mDefiningUid;
    public final String mHostingName;
    public final String mHostingType;
    public final int mHostingZygote;
    public final boolean mIsTopApp;
    public final String mTriggerType;

    public HostingRecord(ComponentName componentName) {
        this("content provider", componentName.toShortString(), null, null, "unknown", -1);
    }

    public HostingRecord(ComponentName componentName, String str, boolean z) {
        this(str, componentName.toShortString(), 0, null, -1, z, null, null, "unknown");
    }

    public HostingRecord(String str) {
        this(str, null, 0, null, -1, false, null, null, "unknown");
    }

    public HostingRecord(String str, String str2) {
        this(str, str2, 0, null, -1, false, null, null, "unknown");
    }

    public HostingRecord(
            String str,
            String str2,
            int i,
            String str3,
            int i2,
            boolean z,
            String str4,
            String str5,
            String str6) {
        this.mHostingType = str;
        this.mHostingName = str2;
        this.mHostingZygote = i;
        this.mDefiningPackageName = str3;
        this.mDefiningUid = i2;
        this.mIsTopApp = z;
        this.mDefiningProcessName = str4;
        this.mAction = str5;
        this.mTriggerType = str6;
    }

    public HostingRecord(String str, String str2, String str3, String str4, String str5, int i) {
        this.mHostingType = str;
        this.mHostingName = str2;
        this.mHostingZygote = 0;
        this.mDefiningPackageName = str3;
        this.mDefiningUid = i;
        this.mIsTopApp = false;
        this.mDefiningProcessName = str4;
        this.mAction = null;
        this.mTriggerType = str5;
    }

    public final String toStringForTracker() {
        return hashCode() + "/" + this.mHostingName + "/" + this.mAction;
    }
}
