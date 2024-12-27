package com.android.systemui.qs;

import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.bar.BarItemImpl;
import java.util.function.Predicate;

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
