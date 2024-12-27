package com.android.systemui.keyguard;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

public final class KeyguardDumpLog {
    public static final KeyguardDumpLog INSTANCE = new KeyguardDumpLog();
    public static final String[] STATE_MSG = {"keyguardGoingAway", "setLockScreenShown", "externalEnabled", "screen_toggled", "occluded"};
    public static SamsungServiceLogger logger;

    private KeyguardDumpLog() {
    }

    public static final void log(String str, LogLevel logLevel, String str2, Throwable th) {
        if (th != null) {
            str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, " ", th.getMessage());
        }
        SamsungServiceLogger samsungServiceLogger = logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x002c, code lost:
    
        if (r3 != 4) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void state$default(com.android.systemui.keyguard.KeyguardDumpLog r2, int r3, boolean r4, boolean r5, boolean r6, int r7, int r8, int r9) {
        /*
            r0 = r9 & 2
            r1 = 0
            if (r0 == 0) goto L6
            r4 = r1
        L6:
            r0 = r9 & 4
            if (r0 == 0) goto Lb
            r5 = r1
        Lb:
            r0 = r9 & 8
            if (r0 == 0) goto L10
            r6 = r1
        L10:
            r0 = r9 & 16
            r1 = -1
            if (r0 == 0) goto L16
            r7 = r1
        L16:
            r9 = r9 & 32
            if (r9 == 0) goto L1b
            r8 = r1
        L1b:
            r2.getClass()
            java.lang.String r2 = " "
            if (r3 == 0) goto L6d
            r9 = 1
            if (r3 == r9) goto L52
            r5 = 2
            if (r3 == r5) goto L4d
            r5 = 3
            if (r3 == r5) goto L2f
            r5 = 4
            if (r3 == r5) goto L4d
            goto L82
        L2f:
            java.lang.String r2 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r7, r2)
            if (r7 == 0) goto L38
            if (r7 == r9) goto L38
            goto L82
        L38:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            java.lang.String r2 = " why:"
            r4.append(r2)
            r4.append(r8)
            java.lang.String r2 = r4.toString()
            goto L82
        L4d:
            java.lang.String r2 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m(r2, r4)
            goto L82
        L52:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r2)
            r7.append(r5)
            r7.append(r2)
            r7.append(r6)
            java.lang.String r2 = r7.toString()
            if (r4 != 0) goto L82
            java.lang.String r4 = " failed"
            java.lang.String r2 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r2, r4)
            goto L82
        L6d:
            if (r4 == 0) goto L74
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            goto L76
        L74:
            java.lang.String r4 = "failed"
        L76:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r2)
            r5.append(r4)
            java.lang.String r2 = r5.toString()
        L82:
            com.android.systemui.log.SamsungServiceLogger r4 = com.android.systemui.keyguard.KeyguardDumpLog.logger
            if (r4 == 0) goto L91
            java.lang.String[] r5 = com.android.systemui.keyguard.KeyguardDumpLog.STATE_MSG
            r3 = r5[r3]
            com.android.systemui.log.core.LogLevel r5 = com.android.systemui.log.core.LogLevel.DEBUG
            com.android.systemui.log.SamsungServiceLoggerImpl r4 = (com.android.systemui.log.SamsungServiceLoggerImpl) r4
            r4.logWithThreadId(r3, r5, r2)
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardDumpLog.state$default(com.android.systemui.keyguard.KeyguardDumpLog, int, boolean, boolean, boolean, int, int, int):void");
    }
}
