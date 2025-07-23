package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.DemuxLifecycleState;
import com.android.systemui.kairos.internal.store.MutableMapK;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DemuxKt {
    public static final DemuxImpl DemuxImpl(Integer num, EventsImpl eventsImpl, MutableMapK.Factory factory) {
        return new DemuxImpl(new DemuxLifecycle(new DemuxLifecycleState.Inactive(new DemuxActivator(num, eventsImpl, factory))));
    }
}
