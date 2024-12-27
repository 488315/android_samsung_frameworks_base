package com.android.systemui.qs.bar;

import android.content.Context;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TopLargeTileBar extends LargeTileBar {
    public QSTileView mBluetoothTileView;
    public final ArrayList mOtherTileViews;
    public QSTileView mWifiTileView;

    public TopLargeTileBar(Context context, QSHost qSHost) {
        super(context, qSHost);
        this.mOtherTileViews = new ArrayList();
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        super.addTile(tileRecord);
        this.mOtherTileViews.clear();
        this.mTiles.forEach(new Consumer() { // from class: com.android.systemui.qs.bar.TopLargeTileBar$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TopLargeTileBar topLargeTileBar = TopLargeTileBar.this;
                SecQSPanelControllerBase.TileRecord tileRecord2 = (SecQSPanelControllerBase.TileRecord) obj;
                topLargeTileBar.getClass();
                boolean equals = tileRecord2.tile.getTileSpec().equals("Wifi");
                QSTileView qSTileView = tileRecord2.tileView;
                if (equals) {
                    topLargeTileBar.mWifiTileView = qSTileView;
                } else if (tileRecord2.tile.getTileSpec().equals("Bluetooth")) {
                    topLargeTileBar.mBluetoothTileView = qSTileView;
                } else {
                    topLargeTileBar.mOtherTileViews.add(qSTileView);
                }
            }
        });
    }
}
