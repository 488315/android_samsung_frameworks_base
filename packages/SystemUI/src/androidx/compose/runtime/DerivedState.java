package androidx.compose.runtime;

import androidx.compose.runtime.DerivedSnapshotState;

/* loaded from: classes.dex */
public interface DerivedState<T> extends State<T> {
    DerivedSnapshotState.ResultRecord getCurrentRecord();

    SnapshotMutationPolicy getPolicy();
}
