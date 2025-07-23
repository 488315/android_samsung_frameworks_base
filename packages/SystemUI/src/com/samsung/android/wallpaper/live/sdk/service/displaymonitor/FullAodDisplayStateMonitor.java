package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FullAodDisplayStateMonitor extends AbstractAodDisplayStateMonitor {
    public final int mFullAodSupportDisplays;
    public boolean mIsGoingToAod;

    public FullAodDisplayStateMonitor(Context context, int i) {
        super(context);
        this.mIsGoingToAod = false;
        this.mFullAodSupportDisplays = i;
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor
    public final void onAodShowStateChanged(boolean z) {
        if (isShowingAod()) {
            if (this.mIsGoingToAod || this.mLastReportedState == DisplayState.OFF) {
                if (Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) != 0) {
                    if (((SdkFoldUtils.isFolded(this.mContext) ? 16 : 4) & this.mFullAodSupportDisplays) != 0) {
                        notifyDisplayStateChanged(DisplayState.AOD_WITH_WALLPAPER);
                    }
                }
                notifyDisplayStateChanged(DisplayState.AOD_WITHOUT_WALLPAPER);
            } else {
                SdkLog.d("FullAodDisplayStateMonitor", "onAodShowStateChanged: going to AOD cancelled");
            }
        } else if (!isInteractive()) {
            notifyDisplayStateChanged(DisplayState.OFF);
        }
        this.mIsGoingToAod = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0092 A[Catch: all -> 0x002d, TRY_LEAVE, TryCatch #0 {all -> 0x002d, blocks: (B:3:0x0001, B:6:0x000e, B:22:0x004e, B:24:0x0054, B:27:0x005d, B:28:0x0067, B:30:0x0071, B:32:0x0077, B:35:0x0080, B:36:0x008a, B:37:0x0092, B:38:0x0023, B:41:0x0030, B:44:0x003a), top: B:2:0x0001 }] */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onCommand(int r7, java.lang.String r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            int r7 = r6.getCurrentWhich()     // Catch: java.lang.Throwable -> L2d
            r0 = r7 & 1
            r1 = 0
            r2 = 1
            if (r0 != r2) goto Ld
            r0 = r2
            goto Le
        Ld:
            r0 = r1
        Le:
            int r3 = r8.hashCode()     // Catch: java.lang.Throwable -> L2d
            r4 = -1229183993(0xffffffffb6bc2407, float:-5.6070307E-6)
            r5 = 2
            if (r3 == r4) goto L3a
            r4 = -709764799(0xffffffffd5b1d941, float:-2.4443332E13)
            if (r3 == r4) goto L30
            r4 = 1765809357(0x69401ccd, float:1.451561E25)
            if (r3 == r4) goto L23
            goto L44
        L23:
            java.lang.String r3 = "android.wallpaper.goingtosleep"
            boolean r8 = r8.equals(r3)     // Catch: java.lang.Throwable -> L2d
            if (r8 == 0) goto L44
            r8 = r2
            goto L45
        L2d:
            r7 = move-exception
            goto L9b
        L30:
            java.lang.String r3 = "samsung.android.wallpaper.goingtosleep"
            boolean r8 = r8.equals(r3)     // Catch: java.lang.Throwable -> L2d
            if (r8 == 0) goto L44
            r8 = r5
            goto L45
        L3a:
            java.lang.String r3 = "android.wallpaper.wakingup"
            boolean r8 = r8.equals(r3)     // Catch: java.lang.Throwable -> L2d
            if (r8 == 0) goto L44
            r8 = r1
            goto L45
        L44:
            r8 = -1
        L45:
            if (r8 == 0) goto L92
            if (r8 == r2) goto L6f
            if (r8 == r5) goto L4c
            goto L99
        L4c:
            if (r0 == 0) goto L99
            boolean r8 = r6.isMyDisplayDisabledByFoldState()     // Catch: java.lang.Throwable -> L2d
            if (r8 != 0) goto L67
            android.content.Context r8 = r6.mContext     // Catch: java.lang.Throwable -> L2d
            boolean r7 = com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils.isDozeAfterScreenOff(r7, r8)     // Catch: java.lang.Throwable -> L2d
            if (r7 == 0) goto L5d
            goto L67
        L5d:
            java.lang.String r7 = "FullAodDisplayStateMonitor"
            java.lang.String r8 = "onCommand: will enter AOD. ignore OFF"
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r7, r8)     // Catch: java.lang.Throwable -> L2d
            r6.mIsGoingToAod = r2     // Catch: java.lang.Throwable -> L2d
            goto L99
        L67:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L2d
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L2d
            r6.mIsGoingToAod = r1     // Catch: java.lang.Throwable -> L2d
            goto L99
        L6f:
            if (r0 != 0) goto L99
            boolean r8 = r6.isMyDisplayDisabledByFoldState()     // Catch: java.lang.Throwable -> L2d
            if (r8 != 0) goto L8a
            android.content.Context r8 = r6.mContext     // Catch: java.lang.Throwable -> L2d
            boolean r7 = com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils.isDozeAfterScreenOff(r7, r8)     // Catch: java.lang.Throwable -> L2d
            if (r7 == 0) goto L80
            goto L8a
        L80:
            java.lang.String r7 = "FullAodDisplayStateMonitor"
            java.lang.String r8 = "onCommand: will enter AOD. ignore OFF"
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r7, r8)     // Catch: java.lang.Throwable -> L2d
            r6.mIsGoingToAod = r2     // Catch: java.lang.Throwable -> L2d
            goto L99
        L8a:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L2d
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L2d
            r6.mIsGoingToAod = r1     // Catch: java.lang.Throwable -> L2d
            goto L99
        L92:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.ON     // Catch: java.lang.Throwable -> L2d
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L2d
            r6.mIsGoingToAod = r1     // Catch: java.lang.Throwable -> L2d
        L99:
            monitor-exit(r6)
            return
        L9b:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L2d
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.FullAodDisplayStateMonitor.onCommand(int, java.lang.String):void");
    }
}
