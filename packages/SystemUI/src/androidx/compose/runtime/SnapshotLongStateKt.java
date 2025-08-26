package androidx.compose.runtime;

/* loaded from: classes.dex */
public abstract class SnapshotLongStateKt {
    public static final MutableLongState mutableLongStateOf(long j) {
        return new ParcelableSnapshotMutableLongState(j);
    }
}
