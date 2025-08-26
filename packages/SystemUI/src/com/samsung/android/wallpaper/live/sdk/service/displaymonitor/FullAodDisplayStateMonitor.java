package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

/* loaded from: classes4.dex */
public class FullAodDisplayStateMonitor extends AbstractAodDisplayStateMonitor {
    public final int mFullAodSupportDisplays;
    public boolean mIsGoingToAod;

    public FullAodDisplayStateMonitor(Context context, int i) {
        super(context);
        this.mIsGoingToAod = false;
        this.mFullAodSupportDisplays = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAodShowStateChanged(boolean z) {
        if (isShowingAod()) {
            if (!this.mIsGoingToAod && this.mLastReportedState != DisplayState.OFF) {
                SdkLog.d("FullAodDisplayStateMonitor", "onAodShowStateChanged: going to AOD cancelled");
            } else if (Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) == 0) {
                notifyDisplayStateChanged(DisplayState.AOD_WITHOUT_WALLPAPER);
            } else {
                if (((SdkFoldUtils.isFolded(this.mContext) ? 16 : 4) & this.mFullAodSupportDisplays) != 0) {
                    notifyDisplayStateChanged(DisplayState.AOD_WITH_WALLPAPER);
                }
            }
        } else if (!isInteractive()) {
            notifyDisplayStateChanged(DisplayState.OFF);
        }
        this.mIsGoingToAod = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0044  */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void onCommand(int i, String str) {
        char c;
        try {
            int currentWhich = getCurrentWhich();
            boolean z = (currentWhich & 1) == 1;
            int iHashCode = str.hashCode();
            if (iHashCode != -1229183993) {
                if (iHashCode != -709764799) {
                    c = (iHashCode == 1765809357 && str.equals("android.wallpaper.goingtosleep")) ? (char) 1 : (char) 65535;
                } else if (str.equals("samsung.android.wallpaper.goingtosleep")) {
                    c = 2;
                }
            } else if (str.equals("android.wallpaper.wakingup")) {
                c = 0;
            }
            if (c == 0) {
                notifyDisplayStateChanged(DisplayState.ON);
                this.mIsGoingToAod = false;
            } else if (c != 1) {
                if (c == 2 && z) {
                    if (isMyDisplayDisabledByFoldState() || SdkDeviceUtils.isDozeAfterScreenOff(currentWhich, this.mContext)) {
                        notifyDisplayStateChanged(DisplayState.OFF);
                        this.mIsGoingToAod = false;
                    } else {
                        SdkLog.d("FullAodDisplayStateMonitor", "onCommand: will enter AOD. ignore OFF");
                        this.mIsGoingToAod = true;
                    }
                }
            } else if (!z) {
                if (isMyDisplayDisabledByFoldState() || SdkDeviceUtils.isDozeAfterScreenOff(currentWhich, this.mContext)) {
                    notifyDisplayStateChanged(DisplayState.OFF);
                    this.mIsGoingToAod = false;
                } else {
                    SdkLog.d("FullAodDisplayStateMonitor", "onCommand: will enter AOD. ignore OFF");
                    this.mIsGoingToAod = true;
                }
            }
        } finally {
        }
    }
}
