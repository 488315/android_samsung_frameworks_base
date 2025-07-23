package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.kairos.CoalescingMutableEvents;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
