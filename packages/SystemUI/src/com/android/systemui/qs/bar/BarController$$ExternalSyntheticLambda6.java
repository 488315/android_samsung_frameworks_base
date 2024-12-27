package com.android.systemui.qs.bar;

import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BarType f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda6(BarType barType, int i) {
        this.$r8$classId = i;
        this.f$0 = barType;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        BarType barType = this.f$0;
        BarItemImpl barItemImpl = (BarItemImpl) obj;
        switch (i) {
        }
        return barType.getCls().isInstance(barItemImpl);
    }
}
