package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0028  */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void onCommand(int i, String str) {
        char c;
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1229183993) {
                c = (iHashCode == 1765809357 && str.equals("android.wallpaper.goingtosleep")) ? (char) 1 : (char) 65535;
            } else if (str.equals("android.wallpaper.wakingup")) {
                c = 0;
            }
            if (c == 0) {
                notifyDisplayStateChanged(DisplayState.ON);
                this.mIsGoingToAod = false;
            } else if (c == 1) {
                if (isMyDisplayDisabledByFoldState()) {
                    notifyDisplayStateChanged(DisplayState.OFF);
                    this.mIsGoingToAod = false;
                } else {
                    if (SdkDeviceUtils.isDozeAfterScreenOff(getCurrentWhich(), this.mContext)) {
                        notifyDisplayStateChanged(DisplayState.OFF);
                        this.mIsGoingToAod = false;
                    } else {
                        SdkLog.d("BasicAodDisplayStateMonitor", "onCommand: will enter AOD. ignore OFF");
                        this.mIsGoingToAod = true;
                    }
                }
            }
        } finally {
        }
    }
}
