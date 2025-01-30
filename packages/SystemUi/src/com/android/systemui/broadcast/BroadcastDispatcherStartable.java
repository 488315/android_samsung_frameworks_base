package com.android.systemui.broadcast;

import com.android.systemui.CoreStartable;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BroadcastDispatcherStartable implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;

    public BroadcastDispatcherStartable(BroadcastDispatcher broadcastDispatcher) {
        this.broadcastDispatcher = broadcastDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        DumpManager.registerDumpable$default(broadcastDispatcher.dumpManager, BroadcastDispatcher.class.getName(), broadcastDispatcher);
    }
}
