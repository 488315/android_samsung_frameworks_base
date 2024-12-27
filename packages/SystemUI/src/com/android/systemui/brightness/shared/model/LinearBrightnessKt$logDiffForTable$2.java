package com.android.systemui.brightness.shared.model;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.plugins.log.TableLogBufferBase;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LinearBrightnessKt$logDiffForTable$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnName;
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LinearBrightnessKt$logDiffForTable$2(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float f = ((LinearBrightness) obj2).floatValue;
        LinearBrightnessKt$logDiffForTable$2 linearBrightnessKt$logDiffForTable$2 = new LinearBrightnessKt$logDiffForTable$2(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
        linearBrightnessKt$logDiffForTable$2.L$0 = (LinearBrightness) obj;
        linearBrightnessKt$logDiffForTable$2.F$0 = f;
        return linearBrightnessKt$logDiffForTable$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LinearBrightness linearBrightness = (LinearBrightness) this.L$0;
        float f = this.F$0;
        if (linearBrightness == null || Float.compare(linearBrightness.floatValue, f) != 0) {
            TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
            String str = this.$columnPrefix;
            String str2 = this.$columnName;
            String formatBrightness = LinearBrightnessKt.formatBrightness(f);
            tableLogBuffer.getClass();
            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, formatBrightness);
        }
        return LinearBrightness.m935boximpl(f);
    }
}
