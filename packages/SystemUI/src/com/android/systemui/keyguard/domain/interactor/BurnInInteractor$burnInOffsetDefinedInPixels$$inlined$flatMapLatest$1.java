package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isXAxis$inlined;
    final /* synthetic */ int $maxBurnInOffsetResourceId$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BurnInInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1(Continuation continuation, BurnInInteractor burnInInteractor, int i, boolean z) {
        super(3, continuation);
        this.this$0 = burnInInteractor;
        this.$maxBurnInOffsetResourceId$inlined = i;
        this.$isXAxis$inlined = z;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1 burnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1 = new BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$maxBurnInOffsetResourceId$inlined, this.$isXAxis$inlined);
        burnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        burnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1.L$1 = obj2;
        return burnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            float floatValue = ((Number) this.L$1).floatValue();
            int dimensionPixelSize = this.this$0.context.getResources().getDimensionPixelSize(this.$maxBurnInOffsetResourceId$inlined);
            BurnInInteractor burnInInteractor = this.this$0;
            ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(burnInInteractor.keyguardInteractor.dozeTimeTick, new BurnInInteractor$burnInOffsetDefinedInPixels$1$1(burnInInteractor, dimensionPixelSize, this.$isXAxis$inlined, floatValue, null));
            this.label = 1;
            if (FlowKt.emitAll(this, mapLatest, flowCollector) == coroutineSingletons) {
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
