package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class GoneToAodTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ GoneToAodTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GoneToAodTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, GoneToAodTransitionViewModel goneToAodTransitionViewModel) {
        super(3, continuation);
        this.this$0 = goneToAodTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        GoneToAodTransitionViewModel$special$$inlined$flatMapLatest$1 goneToAodTransitionViewModel$special$$inlined$flatMapLatest$1 = new GoneToAodTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        goneToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        goneToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return goneToAodTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
                Duration.Companion companion = Duration.Companion;
                DurationUnit durationUnit = DurationUnit.MILLISECONDS;
                flow = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(flowBuilder, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$deviceEntryParentViewAlpha$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return Float.valueOf(((Number) obj2).floatValue());
                    }
                }, DurationKt.toDuration(1100, durationUnit), null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$deviceEntryParentViewAlpha$1$2
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(1.0f);
                    }
                }, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode);
            } else {
                flow = EmptyFlow.INSTANCE;
            }
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
