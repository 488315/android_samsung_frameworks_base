package com.android.systemui.qs.tiles;

import android.content.DialogInterface;
import android.content.Intent;

/* loaded from: classes2.dex */
public final /* synthetic */ class MobileDataTile$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileDataTile f$0;

    public /* synthetic */ MobileDataTile$$ExternalSyntheticLambda0(MobileDataTile mobileDataTile, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileDataTile;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MobileDataTile mobileDataTile = this.f$0;
        switch (i2) {
            case 0:
                Intent intent = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 1:
                Intent intent2 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 2:
                mobileDataTile.mDataController.setMobileDataEnabled(false);
                mobileDataTile.refreshState(null);
                break;
            case 3:
                Intent intent3 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 4:
                Intent intent4 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 5:
                Intent intent5 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 6:
                mobileDataTile.mDataController.setMobileDataEnabled(false);
                mobileDataTile.refreshState(null);
                break;
            case 7:
                Intent intent6 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
            case 8:
                mobileDataTile.mDataController.setMobileDataEnabled(true);
                mobileDataTile.refreshState(null);
                break;
            default:
                Intent intent7 = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.refreshState(null);
                break;
        }
    }
}
