package com.android.systemui.util.kotlin;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ReduceBrightColorsControllerExtKt {
    public static final Flow isEnabled(ReduceBrightColorsController reduceBrightColorsController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ReduceBrightColorsControllerExtKt$isEnabled$1 reduceBrightColorsControllerExtKt$isEnabled$1 = new ReduceBrightColorsControllerExtKt$isEnabled$1(reduceBrightColorsController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ReduceBrightColorsControllerExtKt$isEnabled$2(reduceBrightColorsController, null), FlowConflatedKt.conflatedCallbackFlow(reduceBrightColorsControllerExtKt$isEnabled$1));
    }
}
