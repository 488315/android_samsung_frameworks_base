package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ MobileIconInteractorImpl $this_run$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
        super(3, continuation);
        this.$this_run$inlined = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1 mobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1 = new MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1((Continuation) obj3, this.$this_run$inlined);
        mobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1.L$1 = obj2;
        return mobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = ((Boolean) this.L$1).booleanValue() ? this.$this_run$inlined.satelliteIcon : this.$this_run$inlined.cellularIcon;
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
