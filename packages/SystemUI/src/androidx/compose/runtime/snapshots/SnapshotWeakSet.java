package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotWeakSet<T> {
    public int size;
    public int[] hashes = new int[16];
    public WeakReference[] values = new WeakReference[16];
}
