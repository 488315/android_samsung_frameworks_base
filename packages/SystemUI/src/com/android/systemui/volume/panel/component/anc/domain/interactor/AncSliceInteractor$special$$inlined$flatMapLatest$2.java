package com.android.systemui.volume.panel.component.anc.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AncSliceInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AncSliceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AncSliceInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, AncSliceInteractor ancSliceInteractor) {
        super(3, continuation);
        this.this$0 = ancSliceInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AncSliceInteractor$special$$inlined$flatMapLatest$2 ancSliceInteractor$special$$inlined$flatMapLatest$2 = new AncSliceInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        ancSliceInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        ancSliceInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return ancSliceInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int intValue = ((Number) this.L$1).intValue();
            AncSliceInteractor ancSliceInteractor = this.this$0;
            ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(ancSliceInteractor.audioOutputInteractor.currentAudioDevice, new AncSliceInteractor$ancSlice$$inlined$flatMapLatest$1(null, ancSliceInteractor, intValue, false, false));
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
