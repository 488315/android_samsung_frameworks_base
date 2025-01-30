package com.android.systemui.p016qs;

import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda5(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.equals(((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec());
            case 1:
                return ((QSTile) obj).getTileSpec().equals(this.f$0);
            default:
                return Objects.equals(((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec(), this.f$0);
        }
    }
}
