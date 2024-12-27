package com.android.server.shealth;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Binder;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SamsungHealthService extends Binder {
    public static final List HEALTH_KEY_LIST =
            Arrays.asList(
                    "14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a",
                    "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6");

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
                SamsungHealthService.backgroundAllowlist(context);
            }
        }
    }

    public static void backgroundAllowlist(Context context) {
        try {
            int i =
                    context.getPackageManager()
                            .getApplicationInfo("com.sec.android.app.shealth", 0)
                            .uid;
            String num = Integer.toString(i);
            if (i >= 1000 && num != null) {
                if (!hasValidSignature(context)) {
                    Log.e("SamsungHealthService", "backgroundAllowlist: invalied signature");
                    return;
                } else {
                    ActivityManager.getService().backgroundAllowlistUid(i);
                    Log.d("SamsungHealthService", "backgroundAllowlist successfully called");
                    return;
                }
            }
            Log.e(
                    "SamsungHealthService",
                    "backgroundAllowlist: bad uid: " + i + ", uidString: " + num);
        } catch (Exception e) {
            Log.e("SamsungHealthService", "backgroundAllowlist exception ", e);
        }
    }

    public static boolean hasValidSignature(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            for (Signature signature :
                    context.getPackageManager()
                            .getPackageInfo("com.sec.android.app.shealth", 64)
                            .signatures) {
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest.digest(signature.toCharsString().getBytes())) {
                    int i = (b >> 4) & 15;
                    sb.append((char) (i >= 10 ? i + 87 : i + 48));
                    int i2 = b & 15;
                    sb.append((char) (i2 >= 10 ? i2 + 87 : i2 + 48));
                }
                arrayList.add(sb.toString());
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "hasValidSignature : ", "SamsungHealthService");
        }
        Iterator it = HEALTH_KEY_LIST.iterator();
        while (it.hasNext()) {
            if (arrayList.contains((String) it.next())) {
                Log.i("SamsungHealthService", "key matched");
                return true;
            }
        }
        return false;
    }
}
