package com.android.systemui.qs.external;

import android.service.quicksettings.Tile;
import com.android.systemui.qs.external.CustomTile;

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
                if (customTile.mServiceManager.mStateManager.isActiveTile()) {
                    CustomTileStatePersisterImpl customTileStatePersisterImpl = (CustomTileStatePersisterImpl) customTile.mCustomTileStatePersister;
                    customTileStatePersisterImpl.getClass();
                    customTileStatePersisterImpl.sharedPreferences.edit().putString(customTile.mKey.string, CustomTileStatePersisterKt.writeToString(tile)).apply();
                    break;
                }
                break;
            default:
                CustomTile.CustomDetailAdapter customDetailAdapter = (CustomTile.CustomDetailAdapter) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                int i = CustomTile.CustomDetailAdapter.$r8$clinit;
                customDetailAdapter.getClass();
                customDetailAdapter.setToggleState(!bool.booleanValue());
                break;
        }
    }
}
