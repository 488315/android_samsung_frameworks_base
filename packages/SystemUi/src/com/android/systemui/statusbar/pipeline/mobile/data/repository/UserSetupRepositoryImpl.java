package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserSetupRepositoryImpl implements UserSetupRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final DeviceProvisionedController deviceProvisionedController;
    public final ReadonlyStateFlow isUserSetupFlow;

    public UserSetupRepositoryImpl(DeviceProvisionedController deviceProvisionedController, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.deviceProvisionedController = deviceProvisionedController;
        this.bgDispatcher = coroutineDispatcher;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UserSetupRepositoryImpl$isUserSetupFlow$1 userSetupRepositoryImpl$isUserSetupFlow$1 = new UserSetupRepositoryImpl$isUserSetupFlow$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isUserSetupFlow = FlowKt.stateIn(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserSetupRepositoryImpl$isUserSetupFlow$2(null), ConflatedCallbackFlow.conflatedCallbackFlow(userSetupRepositoryImpl$isUserSetupFlow$1)), new UserSetupRepositoryImpl$isUserSetupFlow$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), Boolean.FALSE);
    }
}
