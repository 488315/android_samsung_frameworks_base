package com.samsung.android.privacydashboard;

import android.app.AppOpsManager;
import android.content.Context;

/* loaded from: classes6.dex */
public class PermissionAccessInformationRequester {
    private static final String VERSION = "1.0.0";

    public static String getVersion() {
        return "1.0.0";
    }

    public static void request(Context context) throws Exception {
        ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).requestPermissionAccessInformation();
    }
}
