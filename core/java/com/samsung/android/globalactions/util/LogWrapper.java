package com.samsung.android.globalactions.util;

import android.util.Log;
import com.samsung.android.emergencymode.Elog;

/* loaded from: classes5.dex */
public class LogWrapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "[SamsungGlobalActions]";
    private String mPackageTag;

    public void setPackageTag(String packageTag) {
        this.mPackageTag = packageTag;
    }

    /* renamed from: v */
    public void m266v(String tag, String msg) {
        Log.m100v(this.mPackageTag + TAG + tag, msg);
    }

    /* renamed from: i */
    public void m265i(String tag, String msg) {
        Log.m98i(this.mPackageTag + TAG + tag, msg);
    }

    /* renamed from: e */
    public void m264e(String tag, String msg) {
        Log.m96e(this.mPackageTag + TAG + tag, msg);
    }

    public void elog(String tag, String msg) {
        Elog.m254d(this.mPackageTag + TAG + tag, msg);
    }

    public void logDebug(String tag, String msg) {
    }
}
