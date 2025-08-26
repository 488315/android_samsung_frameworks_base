package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.DemuxLifecycleState;
import com.android.systemui.kairos.internal.store.MutableMapK;

/* loaded from: classes2.dex */
public abstract class DemuxKt {
    public static final DemuxImpl DemuxImpl(Integer num, EventsImpl eventsImpl, MutableMapK.Factory factory) {
        return new DemuxImpl(new DemuxLifecycle(new DemuxLifecycleState.Inactive(new DemuxActivator(num, eventsImpl, factory))));
    }
}
