package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutationPolicy;

/* loaded from: classes.dex */
public interface SnapshotMutableState<T> extends MutableState<T> {
    SnapshotMutationPolicy getPolicy();
}
