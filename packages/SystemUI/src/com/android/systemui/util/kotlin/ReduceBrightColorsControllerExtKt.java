package com.android.systemui.util.kotlin;

import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ReduceBrightColorsControllerExtKt {
    public static final Flow isEnabled(ReduceBrightColorsController reduceBrightColorsController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ReduceBrightColorsControllerExtKt$isEnabled$2(reduceBrightColorsController, null), FlowConflatedKt.conflatedCallbackFlow(new ReduceBrightColorsControllerExtKt$isEnabled$1(reduceBrightColorsController, null)));
    }
}
