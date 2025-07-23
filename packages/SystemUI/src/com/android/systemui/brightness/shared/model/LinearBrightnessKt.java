package com.android.systemui.brightness.shared.model;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Arrays;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LinearBrightnessKt {
    public static final String formatBrightness(float f) {
        return String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
    }

    /* renamed from: logDiffForTable-CVGC-8U, reason: not valid java name */
    public static final Flow m1069logDiffForTableCVGC8U(Flow flow, final TableLogBuffer tableLogBuffer, final String str) {
        return FlowKt.pairwiseBy(flow, (Function1) new LinearBrightnessKt$logDiffForTable$1(new Function0() { // from class: com.android.systemui.brightness.shared.model.LinearBrightnessKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange("linear", str, (String) null, true);
                return null;
            }
        }), (Function3) new LinearBrightnessKt$logDiffForTable$2(tableLogBuffer, "linear", str, null));
    }
}
