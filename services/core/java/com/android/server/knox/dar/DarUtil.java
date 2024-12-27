package com.android.server.knox.dar;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.os.Binder;
import android.os.SystemProperties;
import android.util.Log;

import com.android.server.input.KeyboardMetricsCollector;

public abstract class DarUtil {
    public static String credentialTypeToString(int i) {
        return i != -1
                ? i != 1
                        ? i != 3
                                ? i != 4
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "Unknown ")
                                        : "Password"
                                : "Pin"
                        : "Pattern"
                : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
    }

    public static boolean isDaemonRunning() {
        String str;
        String concat = "init.svc.".concat("dualdard");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                str = SystemProperties.get(concat);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (str != null && !str.isEmpty()) {
                z = INetd.IF_FLAG_RUNNING.equals(str);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            }
            Log.e("DARUtil", "isDaemonRunning() - Service not found");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x002e, code lost:

       if (com.samsung.android.knox.SemPersonaManager.isDoEnabled(0) != false) goto L16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isEnterpriseUser(android.content.pm.UserInfo r4) {
        /*
            boolean r0 = r4.isManagedProfile()
            java.lang.String r1 = "DARUtil"
            if (r0 == 0) goto L20
            boolean r0 = r4.isSdpNotSupportedSecureFolder()
            if (r0 != 0) goto L20
            boolean r0 = r4.isGuest()
            if (r0 != 0) goto L20
            boolean r0 = r4.isDualAppProfile()
            if (r0 != 0) goto L20
            boolean r0 = r4.isBMode()
            if (r0 == 0) goto L30
        L20:
            int r0 = r4.id
            r2 = 0
            if (r0 != 0) goto L31
            java.lang.String r0 = "Device owner status not updated yet..."
            android.util.Log.d(r1, r0)
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isDoEnabled(r2)
            if (r0 == 0) goto L31
        L30:
            r2 = 1
        L31:
            if (r2 != 0) goto L40
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "Not an enterprise user : "
            r0.<init>(r3)
            int r4 = r4.id
            com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0.m(r0, r4, r1)
            goto L4d
        L40:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "is enterprise user : "
            r0.<init>(r3)
            int r4 = r4.id
            com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0.m(r0, r4, r1)
        L4d:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.DarUtil.isEnterpriseUser(android.content.pm.UserInfo):boolean");
    }
}
