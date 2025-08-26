package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.tileimpl.LabelTileView;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecTileChunkLayout$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecTileChunkLayout$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = SecTileChunkLayout.$r8$clinit;
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            case 1:
                int i2 = SecTileChunkLayout.$r8$clinit;
                return ((SecQSPanelControllerBase.TileRecord) obj).tileView;
            case 2:
                int i3 = SecTileChunkLayout.$r8$clinit;
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            case 3:
                int i4 = SecTileChunkLayout.$r8$clinit;
                QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) obj).tileView;
                if (qSTileView instanceof LabelTileView) {
                    return (LabelTileView) qSTileView;
                }
                return null;
            case 4:
                int i5 = SecTileChunkLayout.$r8$clinit;
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) ((IndexedValue) obj).value).tileView.getVisibility() != 8);
            case 5:
                int i6 = SecTileChunkLayout.$r8$clinit;
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            default:
                int i7 = SecTileChunkLayout.$r8$clinit;
                QSTileView qSTileView2 = ((SecQSPanelControllerBase.TileRecord) obj).tileView;
                if (qSTileView2 instanceof LabelTileView) {
                    return (LabelTileView) qSTileView2;
                }
                return null;
        }
    }
}
