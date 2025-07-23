package androidx.compose.runtime;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
