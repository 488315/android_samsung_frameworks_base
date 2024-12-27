package com.android.server.spay;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public abstract class Utils {
    public static final ArrayList mRegisteredUid = new ArrayList();

    public static boolean backgroundWhitelist(Context context, String str, boolean z) {
        Log.i("com.android.server.spay.Utils", "backgroundWhitelist called, fromInit: " + z);
        if (!"com.samsung.android.spay".equals(str)
                && !"com.samsung.android.spayfw".equals(str)
                && !"com.samsung.android.spaymini".equals(str)
                && !"com.samsung.android.samsungpay.gear".equals(str)
                && !"com.samsung.android.rajaampat".equals(str)) {
            Log.e(
                    "com.android.server.spay.Utils",
                    "backgroundWhitelist: bad packageName: ".concat(str));
            return false;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager
                    .getPackageInfo("android", 64)
                    .signatures[0]
                    .equals(packageManager.getPackageInfo(str, 64).signatures[0])) {
                Log.d("com.android.server.spay.Utils", "hasValidSignature, VALID : ".concat(str));
                try {
                    int i = context.getPackageManager().getApplicationInfo(str, 0).uid;
                    String num = Integer.toString(i);
                    if (i < 1000 || num == null) {
                        Log.e(
                                "com.android.server.spay.Utils",
                                "backgroundWhitelist: bad uid: " + i + ", uidString: " + num);
                        return false;
                    }
                    StringBuilder sb = new StringBuilder("mRegisteredUid: ");
                    ArrayList arrayList = mRegisteredUid;
                    sb.append(arrayList.toString());
                    sb.append(", uidString: ");
                    sb.append(num);
                    Log.d("com.android.server.spay.Utils", sb.toString());
                    if (arrayList.contains(num)) {
                        Log.d("com.android.server.spay.Utils", "uid already added");
                        return true;
                    }
                    ActivityManager.getService().backgroundAllowlistUid(i);
                    if (!z) {
                        arrayList.add(num);
                    }
                    Log.d("com.android.server.spay.Utils", "call backgroundWhitelistUid done");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(
                            "com.android.server.spay.Utils",
                            "backgroundWhitelistUid exception " + e.toString());
                    return false;
                }
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException e2) {
            Log.e("com.android.server.spay.Utils", "hasValidSignature, exception " + e2.toString());
        }
        Log.e(
                "com.android.server.spay.Utils",
                "backgroundWhitelist: bad signature or not found: ".concat(str));
        return false;
    }

    public static boolean deleteDirectory(File file) {
        File[] listFiles;
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteDirectory(listFiles[i]);
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] readFile(java.lang.String r8) {
        /*
            java.lang.String r0 = "Error closing InputStream"
            java.lang.String r1 = "File Read - Length = "
            java.io.File r2 = new java.io.File
            r2.<init>(r8)
            java.lang.String r3 = "In readFile - Path "
            java.lang.String r4 = "com.android.server.spay.Utils"
            com.android.server.DualAppManagerService$$ExternalSyntheticOutline0.m(r3, r8, r4)
            r8 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L51
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L51
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            long r6 = r2.length()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            r5.append(r6)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            android.util.Log.d(r4, r1)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            long r1 = r2.length()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            int r1 = (int) r1     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            byte[] r2 = new byte[r1]     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L49
            int r5 = r3.read(r2)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            if (r5 == r1) goto L40
            java.lang.String r1 = "File Read Failed"
            android.util.Log.d(r4, r1)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            goto L41
        L3c:
            r8 = move-exception
            goto L63
        L3e:
            r8 = move-exception
            goto L55
        L40:
            r8 = r2
        L41:
            r3.close()     // Catch: java.io.IOException -> L45
            goto L62
        L45:
            android.util.Log.d(r4, r0)
            goto L62
        L49:
            r1 = move-exception
            r2 = r8
        L4b:
            r8 = r1
            goto L55
        L4d:
            r1 = move-exception
            r3 = r8
            r8 = r1
            goto L63
        L51:
            r1 = move-exception
            r2 = r8
            r3 = r2
            goto L4b
        L55:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L3c
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.io.IOException -> L5e
            goto L61
        L5e:
            android.util.Log.d(r4, r0)
        L61:
            r8 = r2
        L62:
            return r8
        L63:
            if (r3 == 0) goto L6c
            r3.close()     // Catch: java.io.IOException -> L69
            goto L6c
        L69:
            android.util.Log.d(r4, r0)
        L6c:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.spay.Utils.readFile(java.lang.String):byte[]");
    }

    public static void sendSecureUIAbortIntent(Context context) {
        Log.d("com.android.server.spay.Utils", "sendSecureUIAbortIntent");
        Intent intent = new Intent();
        intent.setAction("com.qualcomm.qti.services.secureui.action.ACTION_SUI_ABORT_MSG");
        intent.setPackage("com.qualcomm.qti.services.secureui");
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }
}
