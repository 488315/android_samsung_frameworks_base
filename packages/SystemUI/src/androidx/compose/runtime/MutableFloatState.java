package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface MutableFloatState extends FloatState, MutableState<Float> {
    @Override // androidx.compose.runtime.State
    default Object getValue() {
        return Float.valueOf(((SnapshotMutableFloatStateImpl) this).getFloatValue());
    }

    @Override // androidx.compose.runtime.MutableState
    default void setValue(Object obj) {
        ((SnapshotMutableFloatStateImpl) this).setFloatValue(((Number) obj).floatValue());
    }
}
