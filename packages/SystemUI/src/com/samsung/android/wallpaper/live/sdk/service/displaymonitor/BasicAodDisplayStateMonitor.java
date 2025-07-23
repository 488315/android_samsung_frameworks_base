package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BasicAodDisplayStateMonitor extends AbstractAodDisplayStateMonitor {
    public boolean mIsGoingToAod;

    public BasicAodDisplayStateMonitor(Context context) {
        super(context);
        this.mIsGoingToAod = false;
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor
    public final void onAodShowStateChanged(boolean z) {
        if (z) {
            if (this.mIsGoingToAod || this.mLastReportedState == DisplayState.OFF) {
                notifyDisplayStateChanged(DisplayState.AOD_WITHOUT_WALLPAPER);
            } else {
                SdkLog.d("BasicAodDisplayStateMonitor", "onAodShowStateChanged: going to AOD cancelled");
            }
        } else if (!isInteractive()) {
            notifyDisplayStateChanged(DisplayState.OFF);
        }
        this.mIsGoingToAod = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0053 A[Catch: all -> 0x001c, TRY_LEAVE, TryCatch #0 {all -> 0x001c, blocks: (B:3:0x0001, B:15:0x002e, B:17:0x0034, B:20:0x0041, B:21:0x004b, B:22:0x0053, B:23:0x0012, B:26:0x001e), top: B:2:0x0001 }] */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onCommand(int r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            int r4 = r5.hashCode()     // Catch: java.lang.Throwable -> L1c
            r0 = -1229183993(0xffffffffb6bc2407, float:-5.6070307E-6)
            r1 = 0
            r2 = 1
            if (r4 == r0) goto L1e
            r0 = 1765809357(0x69401ccd, float:1.451561E25)
            if (r4 == r0) goto L12
            goto L28
        L12:
            java.lang.String r4 = "android.wallpaper.goingtosleep"
            boolean r4 = r5.equals(r4)     // Catch: java.lang.Throwable -> L1c
            if (r4 == 0) goto L28
            r4 = r2
            goto L29
        L1c:
            r4 = move-exception
            goto L5c
        L1e:
            java.lang.String r4 = "android.wallpaper.wakingup"
            boolean r4 = r5.equals(r4)     // Catch: java.lang.Throwable -> L1c
            if (r4 == 0) goto L28
            r4 = r1
            goto L29
        L28:
            r4 = -1
        L29:
            if (r4 == 0) goto L53
            if (r4 == r2) goto L2e
            goto L5a
        L2e:
            boolean r4 = r3.isMyDisplayDisabledByFoldState()     // Catch: java.lang.Throwable -> L1c
            if (r4 != 0) goto L4b
            android.content.Context r4 = r3.mContext     // Catch: java.lang.Throwable -> L1c
            int r5 = r3.getCurrentWhich()     // Catch: java.lang.Throwable -> L1c
            boolean r4 = com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils.isDozeAfterScreenOff(r5, r4)     // Catch: java.lang.Throwable -> L1c
            if (r4 == 0) goto L41
            goto L4b
        L41:
            java.lang.String r4 = "BasicAodDisplayStateMonitor"
            java.lang.String r5 = "onCommand: will enter AOD. ignore OFF"
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r4, r5)     // Catch: java.lang.Throwable -> L1c
            r3.mIsGoingToAod = r2     // Catch: java.lang.Throwable -> L1c
            goto L5a
        L4b:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r4 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L1c
            r3.notifyDisplayStateChanged(r4)     // Catch: java.lang.Throwable -> L1c
            r3.mIsGoingToAod = r1     // Catch: java.lang.Throwable -> L1c
            goto L5a
        L53:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r4 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.ON     // Catch: java.lang.Throwable -> L1c
            r3.notifyDisplayStateChanged(r4)     // Catch: java.lang.Throwable -> L1c
            r3.mIsGoingToAod = r1     // Catch: java.lang.Throwable -> L1c
        L5a:
            monitor-exit(r3)
            return
        L5c:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1c
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.BasicAodDisplayStateMonitor.onCommand(int, java.lang.String):void");
    }
}
