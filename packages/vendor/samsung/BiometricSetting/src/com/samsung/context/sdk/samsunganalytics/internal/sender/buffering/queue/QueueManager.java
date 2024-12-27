package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue;

import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class QueueManager {
    public final LinkedBlockingQueue logQueue = new LinkedBlockingQueue(25);
}
