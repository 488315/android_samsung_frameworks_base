package com.android.systemui.qs.animator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
