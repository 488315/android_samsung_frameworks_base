package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.DemuxImpl;
import com.android.systemui.kairos.internal.DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;

/* loaded from: classes2.dex */
public final class GroupedEvents {
    public final DemuxImpl impl;

    public GroupedEvents(DemuxImpl demuxImpl) {
        this.impl = demuxImpl;
    }

    public final EventsInit get(Object obj) {
        DemuxImpl demuxImpl = this.impl;
        demuxImpl.getClass();
        return new EventsInit(new Init(null, new InitKt$constInit$1(new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(demuxImpl, obj))));
    }
}
