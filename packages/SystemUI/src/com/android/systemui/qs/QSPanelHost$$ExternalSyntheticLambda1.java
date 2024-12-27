package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import java.util.function.Predicate;

public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BarItemImpl) obj) instanceof TileChunkLayoutBar;
            case 1:
                return !((QSTile) obj).isListening();
            default:
                return ((SecQSPanelControllerBase.TileRecord) obj).tile instanceof Dumpable;
        }
    }
}
