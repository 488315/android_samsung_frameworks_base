package com.android.systemui.util.kotlin;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class RotationLockControllerExtKt {
    public static final Flow isRotationLockEnabled(RotationLockController rotationLockController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        RotationLockControllerExtKt$isRotationLockEnabled$1 rotationLockControllerExtKt$isRotationLockEnabled$1 = new RotationLockControllerExtKt$isRotationLockEnabled$1(rotationLockController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new RotationLockControllerExtKt$isRotationLockEnabled$2(rotationLockController, null), FlowConflatedKt.conflatedCallbackFlow(rotationLockControllerExtKt$isRotationLockEnabled$1));
    }
}
