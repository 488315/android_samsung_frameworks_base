package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class BatteryControllerExtKt$isDevicePluggedIn$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ BatteryController $this_isDevicePluggedIn;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryControllerExtKt$isDevicePluggedIn$2(BatteryController batteryController, Continuation continuation) {
        super(2, continuation);
        this.$this_isDevicePluggedIn = batteryController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryControllerExtKt$isDevicePluggedIn$2 batteryControllerExtKt$isDevicePluggedIn$2 = new BatteryControllerExtKt$isDevicePluggedIn$2(this.$this_isDevicePluggedIn, continuation);
        batteryControllerExtKt$isDevicePluggedIn$2.L$0 = obj;
        return batteryControllerExtKt$isDevicePluggedIn$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(((BatteryControllerImpl) this.$this_isDevicePluggedIn).mPluggedIn);
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
        return ((BatteryControllerExtKt$isDevicePluggedIn$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
