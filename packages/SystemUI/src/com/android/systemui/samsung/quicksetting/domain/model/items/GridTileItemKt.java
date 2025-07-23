package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class GridTileItemKt {
    public static final String getQueryString(GridTileItem gridTileItem) {
        if (!(gridTileItem instanceof QuickTile)) {
            if (!(gridTileItem instanceof QuickButton)) {
                return gridTileItem.getType();
            }
            QuickButton quickButton = (QuickButton) gridTileItem;
            StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(quickButton.type);
            m.append(quickButton.spec);
            return m.toString();
        }
        QuickTile quickTile = (QuickTile) gridTileItem;
        return quickTile.type + ((Object) quickTile.tile.getTileLabel());
    }
}
