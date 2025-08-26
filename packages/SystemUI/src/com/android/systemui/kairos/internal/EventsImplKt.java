package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public abstract class EventsImplKt {
    public static final void removeDownstreamAndDeactivateIfNeeded(NodeConnection nodeConnection, Schedulable schedulable) {
        nodeConnection.schedulerUpstream.removeDownstreamAndDeactivateIfNeeded(schedulable);
    }
}
