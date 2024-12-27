package com.android.systemui.qs;

import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.bar.BarItemImpl;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda25 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda25(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((QSTile) obj).getDetailAdapter() == ((DetailAdapter) obj2);
            default:
                return ((ToBooleanFunction) obj2).apply((BarItemImpl) obj);
        }
    }
}
