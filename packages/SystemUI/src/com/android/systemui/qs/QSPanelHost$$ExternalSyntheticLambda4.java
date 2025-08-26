package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSPanelHost f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda4(QSPanelHost qSPanelHost, int i) {
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
                SecQSPanelControllerBase$$ExternalSyntheticLambda1 secQSPanelControllerBase$$ExternalSyntheticLambda1 = qSPanelHost.mTileCallbackFunction;
                if (secQSPanelControllerBase$$ExternalSyntheticLambda1 != null) {
                    SQSTile.SCallback sCallback = (SQSTile.SCallback) secQSPanelControllerBase$$ExternalSyntheticLambda1.apply(tileRecord);
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
