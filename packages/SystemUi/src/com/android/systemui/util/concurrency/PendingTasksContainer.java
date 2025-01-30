package com.android.systemui.util.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PendingTasksContainer {
    public volatile AtomicInteger pendingTasksCount = new AtomicInteger(0);
    public volatile AtomicReference completionCallback = new AtomicReference();
}
