package com.android.systemui.qs.animator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

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
