package androidx.compose.runtime;

/* loaded from: classes.dex */
public abstract class PrimitiveSnapshotStateKt {
    public static final MutableFloatState mutableFloatStateOf(float f) {
        return new ParcelableSnapshotMutableFloatState(f);
    }
}
