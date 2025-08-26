package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class AodBurnInViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AodBurnInViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodBurnInViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, AodBurnInViewModel aodBurnInViewModel) {
        super(3, continuation);
        this.this$0 = aodBurnInViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AodBurnInViewModel$special$$inlined$flatMapLatest$1 aodBurnInViewModel$special$$inlined$flatMapLatest$1 = new AodBurnInViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        aodBurnInViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        aodBurnInViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return aodBurnInViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            BurnInParameters burnInParameters = (BurnInParameters) this.L$1;
            ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(((ConfigurationInteractorImpl) this.this$0.configurationInteractor).dimensionPixelSize(ArraysKt___ArraysKt.toSet(new Integer[]{new Integer(R.dimen.keyguard_enter_from_top_translation_y), new Integer(R.dimen.keyguard_enter_from_side_translation_x)})), new AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1(null, this.this$0, burnInParameters));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, channelFlowTransformLatestTransformLatest, this) == coroutineSingletons) {
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
