package androidx.compose.runtime;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ReferentialEqualityPolicy implements SnapshotMutationPolicy<Object> {
    public static final ReferentialEqualityPolicy INSTANCE = new ReferentialEqualityPolicy();

    private ReferentialEqualityPolicy() {
    }

    @Override // androidx.compose.runtime.SnapshotMutationPolicy
    public final boolean equivalent(Object obj, Object obj2) {
        return obj == obj2;
    }

    public final String toString() {
        return "ReferentialEqualityPolicy";
    }
}
