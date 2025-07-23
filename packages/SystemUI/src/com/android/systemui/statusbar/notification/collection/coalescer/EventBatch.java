package com.android.systemui.statusbar.notification.collection.coalescer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class EventBatch {
    public Runnable mCancelShortTimeout;
    public final long mCreatedTimestamp;
    public final String mGroupKey;
    public final List mMembers = new ArrayList();

    public EventBatch(long j, String str) {
        this.mCreatedTimestamp = j;
        this.mGroupKey = str;
    }
}
