package com.android.systemui.qs.tiles.impl.custom.commons;

import android.service.quicksettings.Tile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class TileExtKt {
    public static final Tile copy(Tile tile) {
        Tile tile2 = new Tile();
        tile2.setIcon(tile.getIcon());
        tile2.setLabel(tile.getLabel());
        tile2.setSubtitle(tile.getSubtitle());
        tile2.setContentDescription(tile.getContentDescription());
        tile2.setStateDescription(tile.getStateDescription());
        tile2.setActivityLaunchForClick(tile.getActivityLaunchForClick());
        tile2.setState(tile.getState());
        return tile2;
    }
}
