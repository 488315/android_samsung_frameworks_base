package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class UserSetupRepositoryImpl implements UserSetupRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final DeviceProvisionedController deviceProvisionedController;
    public final ReadonlyStateFlow isUserSetUp;

    public UserSetupRepositoryImpl(DeviceProvisionedController deviceProvisionedController, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.deviceProvisionedController = deviceProvisionedController;
        this.bgDispatcher = coroutineDispatcher;
        this.isUserSetUp = FlowKt.stateIn(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserSetupRepositoryImpl$isUserSetUp$2(null), FlowConflatedKt.conflatedCallbackFlow(new UserSetupRepositoryImpl$isUserSetUp$1(this, null))), new UserSetupRepositoryImpl$isUserSetUp$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }
}
