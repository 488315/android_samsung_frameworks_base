package com.android.systemui.qs.bar;

import java.util.function.Predicate;

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
