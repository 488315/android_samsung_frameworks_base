package com.android.systemui.statusbar.policy;

import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BatteryControllerStartable implements CoreStartable {
    public final Executor mBackgroundExecutor;

    public BatteryControllerStartable(BatteryController batteryController, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mBackgroundExecutor = executor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
