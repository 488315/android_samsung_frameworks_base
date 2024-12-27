package com.android.systemui.util.kotlin;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

public final class ManagedProfileControllerExtKt {
    public static final Flow getHasActiveWorkProfile(ManagedProfileController managedProfileController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ManagedProfileControllerExtKt$hasActiveWorkProfile$1 managedProfileControllerExtKt$hasActiveWorkProfile$1 = new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(managedProfileController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(managedProfileControllerExtKt$hasActiveWorkProfile$1);
    }
}
