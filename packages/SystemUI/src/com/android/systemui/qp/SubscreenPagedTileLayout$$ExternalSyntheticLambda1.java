package com.android.systemui.qp;

import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenPagedTileLayout$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = SubscreenPagedTileLayout.$r8$clinit;
        return ((QSPanelControllerBase$TileRecord) obj).tile.getTileSpec();
    }
}
