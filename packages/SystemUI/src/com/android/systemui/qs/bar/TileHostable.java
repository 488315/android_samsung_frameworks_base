package com.android.systemui.qs.bar;

import com.android.systemui.qs.SecQSPanelControllerBase;

public interface TileHostable {
    void addTile(SecQSPanelControllerBase.TileRecord tileRecord);

    void removeAllTiles();
}
