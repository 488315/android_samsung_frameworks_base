package com.android.systemui.unfold.system;

import com.android.systemui.unfold.updates.FoldProvider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class DeviceStateRepositoryImpl implements DeviceStateRepository {
    public final Executor executor;
    public final FoldProvider foldProvider;

    public DeviceStateRepositoryImpl(FoldProvider foldProvider, Executor executor) {
        this.foldProvider = foldProvider;
        this.executor = executor;
    }

    public final Flow isFolded() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new DeviceStateRepositoryImpl$isFolded$1(this, null)), -1);
    }
}
