package com.android.systemui.qs.animator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda16 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda16(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Objects.nonNull((SecQSImplAnimatorBase) obj);
            default:
                return Objects.nonNull((ArrayList) obj);
        }
    }
}
