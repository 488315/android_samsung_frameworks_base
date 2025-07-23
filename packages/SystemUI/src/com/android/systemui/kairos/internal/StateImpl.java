package com.android.systemui.kairos.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class StateImpl {
    public final EventsImpl changes;
    public final String name;
    public final String operatorName;
    public final StateStore store;

    public StateImpl(String str, String str2, EventsImpl eventsImpl, StateStore stateStore) {
        this.name = str;
        this.operatorName = str2;
        this.changes = eventsImpl;
        this.store = stateStore;
    }
}
