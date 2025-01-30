package com.android.systemui.p016qs.tiles;

import com.android.systemui.p016qs.tiles.WifiTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                WifiTile wifiTile = (WifiTile) this.f$0;
                if (wifiTile.mExpectDisabled) {
                    wifiTile.mExpectDisabled = false;
                    wifiTile.refreshState(null);
                    break;
                }
                break;
            default:
                WifiTile.WifiDetailAdapter wifiDetailAdapter = (WifiTile.WifiDetailAdapter) this.f$0;
                int i = WifiTile.WifiDetailAdapter.$r8$clinit;
                wifiDetailAdapter.setToggleState(!wifiDetailAdapter.getToggleState().booleanValue());
                break;
        }
    }
}
