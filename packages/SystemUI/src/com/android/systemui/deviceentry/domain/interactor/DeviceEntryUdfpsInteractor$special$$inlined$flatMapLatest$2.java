package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
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

public final class DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ FingerprintPropertyInteractor $fingerprintPropertyInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, FingerprintPropertyInteractor fingerprintPropertyInteractor) {
        super(3, continuation);
        this.$fingerprintPropertyInteractor$inlined = fingerprintPropertyInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2 deviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2 = new DeviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$fingerprintPropertyInteractor$inlined);
        deviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceEntryUdfpsInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((Boolean) this.L$1).booleanValue() ? this.$fingerprintPropertyInteractor$inlined.sensorLocation : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
