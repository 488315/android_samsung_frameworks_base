package com.android.systemui.qs.tiles;

import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;

public final /* synthetic */ class SBluetoothTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SBluetoothTile f$0;

    public /* synthetic */ SBluetoothTile$$ExternalSyntheticLambda0(SBluetoothTile sBluetoothTile, int i) {
        this.$r8$classId = i;
        this.f$0 = sBluetoothTile;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SBluetoothTile sBluetoothTile = this.f$0;
        switch (i) {
            case 0:
                if (((SBluetoothControllerImpl) sBluetoothTile.mController).mState == 12) {
                    sBluetoothTile.fireToggleStateChanged(true);
                    break;
                }
                break;
            default:
                sBluetoothTile.handleSecondaryClick();
                break;
        }
    }
}
