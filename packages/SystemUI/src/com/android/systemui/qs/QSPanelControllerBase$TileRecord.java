package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSPanel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class QSPanelControllerBase$TileRecord {
    public QSPanel.AnonymousClass1 callback;
    public final QSTile tile;
    public final QSTileView tileView;

    public QSPanelControllerBase$TileRecord(QSTile qSTile, QSTileView qSTileView) {
        this.tile = qSTile;
        this.tileView = qSTileView;
    }
}
