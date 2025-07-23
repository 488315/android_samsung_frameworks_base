package com.android.systemui.brightness.shared.model;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class GammaBrightnessKt {
    /* renamed from: logDiffForTable-GAU2kQA, reason: not valid java name */
    public static final Flow m1066logDiffForTableGAU2kQA(FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, final TableLogBuffer tableLogBuffer) {
        return FlowKt.pairwiseBy((Flow) flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, (Function1) new GammaBrightnessKt$logDiffForTable$1(new Function0() { // from class: com.android.systemui.brightness.shared.model.GammaBrightnessKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange("gamma", "brightness", (Integer) null, true);
                return null;
            }
        }), (Function3) new GammaBrightnessKt$logDiffForTable$2(tableLogBuffer, "gamma", "brightness", null));
    }
}
