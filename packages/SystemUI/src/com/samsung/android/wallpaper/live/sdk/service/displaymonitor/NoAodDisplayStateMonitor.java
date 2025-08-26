package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;

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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0027  */
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
            } else if (c == 1) {
                notifyDisplayStateChanged(DisplayState.OFF);
            }
        } finally {
        }
    }
}
