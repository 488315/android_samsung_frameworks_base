package com.android.systemui.volume.ui.navigation;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class VolumeNavigator$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeNavigator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeNavigator$special$$inlined$flatMapLatest$1(Continuation continuation, VolumeNavigator volumeNavigator) {
        super(3, continuation);
        this.this$0 = volumeNavigator;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeNavigator$special$$inlined$flatMapLatest$1 volumeNavigator$special$$inlined$flatMapLatest$1 = new VolumeNavigator$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        volumeNavigator$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        volumeNavigator$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return volumeNavigator$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowOn = ((Boolean) this.L$1).booleanValue() ? FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new VolumeNavigator$2$1(this.this$0, null)), this.this$0.mainContext) : EmptyFlow.INSTANCE;
            this.label = 1;
            if (FlowKt.emitAll(this, flowOn, flowCollector) == coroutineSingletons) {
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
