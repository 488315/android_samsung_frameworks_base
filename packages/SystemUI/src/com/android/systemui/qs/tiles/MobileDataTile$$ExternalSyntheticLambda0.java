package com.android.systemui.qs.tiles;

import android.content.DialogInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                mobileDataTile.refreshState(null);
                break;
            case 1:
                mobileDataTile.refreshState(null);
                break;
            case 2:
                mobileDataTile.mDataController.setMobileDataEnabled(false);
                mobileDataTile.refreshState(null);
                break;
            case 3:
                mobileDataTile.refreshState(null);
                break;
            case 4:
                mobileDataTile.refreshState(null);
                break;
            case 5:
                mobileDataTile.refreshState(null);
                break;
            case 6:
                mobileDataTile.mDataController.setMobileDataEnabled(false);
                mobileDataTile.refreshState(null);
                break;
            case 7:
                mobileDataTile.refreshState(null);
                break;
            case 8:
                mobileDataTile.mDataController.setMobileDataEnabled(true);
                mobileDataTile.refreshState(null);
                break;
            default:
                mobileDataTile.refreshState(null);
                break;
        }
    }
}
