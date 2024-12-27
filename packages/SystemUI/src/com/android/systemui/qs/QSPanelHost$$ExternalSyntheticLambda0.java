package com.android.systemui.qs;

import android.metrics.LogMaker;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                SecQSPanel.QSTileLayout qSTileLayout = ((QSPanelHost) obj2).mTileLayout;
                if (qSTileLayout != null) {
                    qSTileLayout.removeTile(tileRecord);
                }
                tileRecord.tile.removeCallback(tileRecord.callback);
                break;
            case 1:
                TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) ((BarItemImpl) obj);
                boolean z = !((List) obj2).isEmpty();
                if (tileChunkLayoutBar.mShowing != z) {
                    tileChunkLayoutBar.showBar(z);
                    break;
                }
                break;
            case 2:
                ((ArrayList) obj2).add((SecQSPanelControllerBase.TileRecord) obj);
                break;
            default:
                ((MetricsLogger) obj2).write((LogMaker) obj);
                break;
        }
    }
}
