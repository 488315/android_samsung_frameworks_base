package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface MutableLongState extends State, MutableState<Long> {
    @Override // androidx.compose.runtime.State
    default Object getValue() {
        return Long.valueOf(((SnapshotMutableLongStateImpl) this).getLongValue());
    }

    @Override // androidx.compose.runtime.MutableState
    default void setValue(Object obj) {
        ((SnapshotMutableLongStateImpl) this).setLongValue(((Number) obj).longValue());
    }
}
