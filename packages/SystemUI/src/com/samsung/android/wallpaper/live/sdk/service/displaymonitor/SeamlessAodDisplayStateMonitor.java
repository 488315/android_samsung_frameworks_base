package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SeamlessAodDisplayStateMonitor extends AbstractAodDisplayStateMonitor {
    public DisplayState mLastAodState;

    public SeamlessAodDisplayStateMonitor(Context context) {
        super(context);
        this.mLastAodState = DisplayState.AOD_WITHOUT_WALLPAPER;
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor, com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    public final synchronized DisplayState getDisplayState() {
        if (isMyDisplayDisabledByFoldState()) {
            return DisplayState.OFF;
        }
        if (isInteractive()) {
            return DisplayState.ON;
        }
        if (isShowingAod()) {
            return this.mLastAodState;
        }
        return DisplayState.OFF;
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor
    public final void onAodShowStateChanged(boolean z) {
        if (((SdkFoldUtils.isFolded(this.mContext) ? (char) 16 : (char) 4) & '<') == 16) {
            return;
        }
        if (!isShowingAod()) {
            if (isInteractive()) {
                return;
            }
            notifyDisplayStateChanged(DisplayState.OFF);
        } else {
            if (Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) != 0) {
                if (((SdkFoldUtils.isFolded(this.mContext) ? (char) 16 : (char) 4) & '<') == 16) {
                    notifyDisplayStateChanged(DisplayState.AOD_WITH_WALLPAPER);
                    return;
                }
            }
            notifyDisplayStateChanged(DisplayState.AOD_WITHOUT_WALLPAPER);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a2 A[Catch: all -> 0x0024, TRY_LEAVE, TryCatch #0 {all -> 0x0024, blocks: (B:4:0x0003, B:20:0x0047, B:23:0x0050, B:28:0x0058, B:32:0x0061, B:34:0x0082, B:36:0x0066, B:38:0x006b, B:39:0x006e, B:40:0x0088, B:43:0x0091, B:47:0x0098, B:48:0x009d, B:49:0x00a2, B:50:0x001a, B:53:0x0027, B:56:0x0031), top: B:3:0x0003 }] */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onCommand(int r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r0 = "onCommand : unexpected state. x="
            monitor-enter(r6)
            int r1 = r8.hashCode()     // Catch: java.lang.Throwable -> L24
            r2 = 0
            r3 = 1
            r4 = -1567151962(0xffffffffa29728a6, float:-4.097167E-18)
            r5 = 2
            if (r1 == r4) goto L31
            r4 = -1229183993(0xffffffffb6bc2407, float:-5.6070307E-6)
            if (r1 == r4) goto L27
            r4 = 1765809357(0x69401ccd, float:1.451561E25)
            if (r1 == r4) goto L1a
            goto L3b
        L1a:
            java.lang.String r1 = "android.wallpaper.goingtosleep"
            boolean r8 = r8.equals(r1)     // Catch: java.lang.Throwable -> L24
            if (r8 == 0) goto L3b
            r8 = r3
            goto L3c
        L24:
            r7 = move-exception
            goto La9
        L27:
            java.lang.String r1 = "android.wallpaper.wakingup"
            boolean r8 = r8.equals(r1)     // Catch: java.lang.Throwable -> L24
            if (r8 == 0) goto L3b
            r8 = r2
            goto L3c
        L31:
            java.lang.String r1 = "android.wallpaper.aodstate"
            boolean r8 = r8.equals(r1)     // Catch: java.lang.Throwable -> L24
            if (r8 == 0) goto L3b
            r8 = r5
            goto L3c
        L3b:
            r8 = -1
        L3c:
            if (r8 == 0) goto La2
            r1 = 4
            r4 = 16
            if (r8 == r3) goto L88
            if (r8 == r5) goto L47
            goto La7
        L47:
            android.content.Context r8 = r6.mContext     // Catch: java.lang.Throwable -> L24
            boolean r8 = com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils.isFolded(r8)     // Catch: java.lang.Throwable -> L24
            if (r8 == 0) goto L50
            r1 = r4
        L50:
            r8 = r1 & 60
            if (r8 != r4) goto L55
            r2 = r3
        L55:
            if (r2 != 0) goto L58
            goto La7
        L58:
            boolean r8 = r6.isInteractive()     // Catch: java.lang.Throwable -> L24
            if (r8 == 0) goto L5f
            goto La7
        L5f:
            if (r7 != 0) goto L64
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L24
            goto L80
        L64:
            if (r7 != r3) goto L69
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.AOD_WITHOUT_WALLPAPER     // Catch: java.lang.Throwable -> L24
            goto L80
        L69:
            if (r7 != r5) goto L6e
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.AOD_WITH_WALLPAPER     // Catch: java.lang.Throwable -> L24
            goto L80
        L6e:
            java.lang.String r8 = "SeamlessAodDisplayStateMonitor"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L24
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L24
            r1.append(r7)     // Catch: java.lang.Throwable -> L24
            java.lang.String r7 = r1.toString()     // Catch: java.lang.Throwable -> L24
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r8, r7)     // Catch: java.lang.Throwable -> L24
            r7 = 0
        L80:
            if (r7 == 0) goto La7
            r6.mLastAodState = r7     // Catch: java.lang.Throwable -> L24
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L24
            goto La7
        L88:
            android.content.Context r7 = r6.mContext     // Catch: java.lang.Throwable -> L24
            boolean r7 = com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils.isFolded(r7)     // Catch: java.lang.Throwable -> L24
            if (r7 == 0) goto L91
            r1 = r4
        L91:
            r7 = r1 & 60
            if (r7 != r4) goto L96
            r2 = r3
        L96:
            if (r2 != 0) goto L9d
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L24
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L24
        L9d:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.OFF     // Catch: java.lang.Throwable -> L24
            r6.mLastAodState = r7     // Catch: java.lang.Throwable -> L24
            goto La7
        La2:
            com.samsung.android.wallpaper.live.sdk.data.DisplayState r7 = com.samsung.android.wallpaper.live.sdk.data.DisplayState.ON     // Catch: java.lang.Throwable -> L24
            r6.notifyDisplayStateChanged(r7)     // Catch: java.lang.Throwable -> L24
        La7:
            monitor-exit(r6)
            return
        La9:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L24
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.SeamlessAodDisplayStateMonitor.onCommand(int, java.lang.String):void");
    }
}
