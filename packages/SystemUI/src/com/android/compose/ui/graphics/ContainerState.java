package com.android.compose.ui.graphics;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.geometry.Offset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContainerState {
    public final MutableState lastOffsetInWindow$delegate;
    public final SnapshotStateList renderers = new SnapshotStateList();

    public ContainerState() {
        Offset.Companion.getClass();
        this.lastOffsetInWindow$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(0L));
    }
}
