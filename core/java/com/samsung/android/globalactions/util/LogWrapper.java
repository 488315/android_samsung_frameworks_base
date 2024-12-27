package com.samsung.android.globalactions.util;

import android.util.Log;

import com.samsung.android.emergencymode.Elog;

/* loaded from: classes6.dex */
public class LogWrapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "[SamsungGlobalActions]";
    private String mPackageTag;

    public void setPackageTag(String packageTag) {
        this.mPackageTag = packageTag;
    }

    public void v(String tag, String msg) {
        Log.v(this.mPackageTag + TAG + tag, msg);
    }

    public void i(String tag, String msg) {
        Log.i(this.mPackageTag + TAG + tag, msg);
    }

    public void e(String tag, String msg) {
        Log.e(this.mPackageTag + TAG + tag, msg);
    }

    public void elog(String tag, String msg) {
        Elog.d(this.mPackageTag + TAG + tag, msg);
    }

    public void logDebug(String tag, String msg) {}
}
