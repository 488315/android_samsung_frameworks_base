package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserSetupRepositoryImpl implements UserSetupRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final DeviceProvisionedController deviceProvisionedController;
    public final ReadonlyStateFlow isUserSetUp;

    public UserSetupRepositoryImpl(DeviceProvisionedController deviceProvisionedController, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.deviceProvisionedController = deviceProvisionedController;
        this.bgDispatcher = coroutineDispatcher;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UserSetupRepositoryImpl$isUserSetUp$1 userSetupRepositoryImpl$isUserSetUp$1 = new UserSetupRepositoryImpl$isUserSetUp$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isUserSetUp = FlowKt.stateIn(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserSetupRepositoryImpl$isUserSetUp$2(null), FlowConflatedKt.conflatedCallbackFlow(userSetupRepositoryImpl$isUserSetUp$1)), new UserSetupRepositoryImpl$isUserSetUp$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }
}
