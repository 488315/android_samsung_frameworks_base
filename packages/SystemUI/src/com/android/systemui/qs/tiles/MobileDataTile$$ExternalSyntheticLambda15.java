package com.android.systemui.qs.tiles;

import android.content.DialogInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MobileDataTile$$ExternalSyntheticLambda15 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileDataTile f$0;

    public /* synthetic */ MobileDataTile$$ExternalSyntheticLambda15(MobileDataTile mobileDataTile, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileDataTile;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        int i = this.$r8$classId;
        MobileDataTile mobileDataTile = this.f$0;
        switch (i) {
            case 0:
                mobileDataTile.refreshState(null);
                break;
            default:
                mobileDataTile.refreshState(null);
                break;
        }
    }
}
