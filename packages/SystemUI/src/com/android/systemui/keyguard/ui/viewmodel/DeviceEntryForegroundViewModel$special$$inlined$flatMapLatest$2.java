package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
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

public final class DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ ConfigurationInteractor $configurationInteractor$inlined;
    final /* synthetic */ DeviceEntryIconViewModel $deviceEntryIconViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryForegroundViewModel this$0;

    public DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, ConfigurationInteractor configurationInteractor, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
        super(3, continuation);
        this.$deviceEntryIconViewModel$inlined = deviceEntryIconViewModel;
        this.$configurationInteractor$inlined = configurationInteractor;
        this.this$0 = deviceEntryForegroundViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2 deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2 = new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$deviceEntryIconViewModel$inlined, this.$configurationInteractor$inlined, this.this$0);
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((Boolean) this.L$1).booleanValue() ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Integer(-1)) : FlowKt.transformLatest(this.$deviceEntryIconViewModel$inlined.useBackgroundProtection, new DeviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1(null, this.$configurationInteractor$inlined, this.this$0));
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
