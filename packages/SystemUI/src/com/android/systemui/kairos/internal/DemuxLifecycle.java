package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.util.UtilKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class DemuxLifecycle {
    public volatile DemuxLifecycleState lifecycleState;
    public final MutexImpl mutex = MutexKt.Mutex$default();

    public DemuxLifecycle(DemuxLifecycleState demuxLifecycleState) {
        this.lifecycleState = demuxLifecycleState;
    }

    public final String toString() {
        return "EventsDmuxState[" + UtilKt.getHashString(this) + "][" + this.lifecycleState + "][" + this.mutex + "]";
    }
}
