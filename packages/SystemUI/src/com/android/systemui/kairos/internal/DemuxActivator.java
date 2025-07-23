package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MutableMapK;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DemuxActivator {
    public final Integer numKeys;
    public final MutableMapK.Factory storeFactory;
    public final EventsImpl upstream;

    public DemuxActivator(Integer num, EventsImpl eventsImpl, MutableMapK.Factory factory) {
        this.numKeys = num;
        this.upstream = eventsImpl;
        this.storeFactory = factory;
    }
}
