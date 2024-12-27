package com.android.systemui.qs.external;

import android.service.quicksettings.Tile;
import com.android.systemui.qs.external.CustomTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CustomTile$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CustomTile customTile = (CustomTile) this.f$0;
                Tile tile = (Tile) this.f$1;
                customTile.applyTileState(tile, true);
                if (customTile.mServiceManager.isActiveTile()) {
                    CustomTileStatePersisterImpl customTileStatePersisterImpl = (CustomTileStatePersisterImpl) customTile.mCustomTileStatePersister;
                    customTileStatePersisterImpl.getClass();
                    customTileStatePersisterImpl.sharedPreferences.edit().putString(customTile.mKey.string, CustomTileStatePersisterKt.writeToString(tile)).apply();
                    break;
                }
                break;
            default:
                CustomTile.CustomDetailAdapter customDetailAdapter = (CustomTile.CustomDetailAdapter) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                customDetailAdapter.getClass();
                customDetailAdapter.setToggleState(!bool.booleanValue());
                break;
        }
    }
}
