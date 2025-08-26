package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

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

    /* JADX WARN: Removed duplicated region for block: B:22:0x003b  */
    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void onCommand(int i, String str) {
        char c;
        DisplayState displayState;
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1567151962) {
                if (iHashCode != -1229183993) {
                    c = (iHashCode == 1765809357 && str.equals("android.wallpaper.goingtosleep")) ? (char) 1 : (char) 65535;
                } else if (str.equals("android.wallpaper.wakingup")) {
                    c = 0;
                }
            } else if (str.equals("android.wallpaper.aodstate")) {
                c = 2;
            }
            if (c != 0) {
                if (c == 1) {
                    if (!(((SdkFoldUtils.isFolded(this.mContext) ? (char) 16 : (char) 4) & '<') == 16)) {
                        notifyDisplayStateChanged(DisplayState.OFF);
                    }
                    this.mLastAodState = DisplayState.OFF;
                } else if (c == 2) {
                    if ((((SdkFoldUtils.isFolded(this.mContext) ? (char) 16 : (char) 4) & '<') == 16) && !isInteractive()) {
                        if (i == 0) {
                            displayState = DisplayState.OFF;
                        } else if (i == 1) {
                            displayState = DisplayState.AOD_WITHOUT_WALLPAPER;
                        } else if (i == 2) {
                            displayState = DisplayState.AOD_WITH_WALLPAPER;
                        } else {
                            SdkLog.e("SeamlessAodDisplayStateMonitor", "onCommand : unexpected state. x=" + i);
                            displayState = null;
                        }
                        if (displayState != null) {
                            this.mLastAodState = displayState;
                            notifyDisplayStateChanged(displayState);
                        }
                    }
                }
            } else {
                notifyDisplayStateChanged(DisplayState.ON);
            }
        } finally {
        }
    }
}
