package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda17 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda17(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        switch (i) {
            case 0:
                return str.equals(((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec());
            case 1:
                return Objects.equals(((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec(), str);
            default:
                return ((QSTile) obj).getTileSpec().equals(str);
        }
    }
}
