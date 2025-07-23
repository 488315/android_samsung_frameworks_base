package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NoAodDisplayStateMonitor extends DisplayStateMonitor {
    public NoAodDisplayStateMonitor(Context context) {
        super(context);
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    public final synchronized DisplayState getDisplayState() {
        if (isInteractive()) {
            return DisplayState.ON;
        }
        return DisplayState.OFF;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0033 A[Catch: all -> 0x001b, TRY_LEAVE, TryCatch #0 {all -> 0x001b, blocks: (B:3:0x0001, B:15:0x002d, B:16:0x0033, B:17:0x0011, B:20:0x001d), top: B:2:0x0001 }] */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onCommand(int r3, java.lang.String r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            int r3 = r4.hashCode()     // Catch: java.lang.Throwable -> L1b
            r0 = -1229183993(0xffffffffb6bc2407, float:-5.6070307E-6)
            r1 = 1
            if (r3 == r0) goto L1d
            r0 = 1765809357(0x69401ccd, float:1.451561E25)
            if (r3 == r0) goto L11
            goto L27
        L11:
            java.lang.String r3 = "android.wallpaper.goingtosleep"
            boolean r3 = r4.equals(r3)     // Catch: java.lang.Throwable -> L1b
            if (r3 == 0) goto L27
            r3 = r1
            goto L28
        L1b:
            r3 = move-exception
            goto L3a
        L1d:
            java.lang.String r3 = "android.wallpaper.wakingup"
            boolean r3 = r4.equals(r3)     // Catch: java.lang.Throwable -> L1b
            if (r3 == 0) goto L27
            r3 = 0
            goto L28
        L27:
            r3 = -1
        L28:
            if (r3 == 0) goto L33
            if (r3 == r1) goto L2d
            goto L38
        L2d:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r3 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L1b
            r2.notifyDisplayStateChanged(r3)     // Catch: java.lang.Throwable -> L1b
            goto L38
        L33:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r3 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.ON     // Catch: java.lang.Throwable -> L1b
            r2.notifyDisplayStateChanged(r3)     // Catch: java.lang.Throwable -> L1b
        L38:
            monitor-exit(r2)
            return
        L3a:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L1b
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.NoAodDisplayStateMonitor.onCommand(int, java.lang.String):void");
    }
}
