package com.samsung.android.wallpaper.live.sdk.utils;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdkCommonUtils {
    public static String dumpBundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        if (bundle.isEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                sb.append("[");
                sb.append(dumpBundleToString((Bundle) obj));
                sb.append("]");
            } else {
                sb.append(obj);
            }
            sb.append(", ");
        }
        String sb2 = sb.toString();
        return sb2.endsWith(", ") ? sb2.substring(0, sb2.length() - 2) : sb2;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getFocusedUserId(android.content.Context r5) {
        /*
            java.lang.String r0 = "SdkCommonUtils"
            java.lang.String r1 = "getFocusedUserId : "
            int r2 = android.app.ActivityManager.semGetCurrentUser()     // Catch: java.lang.SecurityException -> L1c
            if (r2 == 0) goto L2f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.SecurityException -> L1a
            r3.<init>(r1)     // Catch: java.lang.SecurityException -> L1a
            r3.append(r2)     // Catch: java.lang.SecurityException -> L1a
            java.lang.String r1 = r3.toString()     // Catch: java.lang.SecurityException -> L1a
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r0, r1)     // Catch: java.lang.SecurityException -> L1a
            goto L2f
        L1a:
            r1 = move-exception
            goto L1e
        L1c:
            r1 = move-exception
            r2 = 0
        L1e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getFocusedUserId : e="
            r3.<init>(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r0, r3, r1)
        L2f:
            if (r2 != 0) goto L3d
            java.lang.String r1 = "persona"
            java.lang.Object r5 = r5.getSystemService(r1)
            com.samsung.android.knox.SemPersonaManager r5 = (com.samsung.android.knox.SemPersonaManager) r5
            int r2 = r5.getFocusedKnoxId()
        L3d:
            if (r2 == 0) goto L50
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r1 = "getFocusedUserId : id = "
            r5.<init>(r1)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r0, r5)
        L50:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.utils.SdkCommonUtils.getFocusedUserId(android.content.Context):int");
    }
}
