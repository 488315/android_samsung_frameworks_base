package com.android.systemui.brightness.shared.model;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Arrays;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;

public abstract class LinearBrightnessKt {
    public static final String formatBrightness(float f) {
        return String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
    }

    /* renamed from: logDiffForTable-CVGC-8U, reason: not valid java name */
    public static final Flow m937logDiffForTableCVGC8U(Flow flow, final TableLogBuffer tableLogBuffer, final String str) {
        final String str2 = "linear";
        final LinearBrightness linearBrightness = null;
        return FlowKt.pairwiseBy(flow, (Function1) new LinearBrightnessKt$logDiffForTable$1(new Function0() { // from class: com.android.systemui.brightness.shared.model.LinearBrightnessKt$logDiffForTable$initialValueFun$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str3 = str2;
                String str4 = str;
                LinearBrightness linearBrightness2 = linearBrightness;
                tableLogBuffer2.logChange(str3, str4, linearBrightness2 != null ? LinearBrightnessKt.formatBrightness(linearBrightness2.floatValue) : null, true);
                return linearBrightness;
            }
        }), (Function3) new LinearBrightnessKt$logDiffForTable$2(tableLogBuffer, "linear", str, null));
    }
}
