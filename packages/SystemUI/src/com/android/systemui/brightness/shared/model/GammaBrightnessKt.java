package com.android.systemui.brightness.shared.model;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class GammaBrightnessKt {
    /* renamed from: logDiffForTable-GAU2kQA, reason: not valid java name */
    public static final Flow m934logDiffForTableGAU2kQA(FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, final TableLogBuffer tableLogBuffer) {
        final String str = "gamma";
        final String str2 = "brightness";
        final GammaBrightness gammaBrightness = null;
        return FlowKt.pairwiseBy((Flow) flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, (Function1) new GammaBrightnessKt$logDiffForTable$1(new Function0() { // from class: com.android.systemui.brightness.shared.model.GammaBrightnessKt$logDiffForTable$initialValueFun$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str3 = str;
                String str4 = str2;
                GammaBrightness gammaBrightness2 = gammaBrightness;
                tableLogBuffer2.logChange(str3, str4, gammaBrightness2 != null ? Integer.valueOf(gammaBrightness2.value) : null, true);
                return gammaBrightness;
            }
        }), (Function3) new GammaBrightnessKt$logDiffForTable$2(tableLogBuffer, "gamma", "brightness", null));
    }
}
