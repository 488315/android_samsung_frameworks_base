package com.android.systemui.kairos;

/* loaded from: classes2.dex */
public final class StateSelector {
    public final GroupedEvents groupedChanges;
    public final State upstream;

    public StateSelector(State state, GroupedEvents groupedEvents) {
        this.upstream = state;
        this.groupedChanges = groupedEvents;
    }
}
