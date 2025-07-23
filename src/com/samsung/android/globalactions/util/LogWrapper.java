package com.samsung.android.globalactions.util;

import android.util.Log;
import com.samsung.android.emergencymode.Elog;

/* loaded from: classes6.dex */
public class LogWrapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "[SamsungGlobalActions]";
    private String mPackageTag;

    public void logDebug(String str, String str2) {
    }

    public void setPackageTag(String str) {
        this.mPackageTag = str;
    }

    public void v(String str, String str2) {
        Log.v(this.mPackageTag + TAG + str, str2);
    }

    public void i(String str, String str2) {
        Log.i(this.mPackageTag + TAG + str, str2);
    }

    public void e(String str, String str2) {
        Log.e(this.mPackageTag + TAG + str, str2);
    }

    public void elog(String str, String str2) {
        Elog.d(this.mPackageTag + TAG + str, str2);
    }
}
