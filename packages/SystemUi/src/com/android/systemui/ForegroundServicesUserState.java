package com.android.systemui;

import android.util.ArrayMap;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ForegroundServicesUserState {
    public String[] mRunning = null;
    public long mServiceStartTime = 0;
    public final ArrayMap mImportantNotifications = new ArrayMap(1);
    public final ArrayMap mStandardLayoutNotifications = new ArrayMap(1);
    public final ArrayMap mAppOps = new ArrayMap(1);

    public final String toString() {
        return "UserServices{mRunning=" + Arrays.toString(this.mRunning) + ", mServiceStartTime=" + this.mServiceStartTime + ", mImportantNotifications=" + this.mImportantNotifications + ", mStandardLayoutNotifications=" + this.mStandardLayoutNotifications + '}';
    }
}
