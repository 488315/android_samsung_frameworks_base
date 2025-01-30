package com.android.systemui.p016qs.bar;

import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BarType f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda4(BarType barType, int i) {
        this.$r8$classId = i;
        this.f$0 = barType;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.getCls().isInstance((BarItemImpl) obj);
            default:
                return this.f$0.getCls().isInstance((BarItemImpl) obj);
        }
    }
}
