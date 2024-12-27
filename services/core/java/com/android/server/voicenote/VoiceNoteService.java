package com.android.server.voicenote;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VoiceNoteService extends Binder {

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
                VoiceNoteService.backgroundAllowlist(context);
            }
        }
    }

    public static void backgroundAllowlist(Context context) {
        try {
            int i =
                    context.getPackageManager()
                            .getApplicationInfo("com.sec.android.app.voicenote", 0)
                            .uid;
            String num = Integer.toString(i);
            if (i >= 1000 && num != null) {
                if (hasValidSignature(context)) {
                    ActivityManager.getService().backgroundAllowlistUid(i);
                    Log.d("VoiceNoteService", "backgroundAllowlist successfully called");
                    return;
                }
                return;
            }
            Log.e("VoiceNoteService", "backgroundAllowlist: bad uid: " + i + ", uidString: " + num);
        } catch (Exception e) {
            Log.e("VoiceNoteService", "backgroundAllowlist exception ", e);
        }
    }

    public static boolean hasValidSignature(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager
                    .getPackageInfo("android", 64)
                    .signatures[0]
                    .equals(
                            packageManager.getPackageInfo("com.sec.android.app.voicenote", 64)
                                    .signatures[0])) {
                Log.d(
                        "VoiceNoteService",
                        "hasValidSignature, VALID : com.sec.android.app.voicenote");
                return true;
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e("VoiceNoteService", "hasValidSignature, exception " + e.toString());
        }
        return false;
    }
}
