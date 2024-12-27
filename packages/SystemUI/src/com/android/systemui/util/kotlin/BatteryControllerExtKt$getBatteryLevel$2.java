package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class BatteryControllerExtKt$getBatteryLevel$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public BatteryControllerExtKt$getBatteryLevel$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryControllerExtKt$getBatteryLevel$2 batteryControllerExtKt$getBatteryLevel$2 = new BatteryControllerExtKt$getBatteryLevel$2(continuation);
        batteryControllerExtKt$getBatteryLevel$2.L$0 = obj;
        return batteryControllerExtKt$getBatteryLevel$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Integer num = new Integer(0);
            this.label = 1;
            if (flowCollector.emit(num, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((BatteryControllerExtKt$getBatteryLevel$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
