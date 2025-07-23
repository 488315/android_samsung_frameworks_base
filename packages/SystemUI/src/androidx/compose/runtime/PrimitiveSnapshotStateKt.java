package androidx.compose.runtime;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PrimitiveSnapshotStateKt {
    public static final MutableFloatState mutableFloatStateOf(float f) {
        return new ParcelableSnapshotMutableFloatState(f);
    }
}
