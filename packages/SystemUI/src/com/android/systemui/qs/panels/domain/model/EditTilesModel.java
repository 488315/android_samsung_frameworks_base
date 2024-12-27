package com.android.systemui.qs.panels.domain.model;

import com.android.systemui.qs.panels.shared.model.EditTileData;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EditTilesModel {
    public final List customTiles;
    public final List stockTiles;

    public EditTilesModel(List<EditTileData> list, List<EditTileData> list2) {
        this.stockTiles = list;
        this.customTiles = list2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EditTilesModel)) {
            return false;
        }
        EditTilesModel editTilesModel = (EditTilesModel) obj;
        return Intrinsics.areEqual(this.stockTiles, editTilesModel.stockTiles) && Intrinsics.areEqual(this.customTiles, editTilesModel.customTiles);
    }

    public final int hashCode() {
        return this.customTiles.hashCode() + (this.stockTiles.hashCode() * 31);
    }

    public final String toString() {
        return "EditTilesModel(stockTiles=" + this.stockTiles + ", customTiles=" + this.customTiles + ")";
    }
}
