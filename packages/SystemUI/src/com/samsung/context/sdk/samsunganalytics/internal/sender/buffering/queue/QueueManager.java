package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue;

import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public class QueueManager {
    public final LinkedBlockingQueue logQueue = new LinkedBlockingQueue(25);
}
