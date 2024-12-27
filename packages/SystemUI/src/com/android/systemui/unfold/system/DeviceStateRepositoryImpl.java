package com.android.systemui.unfold.system;

import com.android.systemui.unfold.updates.FoldProvider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
