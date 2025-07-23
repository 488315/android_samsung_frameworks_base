package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RotationLockControllerExtKt {
    public static final Flow isRotationLockEnabled(RotationLockController rotationLockController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new RotationLockControllerExtKt$isRotationLockEnabled$2(rotationLockController, null), FlowConflatedKt.conflatedCallbackFlow(new RotationLockControllerExtKt$isRotationLockEnabled$1(rotationLockController, null)));
    }
}
