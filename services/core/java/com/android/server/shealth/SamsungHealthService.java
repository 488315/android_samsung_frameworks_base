package com.android.server.shealth;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.os.Binder;
import android.util.Log;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class SamsungHealthService extends Binder {
    public static final List HEALTH_KEY_LIST = Arrays.asList("14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a", "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6");

    public SamsungHealthService(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.sec.android.app.shealth", 0);
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.setPriority(1000);
        context.registerReceiver(new UpdateReceiver(), intentFilter);
        backgroundAllowlist(context);
    }

    public class UpdateReceiver extends BroadcastReceiver {
        public UpdateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent == null || (action = intent.getAction()) == null) {
                return;
            }
            if (action.equals("android.intent.action.PACKAGE_REPLACED") || action.equals("android.intent.action.PACKAGE_ADDED")) {
                SamsungHealthService.backgroundAllowlist(context);
            }
        }
    }

    public static void backgroundAllowlist(Context context) {
        try {
            int i = context.getPackageManager().getApplicationInfo("com.sec.android.app.shealth", 0).uid;
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
            Log.e("SamsungHealthService", "backgroundAllowlist: bad uid: " + i + ", uidString: " + num);
        } catch (Exception e) {
            Log.e("SamsungHealthService", "backgroundAllowlist exception ", e);
        }
    }

    public static boolean hasValidSignature(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            for (Signature signature : context.getPackageManager().getPackageInfo("com.sec.android.app.shealth", 64).signatures) {
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest.digest(signature.toCharsString().getBytes())) {
                    int i = (b >> 4) & 15;
                    sb.append((char) (i >= 10 ? (i + 97) - 10 : i + 48));
                    int i2 = b & 15;
                    sb.append((char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48));
                }
                arrayList.add(sb.toString());
            }
        } catch (Exception e) {
            Log.e("SamsungHealthService", "hasValidSignature : " + e);
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
