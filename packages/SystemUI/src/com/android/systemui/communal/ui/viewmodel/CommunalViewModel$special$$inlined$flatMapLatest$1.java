package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
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
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class CommunalViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, CommunalViewModel communalViewModel) {
        super(3, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalViewModel$special$$inlined$flatMapLatest$1 communalViewModel$special$$inlined$flatMapLatest$1 = new CommunalViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        communalViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                combine = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(this.this$0.communalInteractor.tutorialContent);
            } else {
                CommunalViewModel communalViewModel = this.this$0;
                ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(communalViewModel._isMediaHostVisible, new CommunalViewModel$latestCommunalContent$lambda$1$$inlined$flatMapLatest$1(null, communalViewModel));
                CommunalInteractor communalInteractor = this.this$0.communalInteractor;
                combine = FlowKt.combine(transformLatest, communalInteractor.widgetContent, communalInteractor.ctaTileContent, new CommunalViewModel$latestCommunalContent$1$1(null));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
