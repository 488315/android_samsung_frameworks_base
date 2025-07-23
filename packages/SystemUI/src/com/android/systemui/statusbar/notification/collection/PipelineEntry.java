package com.android.systemui.statusbar.notification.collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PipelineEntry {
    public final String mKey;
    public final ListAttachState mAttachState = ListAttachState.create();
    public final ListAttachState mPreviousAttachState = ListAttachState.create();
    public int mBucket = 15;

    public PipelineEntry(String str) {
        this.mKey = str;
    }

    public final void beginNewAttachState() {
        ListAttachState listAttachState = this.mPreviousAttachState;
        listAttachState.getClass();
        ListAttachState listAttachState2 = this.mAttachState;
        listAttachState.parent = listAttachState2.parent;
        listAttachState.section = listAttachState2.section;
        listAttachState.excludingFilter = listAttachState2.excludingFilter;
        listAttachState.promoter = listAttachState2.promoter;
        listAttachState.groupPruneReason = listAttachState2.groupPruneReason;
        SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
        suppressedAttachState.getClass();
        SuppressedAttachState suppressedAttachState2 = listAttachState2.suppressedChanges;
        suppressedAttachState.parent = suppressedAttachState2.parent;
        suppressedAttachState.section = suppressedAttachState2.section;
        suppressedAttachState.wasPruneSuppressed = suppressedAttachState2.wasPruneSuppressed;
        listAttachState.stableIndex = listAttachState2.stableIndex;
        listAttachState2.parent = null;
        listAttachState2.section = null;
        listAttachState2.excludingFilter = null;
        listAttachState2.promoter = null;
        listAttachState2.groupPruneReason = null;
        SuppressedAttachState suppressedAttachState3 = listAttachState2.suppressedChanges;
        suppressedAttachState3.parent = null;
        suppressedAttachState3.section = null;
        suppressedAttachState3.wasPruneSuppressed = false;
        listAttachState2.stableIndex = -1;
    }

    public String getKey() {
        return this.mKey;
    }

    public abstract PipelineEntry getParent();

    public abstract NotificationEntry getRepresentativeEntry();

    public boolean wasAttachedInPreviousPass() {
        return this.mPreviousAttachState.parent != null;
    }
}
