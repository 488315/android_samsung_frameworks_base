package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface MutableIntState extends State, MutableState<Integer> {
    @Override // androidx.compose.runtime.State
    default Object getValue() {
        return Integer.valueOf(((SnapshotMutableIntStateImpl) this).getIntValue());
    }

    @Override // androidx.compose.runtime.MutableState
    default void setValue(Object obj) {
        ((SnapshotMutableIntStateImpl) this).setIntValue(((Number) obj).intValue());
    }
}
