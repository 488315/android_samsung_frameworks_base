package com.android.systemui.util.leak;

import com.android.systemui.util.leak.TrackedObjects;
import java.util.Collection;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class LeakDetector$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return !(((Collection) obj) instanceof TrackedObjects.TrackedClass);
            default:
                return ((Collection) obj) instanceof TrackedObjects.TrackedClass;
        }
    }
}
