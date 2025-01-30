package com.android.systemui.p016qs.external;

import android.service.quicksettings.Tile;
import com.android.systemui.p016qs.external.CustomTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CustomTile$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
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
                    CustomTileStatePersister customTileStatePersister = customTile.mCustomTileStatePersister;
                    customTileStatePersister.getClass();
                    customTileStatePersister.sharedPreferences.edit().putString(customTile.mKey.string, CustomTileStatePersisterKt.writeToString(tile)).apply();
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
