package com.android.systemui.qs.tiles;

import android.content.DialogInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                this.f$0.refreshState(null);
                break;
            case 1:
                this.f$0.refreshState(null);
                break;
            case 2:
                this.f$0.refreshState(null);
                break;
            case 3:
                MobileDataTile mobileDataTile = this.f$0;
                mobileDataTile.mDataController.setMobileDataEnabled(false);
                mobileDataTile.refreshState(null);
                break;
            case 4:
                this.f$0.refreshState(null);
                break;
            case 5:
                MobileDataTile mobileDataTile2 = this.f$0;
                mobileDataTile2.mDataController.setMobileDataEnabled(true);
                mobileDataTile2.refreshState(null);
                break;
            case 6:
                this.f$0.refreshState(null);
                break;
            case 7:
                this.f$0.refreshState(null);
                break;
            case 8:
                MobileDataTile mobileDataTile3 = this.f$0;
                mobileDataTile3.mDataController.setMobileDataEnabled(false);
                mobileDataTile3.refreshState(null);
                break;
            default:
                this.f$0.refreshState(null);
                break;
        }
    }
}
