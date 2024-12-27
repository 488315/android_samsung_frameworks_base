package com.android.server.accessibility;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
