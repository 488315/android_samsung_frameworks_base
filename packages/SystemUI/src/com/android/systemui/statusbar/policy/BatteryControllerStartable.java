package com.android.systemui.statusbar.policy;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;

public final class BatteryControllerStartable implements CoreStartable {
    public final Executor mBackgroundExecutor;

    public BatteryControllerStartable(BatteryController batteryController, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mBackgroundExecutor = executor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.FEATURE_FLAGS.getClass();
    }
}
