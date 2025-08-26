package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.WeakReference;

/* loaded from: classes.dex */
public final class SnapshotWeakSet<T> {
    public int size;
    public int[] hashes = new int[16];
    public WeakReference[] values = new WeakReference[16];
}
