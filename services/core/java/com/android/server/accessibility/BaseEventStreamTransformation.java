package com.android.server.accessibility;

public abstract class BaseEventStreamTransformation implements EventStreamTransformation {
    public EventStreamTransformation mNext;

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final EventStreamTransformation getNext() {
        return this.mNext;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void setNext(EventStreamTransformation eventStreamTransformation) {
        this.mNext = eventStreamTransformation;
    }
}
