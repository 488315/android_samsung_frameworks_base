package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue;

import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public final class QueueManager {
    protected LinkedBlockingQueue<SimpleLog> logQueue = new LinkedBlockingQueue<>(25);

    public final Queue<SimpleLog> getAll() {
        return this.logQueue;
    }

    public final void insert(SimpleLog simpleLog) {
        if (this.logQueue.offer(simpleLog)) {
            return;
        }
        Debug.LogD("QueueManager", "queue size over. remove oldest log");
        this.logQueue.poll();
        this.logQueue.offer(simpleLog);
    }
}
