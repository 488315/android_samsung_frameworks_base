package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.HotspotTile;

public final /* synthetic */ class HotspotTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HotspotTile$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((HotspotTile) obj).handleSecondaryClick(true);
                break;
            default:
                ((HotspotTile.HotSpotDetailAdapter) obj).setToggleState(!r1.getToggleState().booleanValue());
                break;
        }
    }
}
