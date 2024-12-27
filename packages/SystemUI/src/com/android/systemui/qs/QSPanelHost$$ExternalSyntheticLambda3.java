package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.function.Function;

public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSPanelHost f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda3(QSPanelHost qSPanelHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSPanelHost;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        QSPanelHost qSPanelHost = this.f$0;
        QSTile qSTile = (QSTile) obj;
        switch (i) {
            case 0:
                SecQSPanelControllerBase.TileRecord tileRecord = new SecQSPanelControllerBase.TileRecord(qSTile, qSPanelHost.createTileView(qSTile, qSPanelHost.isHeader()));
                Function function = qSPanelHost.mTileCallbackFunction;
                if (function != null) {
                    SQSTile.SCallback sCallback = (SQSTile.SCallback) function.apply(tileRecord);
                    QSTile qSTile2 = tileRecord.tile;
                    qSTile2.addCallback(sCallback);
                    tileRecord.callback = sCallback;
                    tileRecord.tileView.init(qSTile2);
                    qSTile2.refreshState();
                }
                SecQSPanel.QSTileLayout qSTileLayout = qSPanelHost.mTileLayout;
                if (qSTileLayout != null) {
                    qSTileLayout.addTile(tileRecord);
                }
                return tileRecord;
            default:
                return new SecQSPanelControllerBase.TileRecord(qSTile, qSPanelHost.createTileView(qSTile, qSPanelHost.isHeader()));
        }
    }
}
