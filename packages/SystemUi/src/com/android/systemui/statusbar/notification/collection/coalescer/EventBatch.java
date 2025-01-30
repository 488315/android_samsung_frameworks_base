package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EventBatch {
    public ExecutorImpl.ExecutionToken mCancelShortTimeout;
    public final long mCreatedTimestamp;
    public final String mGroupKey;
    public final List mMembers = new ArrayList();

    public EventBatch(long j, String str) {
        this.mCreatedTimestamp = j;
        this.mGroupKey = str;
    }
}
