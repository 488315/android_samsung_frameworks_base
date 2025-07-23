package com.android.systemui.kairos.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class EventsImplKt {
    public static final void removeDownstreamAndDeactivateIfNeeded(NodeConnection nodeConnection, Schedulable schedulable) {
        nodeConnection.schedulerUpstream.removeDownstreamAndDeactivateIfNeeded(schedulable);
    }
}
