package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class DevicePostureRepositoryImpl implements DevicePostureRepository {
    public final CoroutineDispatcher mainDispatcher;
    public final DevicePostureController postureController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DevicePostureRepositoryImpl(DevicePostureController devicePostureController, CoroutineDispatcher coroutineDispatcher) {
        this.postureController = devicePostureController;
        this.mainDispatcher = coroutineDispatcher;
    }

    public final Flow getCurrentDevicePosture() {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DevicePostureRepositoryImpl$currentDevicePosture$1 devicePostureRepositoryImpl$currentDevicePosture$1 = new DevicePostureRepositoryImpl$currentDevicePosture$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(devicePostureRepositoryImpl$currentDevicePosture$1), this.mainDispatcher);
    }
}
