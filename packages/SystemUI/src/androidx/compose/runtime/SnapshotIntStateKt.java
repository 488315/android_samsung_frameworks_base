package androidx.compose.runtime;

/* loaded from: classes.dex */
public abstract class SnapshotIntStateKt {
    public static final MutableIntState mutableIntStateOf(int i) {
        return new ParcelableSnapshotMutableIntState(i);
    }
}
