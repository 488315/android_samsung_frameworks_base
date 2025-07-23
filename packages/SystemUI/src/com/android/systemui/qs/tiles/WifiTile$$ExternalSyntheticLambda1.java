package com.android.systemui.qs.tiles;

import android.app.SemStatusBarManager;
import android.content.Intent;
import com.android.systemui.qs.tiles.WifiTile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiTile$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WifiTile wifiTile = (WifiTile) obj;
                if (wifiTile.mExpectDisabled) {
                    wifiTile.mExpectDisabled = false;
                    wifiTile.refreshState(null);
                    break;
                }
                break;
            default:
                WifiTile wifiTile2 = ((WifiTile.WifiTileReceiver) obj).this$0;
                Intent intent = WifiTile.WIFI_SETTINGS;
                SemStatusBarManager semStatusBarManager = (SemStatusBarManager) wifiTile2.mContext.getSystemService("sem_statusbar");
                if (semStatusBarManager != null) {
                    semStatusBarManager.expandQuickSettingsPanel();
                    break;
                }
                break;
        }
    }
}
