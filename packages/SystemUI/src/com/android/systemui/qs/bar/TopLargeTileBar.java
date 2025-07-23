package com.android.systemui.qs.bar;

import android.content.Context;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.animator.QsAnimatorState;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TopLargeTileBar extends LargeTileBar {
    public final ArrayList mOtherTileViews;

    public TopLargeTileBar(Context context) {
        super(context);
        this.mOtherTileViews = new ArrayList();
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        super.addTile(tileRecord);
        this.mOtherTileViews.clear();
        this.mTiles.forEach(new Consumer() { // from class: com.android.systemui.qs.bar.TopLargeTileBar$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileView qSTileView;
                TopLargeTileBar topLargeTileBar = TopLargeTileBar.this;
                SecQSPanelControllerBase.TileRecord tileRecord2 = (SecQSPanelControllerBase.TileRecord) obj;
                topLargeTileBar.getClass();
                if (QsAnimatorState.isDetailPopupShowing && !QsAnimatorState.isDetailPopupClosing && tileRecord2 != null && (qSTileView = tileRecord2.tileView) != null) {
                    qSTileView.setAlpha(0.0f);
                }
                if (tileRecord2.tile.getTileSpec().equals("Wifi") || tileRecord2.tile.getTileSpec().equals("Bluetooth")) {
                    return;
                }
                topLargeTileBar.mOtherTileViews.add(tileRecord2.tileView);
            }
        });
    }
}
