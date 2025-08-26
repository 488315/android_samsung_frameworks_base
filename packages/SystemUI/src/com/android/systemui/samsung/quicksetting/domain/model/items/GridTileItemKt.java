package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public abstract class GridTileItemKt {
    public static final String getQueryString(GridTileItem gridTileItem) {
        if (!(gridTileItem instanceof QuickTile)) {
            if (!(gridTileItem instanceof QuickButton)) {
                return gridTileItem.getType();
            }
            QuickButton quickButton = (QuickButton) gridTileItem;
            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(quickButton.type);
            sbM.append(quickButton.spec);
            return sbM.toString();
        }
        QuickTile quickTile = (QuickTile) gridTileItem;
        return quickTile.type + ((Object) quickTile.tile.getTileLabel());
    }
}
