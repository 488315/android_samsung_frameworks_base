package com.android.systemui.qs;

import android.util.Log;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.TileHostable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda31 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ QSPanelHost f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda31(QSPanelHost qSPanelHost, String str, TileHostable tileHostable) {
        this.f$0 = qSPanelHost;
        this.f$2 = str;
        this.f$1 = tileHostable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSPanelHost qSPanelHost = this.f$0;
                List list = (List) this.f$1;
                String str = this.f$2;
                TileHostable tileHostable = (TileHostable) obj;
                qSPanelHost.getClass();
                tileHostable.removeAllTiles();
                list.forEach(new QSPanelHost$$ExternalSyntheticLambda31(qSPanelHost, str, tileHostable));
                break;
            default:
                QSPanelHost qSPanelHost2 = this.f$0;
                String str2 = this.f$2;
                TileHostable tileHostable2 = (TileHostable) this.f$1;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                qSPanelHost2.getClass();
                Log.d("QSPanelHost", str2 + ": tile[ " + tileRecord.tile.getTileSpec() + " ]: record: " + tileRecord);
                Function function = qSPanelHost2.mTileCallbackFunction;
                if (function != null) {
                    SQSTile.SCallback sCallback = (SQSTile.SCallback) function.apply(tileRecord);
                    QSTile qSTile = tileRecord.tile;
                    qSTile.addCallback(sCallback);
                    tileRecord.callback = sCallback;
                    tileRecord.tileView.init(qSTile);
                    qSTile.refreshState();
                }
                tileHostable2.addTile(tileRecord);
                break;
        }
    }

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda31(QSPanelHost qSPanelHost, List list, String str) {
        this.f$0 = qSPanelHost;
        this.f$1 = list;
        this.f$2 = str;
    }
}
