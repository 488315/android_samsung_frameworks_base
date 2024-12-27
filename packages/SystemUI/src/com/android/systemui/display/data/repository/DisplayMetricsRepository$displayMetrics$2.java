package com.android.systemui.display.data.repository;

import android.util.DisplayMetrics;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DisplayMetricsRepository$displayMetrics$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LogBuffer $logBuffer;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayMetricsRepository$displayMetrics$2(LogBuffer logBuffer, Continuation continuation) {
        super(2, continuation);
        this.$logBuffer = logBuffer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayMetricsRepository$displayMetrics$2 displayMetricsRepository$displayMetrics$2 = new DisplayMetricsRepository$displayMetrics$2(this.$logBuffer, continuation);
        displayMetricsRepository$displayMetrics$2.L$0 = obj;
        return displayMetricsRepository$displayMetrics$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayMetricsRepository$displayMetrics$2) create((DisplayMetrics) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisplayMetrics displayMetrics = (DisplayMetrics) this.L$0;
        LogBuffer logBuffer = this.$logBuffer;
        LogMessage obtain = logBuffer.obtain("DisplayMetrics", LogLevel.INFO, new Function1() { // from class: com.android.systemui.display.data.repository.DisplayMetricsRepository$displayMetrics$2.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New metrics: ", ((LogMessage) obj2).getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = displayMetrics.toString();
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
