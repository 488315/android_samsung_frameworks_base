package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public final class IncrementalImpl extends StateImpl {
    public final EventsImpl patches;

    public IncrementalImpl(String str, String str2, EventsImpl eventsImpl, EventsImpl eventsImpl2, StateStore stateStore) {
        super(str, str2, eventsImpl, stateStore);
        this.patches = eventsImpl2;
    }
}
