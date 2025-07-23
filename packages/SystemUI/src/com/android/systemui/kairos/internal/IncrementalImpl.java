package com.android.systemui.kairos.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IncrementalImpl extends StateImpl {
    public final EventsImpl patches;

    public IncrementalImpl(String str, String str2, EventsImpl eventsImpl, EventsImpl eventsImpl2, StateStore stateStore) {
        super(str, str2, eventsImpl, stateStore);
        this.patches = eventsImpl2;
    }
}
