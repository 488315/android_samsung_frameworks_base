package androidx.compose.runtime.snapshots;

/* loaded from: classes.dex */
public abstract class SnapshotStateListKt {
    public static final Object sync = new Object();

    public static final void access$validateRange(int i, int i2) {
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index (" + i + ") is out of bound of [0, " + i2 + ')');
        }
    }
}
