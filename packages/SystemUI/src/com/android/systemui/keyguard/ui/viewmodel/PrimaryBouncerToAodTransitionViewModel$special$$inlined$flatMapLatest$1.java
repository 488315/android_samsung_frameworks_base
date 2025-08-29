package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
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

/* loaded from: classes2.dex */
public final class PrimaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PrimaryBouncerToAodTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel) {
        super(3, continuation);
        this.this$0 = primaryBouncerToAodTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PrimaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1 primaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1 = new PrimaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        primaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        primaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return primaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowM2614sharedFlow74qcysc$default;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
                Duration.Companion companion = Duration.Companion;
                flowM2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilder, DurationKt.toDuration(300, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToAodTransitionViewModel$deviceEntryParentViewAlpha$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return Float.valueOf(((Number) obj2).floatValue());
                    }
                }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToAodTransitionViewModel$deviceEntryParentViewAlpha$1$2
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(1.0f);
                    }
                }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToAodTransitionViewModel$deviceEntryParentViewAlpha$1$3
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(1.0f);
                    }
                }, null, null, 204);
            } else {
                flowM2614sharedFlow74qcysc$default = EmptyFlow.INSTANCE;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowM2614sharedFlow74qcysc$default, this) == coroutineSingletons) {
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
