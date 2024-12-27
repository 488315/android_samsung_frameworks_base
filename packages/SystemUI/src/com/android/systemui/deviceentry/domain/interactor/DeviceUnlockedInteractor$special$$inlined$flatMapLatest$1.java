package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
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

public final class DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    public DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceUnlockedInteractor deviceUnlockedInteractor) {
        super(3, continuation);
        this.this$0 = deviceUnlockedInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1 deviceUnlockedInteractor$special$$inlined$flatMapLatest$1 = new DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow transformLatest;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) this.L$1;
            if (!authenticationMethodModel.isSecure) {
                transformLatest = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new DeviceUnlockStatus(true, null));
            } else if (authenticationMethodModel.equals(AuthenticationMethodModel.Sim.INSTANCE)) {
                transformLatest = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new DeviceUnlockStatus(false, null));
            } else {
                DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                transformLatest = FlowKt.transformLatest(deviceUnlockedInteractor.powerInteractor.isAsleep, new DeviceUnlockedInteractor$deviceUnlockStatus$lambda$9$$inlined$flatMapLatest$1(null, deviceUnlockedInteractor));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, transformLatest, flowCollector) == coroutineSingletons) {
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
