package com.android.systemui.qs.tiles;

import android.app.SemStatusBarManager;
import com.android.systemui.qs.tiles.SBluetoothTile;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class SBluetoothTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SBluetoothTile$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SBluetoothTile sBluetoothTile = (SBluetoothTile) obj;
                if (((SBluetoothControllerImpl) sBluetoothTile.mController).mState == 12) {
                    sBluetoothTile.fireToggleStateChanged(true);
                    break;
                }
                break;
            case 1:
                boolean z = SBluetoothTile.DEBUG;
                ((SBluetoothTile) obj).handleSecondaryClick();
                break;
            default:
                SBluetoothTile sBluetoothTile2 = ((SBluetoothTile.SubscreenBluetoothTileReceiver) obj).this$0;
                boolean z2 = SBluetoothTile.DEBUG;
                SemStatusBarManager semStatusBarManager = (SemStatusBarManager) sBluetoothTile2.mContext.getSystemService("sem_statusbar");
                if (semStatusBarManager != null) {
                    semStatusBarManager.expandQuickSettingsPanel();
                    break;
                }
                break;
        }
    }
}
