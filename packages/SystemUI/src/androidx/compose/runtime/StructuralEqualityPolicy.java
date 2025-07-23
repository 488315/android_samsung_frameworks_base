package androidx.compose.runtime;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StructuralEqualityPolicy implements SnapshotMutationPolicy<Object> {
    public static final StructuralEqualityPolicy INSTANCE = new StructuralEqualityPolicy();

    private StructuralEqualityPolicy() {
    }

    @Override // androidx.compose.runtime.SnapshotMutationPolicy
    public final boolean equivalent(Object obj, Object obj2) {
        return Intrinsics.areEqual(obj, obj2);
    }

    public final String toString() {
        return "StructuralEqualityPolicy";
    }
}
