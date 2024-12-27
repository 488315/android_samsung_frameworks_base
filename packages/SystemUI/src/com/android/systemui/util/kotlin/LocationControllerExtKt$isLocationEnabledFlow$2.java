package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class LocationControllerExtKt$isLocationEnabledFlow$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocationController $this_isLocationEnabledFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocationControllerExtKt$isLocationEnabledFlow$2(LocationController locationController, Continuation continuation) {
        super(2, continuation);
        this.$this_isLocationEnabledFlow = locationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocationControllerExtKt$isLocationEnabledFlow$2 locationControllerExtKt$isLocationEnabledFlow$2 = new LocationControllerExtKt$isLocationEnabledFlow$2(this.$this_isLocationEnabledFlow, continuation);
        locationControllerExtKt$isLocationEnabledFlow$2.L$0 = obj;
        return locationControllerExtKt$isLocationEnabledFlow$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(((LocationControllerImpl) this.$this_isLocationEnabledFlow).isLocationEnabled$1());
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
        return ((LocationControllerExtKt$isLocationEnabledFlow$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
