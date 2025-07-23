package com.android.systemui.statusbar.notification.collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ListEntry extends PipelineEntry {
    public final long mCreationTime;

    public ListEntry(String str, long j) {
        super(str);
        this.mCreationTime = j;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final PipelineEntry getParent() {
        return this.mAttachState.parent;
    }
}
