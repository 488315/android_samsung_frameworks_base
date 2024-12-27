package com.android.server.samsungnotes;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SamsungNotesService extends Binder {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdateReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action;
            if (intent == null || (action = intent.getAction()) == null) {
                return;
            }
            if (action.equals("android.intent.action.PACKAGE_REPLACED")
                    || action.equals("android.intent.action.PACKAGE_ADDED")) {
                SamsungNotesService.backgroundAllowlist(context);
            }
        }
    }

    public SamsungNotesService(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.app.notes", 0);
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.setPriority(1000);
        context.registerReceiver(new UpdateReceiver(), intentFilter);
        backgroundAllowlist(context);
    }

    public static void backgroundAllowlist(Context context) {
        try {
            int i =
                    context.getPackageManager()
                            .getApplicationInfo("com.samsung.android.app.notes", 0)
                            .uid;
            String num = Integer.toString(i);
            if (i >= 1000 && num != null) {
                if (hasValidSignature(context)) {
                    ActivityManager.getService().backgroundAllowlistUid(i);
                    Log.d("SamsungNotesService", "backgroundAllowlist successfully called");
                    return;
                }
                return;
            }
            Log.e(
                    "SamsungNotesService",
                    "backgroundAllowlist: bad uid: " + i + ", uidString: " + num);
        } catch (Exception e) {
            Log.e("SamsungNotesService", "backgroundAllowlist exception ", e);
        }
    }

    public static boolean hasValidSignature(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager
                    .getPackageInfo("android", 64)
                    .signatures[0]
                    .equals(
                            packageManager.getPackageInfo("com.samsung.android.app.notes", 64)
                                    .signatures[0])) {
                Log.d(
                        "SamsungNotesService",
                        "hasValidSignature, VALID : com.samsung.android.app.notes");
                return true;
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e("SamsungNotesService", "hasValidSignature, exception " + e.toString());
        }
        return false;
    }
}
