package com.android.systemui.qs.tiles.impl.custom.commons;

import android.service.quicksettings.Tile;

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
