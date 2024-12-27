package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSPanel;

public class QSPanelControllerBase$TileRecord {
    public QSPanel.AnonymousClass1 callback;
    public final QSTile tile;
    public final QSTileView tileView;

    public QSPanelControllerBase$TileRecord(QSTile qSTile, QSTileView qSTileView) {
        this.tile = qSTile;
        this.tileView = qSTileView;
    }
}
