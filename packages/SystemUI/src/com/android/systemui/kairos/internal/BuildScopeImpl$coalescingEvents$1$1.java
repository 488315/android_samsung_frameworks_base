package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.kairos.CoalescingMutableEvents;

/* loaded from: classes2.dex */
public final class BuildScopeImpl$coalescingEvents$1$1 implements CoalescingEventProducerScope {
    public final /* synthetic */ CoalescingMutableEvents $events;

    public BuildScopeImpl$coalescingEvents$1$1(CoalescingMutableEvents coalescingMutableEvents) {
        this.$events = coalescingMutableEvents;
    }

    public final void emit(Object obj) {
        this.$events.emit(obj);
    }
}
