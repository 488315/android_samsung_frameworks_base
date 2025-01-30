package com.android.systemui.qs;

import android.metrics.LogMaker;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.PagedTileLayoutBar;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((MetricsLogger) this.f$0).write((LogMaker) obj);
                break;
            case 1:
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                SecQSPanel.QSTileLayout qSTileLayout = ((QSPanelHost) this.f$0).mTileLayout;
                if (qSTileLayout != null) {
                    qSTileLayout.removeTile(tileRecord);
                }
                tileRecord.tile.removeCallback(tileRecord.callback);
                break;
            case 2:
                PagedTileLayoutBar pagedTileLayoutBar = (PagedTileLayoutBar) ((BarItemImpl) obj);
                boolean z = !((List) this.f$0).isEmpty();
                if (pagedTileLayoutBar.mShowing != z) {
                    pagedTileLayoutBar.showBar(z);
                    break;
                }
                break;
            default:
                ((ArrayList) this.f$0).add((SecQSPanelControllerBase.TileRecord) obj);
                break;
        }
    }
}
