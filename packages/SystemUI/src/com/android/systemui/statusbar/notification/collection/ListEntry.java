package com.android.systemui.statusbar.notification.collection;

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
