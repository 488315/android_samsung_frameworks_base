package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;
import com.samsung.android.sdk.routines.v3.data.ActionResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class Dispatcher {
    public static Bundle c$2() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraKey.RESULT_INT.a, ActionResult.ResultCode.FAIL_TIMEOUT.value);
        return bundle;
    }

    public abstract String a();

    /* JADX WARN: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.String r0 = "wait: "
            monitor-enter(r10)
            r1 = 7000(0x1b58, double:3.4585E-320)
            r3 = 1
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le java.lang.InterruptedException -> L12
            r10.wait(r1)     // Catch: java.lang.Throwable -> Le java.lang.InterruptedException -> L10
            goto L43
        Le:
            r9 = move-exception
            goto L52
        L10:
            r6 = move-exception
            goto L15
        L12:
            r6 = move-exception
            r4 = 0
        L15:
            java.lang.Throwable r7 = new java.lang.Throwable     // Catch: java.lang.Throwable -> Le
            r7.<init>()     // Catch: java.lang.Throwable -> Le
            java.lang.StackTraceElement[] r7 = r7.getStackTrace()     // Catch: java.lang.Throwable -> Le
            r7 = r7[r3]     // Catch: java.lang.Throwable -> Le
            java.lang.String r7 = r7.getMethodName()     // Catch: java.lang.Throwable -> Le
            java.lang.String r9 = r9.a()     // Catch: java.lang.Throwable -> Le
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Le
            r8.<init>(r0)     // Catch: java.lang.Throwable -> Le
            r8.append(r7)     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = " - "
            r8.append(r0)     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = r6.getMessage()     // Catch: java.lang.Throwable -> Le
            r8.append(r0)     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = r8.toString()     // Catch: java.lang.Throwable -> Le
            com.samsung.android.sdk.routines.v3.internal.Log.a(r9, r0)     // Catch: java.lang.Throwable -> Le
        L43:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> Le
            long r9 = java.lang.System.currentTimeMillis()
            long r9 = r9 - r4
            int r9 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r9 < 0) goto L4f
            r9 = r3
            goto L50
        L4f:
            r9 = 0
        L50:
            r9 = r9 ^ r3
            return r9
        L52:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> Le
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.routines.v3.internal.Dispatcher.a(java.lang.Object):boolean");
    }
}
