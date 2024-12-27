package com.android.server.smartthings;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SmartThingsService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdateReceiver extends BroadcastReceiver {
        public UpdateReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            action.getClass();
            if (action.equals("android.intent.action.PACKAGE_REPLACED")
                    || action.equals("android.intent.action.PACKAGE_ADDED")) {
                SmartThingsService.this.getClass();
                SmartThingsService.backgroundWhitelist(context);
            }
        }
    }

    public SmartThingsService(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.oneconnect", 0);
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.setPriority(1000);
        context.registerReceiver(new UpdateReceiver(), intentFilter);
        backgroundWhitelist(context);
    }

    public static void backgroundWhitelist(Context context) {
        try {
            int i =
                    context.getPackageManager()
                            .getApplicationInfo("com.samsung.android.oneconnect", 0)
                            .uid;
            String num = Integer.toString(i);
            if (i >= 1000 && num != null) {
                if (hasValidSignature(context)) {
                    ActivityManager.getService().backgroundAllowlistUid(i);
                    Log.d(
                            "oneconnect_svc",
                            "smartthings service : backgroundAllowlist successfully called");
                    return;
                }
                return;
            }
            Log.e(
                    "oneconnect_svc",
                    "smartthings service : backgroundAllowlist: bad uid: "
                            + i
                            + ", uidString: "
                            + num);
        } catch (Exception e) {
            Log.e("oneconnect_svc", "smartthings service : backgroundAllowlist exception ", e);
        }
    }

    public static boolean hasValidSignature(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager
                    .getPackageInfo("android", 64)
                    .signatures[0]
                    .equals(
                            packageManager.getPackageInfo("com.samsung.android.oneconnect", 64)
                                    .signatures[0])) {
                Log.d(
                        "oneconnect_svc",
                        "hasValidSignature, VALID : com.samsung.android.oneconnect");
                return true;
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e("oneconnect_svc", "hasValidSignature, exception " + e.toString());
        }
        return false;
    }
}
