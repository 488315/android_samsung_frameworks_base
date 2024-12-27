package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue;

import java.util.concurrent.LinkedBlockingQueue;

public final class QueueManager {
    public final LinkedBlockingQueue logQueue = new LinkedBlockingQueue(25);
}
