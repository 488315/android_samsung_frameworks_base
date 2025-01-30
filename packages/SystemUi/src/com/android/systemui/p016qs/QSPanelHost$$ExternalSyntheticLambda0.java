package com.android.systemui.p016qs;

import android.metrics.LogMaker;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.p016qs.bar.BarItemImpl;
import com.android.systemui.p016qs.bar.TileHostable;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda0(int i) {
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
                QSTile qSTile = (QSTile) obj;
                return qSTile.populate(new LogMaker(qSTile.getMetricsCategory()).setType(1));
            case 3:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            case 4:
                return ((SecQSPanelControllerBase.TileRecord) obj).tileView;
            case 5:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            case 6:
                return (TileHostable) ((BarItemImpl) obj);
            case 7:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile;
            default:
                return ((SecQSPanelControllerBase.TileRecord) obj).tileView;
        }
    }
}
