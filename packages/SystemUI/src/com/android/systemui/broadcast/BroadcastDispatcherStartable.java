package com.android.systemui.broadcast;

import com.android.systemui.CoreStartable;
import com.android.systemui.dump.DumpManager;

public final class BroadcastDispatcherStartable implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;

    public BroadcastDispatcherStartable(BroadcastDispatcher broadcastDispatcher) {
        this.broadcastDispatcher = broadcastDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        String name = BroadcastDispatcher.class.getName();
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        DumpManager.registerDumpable$default(broadcastDispatcher.dumpManager, name, broadcastDispatcher);
    }
}
