package com.android.systemui.p016qs.animator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSFragmentAnimatorManager$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Object obj2;
        switch (this.$r8$classId) {
            case 0:
            default:
                obj2 = (SecQSFragmentAnimatorBase) obj;
                break;
            case 1:
                obj2 = (ArrayList) obj;
                break;
        }
        return Objects.nonNull(obj2);
    }
}
