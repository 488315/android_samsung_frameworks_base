package com.android.compose.ui.graphics;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.geometry.Offset;

/* loaded from: classes.dex */
public final class ContainerState {
    public final MutableState lastOffsetInWindow$delegate;
    public final SnapshotStateList renderers = new SnapshotStateList();

    public ContainerState() {
        Offset.Companion.getClass();
        this.lastOffsetInWindow$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl(0L));
    }
}
