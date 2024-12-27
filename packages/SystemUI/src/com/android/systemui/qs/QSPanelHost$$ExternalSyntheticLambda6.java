package com.android.systemui.qs;

import android.metrics.LogMaker;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileHostable;
import java.util.function.Function;

public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda6 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec();
            case 1:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            case 2:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            case 3:
                return ((SecQSPanelControllerBase.TileRecord) obj).tileView;
            case 4:
                return (TileHostable) ((BarItemImpl) obj);
            case 5:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            case 6:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            default:
                QSTile qSTile = (QSTile) obj;
                return qSTile.populate(new LogMaker(qSTile.getMetricsCategory()).setType(1));
        }
    }
}
