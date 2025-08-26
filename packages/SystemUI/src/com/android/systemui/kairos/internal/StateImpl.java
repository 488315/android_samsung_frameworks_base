package com.android.systemui.kairos.internal;

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
