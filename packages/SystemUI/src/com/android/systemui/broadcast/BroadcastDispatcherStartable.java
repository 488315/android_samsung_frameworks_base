package com.android.systemui.broadcast;

import com.android.systemui.CoreStartable;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
