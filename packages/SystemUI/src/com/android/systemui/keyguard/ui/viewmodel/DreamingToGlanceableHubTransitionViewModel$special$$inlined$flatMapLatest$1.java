package com.android.systemui.keyguard.ui.viewmodel;

import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DreamingToGlanceableHubTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, DreamingToGlanceableHubTransitionViewModel dreamingToGlanceableHubTransitionViewModel) {
        super(3, continuation);
        this.this$0 = dreamingToGlanceableHubTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1 dreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1 = new DreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        dreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        dreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return dreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final int intValue = ((Number) this.L$1).intValue();
            KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(this.this$0.transitionAnimation, DreamingToGlanceableHubTransitionViewModel.TO_GLANCEABLE_HUB_DURATION, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$dreamOverlayTranslationX$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    return Float.valueOf(((Number) obj2).floatValue() * intValue);
                }
            }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$dreamOverlayTranslationX$1$2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(0.0f);
                }
            }, null, Interpolators.EMPHASIZED, "DREAMING->GLANCEABLE_HUB: overlayTranslationX", 44);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, m2599sharedFlow74qcysc$default, this) == coroutineSingletons) {
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
