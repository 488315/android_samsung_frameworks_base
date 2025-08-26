package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MutableMapK;

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
