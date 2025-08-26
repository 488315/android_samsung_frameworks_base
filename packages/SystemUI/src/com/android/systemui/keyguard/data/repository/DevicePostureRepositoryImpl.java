package com.android.systemui.keyguard.data.repository;

import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class DevicePostureRepositoryImpl implements DevicePostureRepository {
    public final CoroutineDispatcher mainDispatcher;
    public final DevicePostureController postureController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DevicePostureRepositoryImpl$currentDevicePosture$1(this, null)), this.mainDispatcher);
    }
}
