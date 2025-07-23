package com.android.systemui.deviceentry.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryBiometricsAllowedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor) {
        super(3, continuation);
        this.this$0 = deviceEntryBiometricsAllowedInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1 deviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1 = new DeviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        deviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((Boolean) this.L$1).booleanValue() ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : this.this$0.isFingerprintAuthCurrentlyAllowed;
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
}
