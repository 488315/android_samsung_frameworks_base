package com.android.systemui.util.kotlin;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ManagedProfileControllerExtKt {
    public static final Flow getHasActiveWorkProfile(ManagedProfileController managedProfileController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ManagedProfileControllerExtKt$hasActiveWorkProfile$1 managedProfileControllerExtKt$hasActiveWorkProfile$1 = new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(managedProfileController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(managedProfileControllerExtKt$hasActiveWorkProfile$1);
    }
}
