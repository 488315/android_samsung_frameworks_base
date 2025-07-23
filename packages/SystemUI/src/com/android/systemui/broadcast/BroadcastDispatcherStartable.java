package com.android.systemui.broadcast;

import com.android.systemui.CoreStartable;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BroadcastDispatcherStartable implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;

    public BroadcastDispatcherStartable(BroadcastDispatcher broadcastDispatcher) {
        this.broadcastDispatcher = broadcastDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        DumpManager.registerDumpable$default(broadcastDispatcher.dumpManager, broadcastDispatcher.getClass().getName(), broadcastDispatcher);
    }
}
