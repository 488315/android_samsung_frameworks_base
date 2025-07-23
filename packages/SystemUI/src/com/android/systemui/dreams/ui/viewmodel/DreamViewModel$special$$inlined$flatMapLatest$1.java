package com.android.systemui.dreams.ui.viewmodel;

import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DreamViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, DreamViewModel dreamViewModel) {
        super(3, continuation);
        this.this$0 = dreamViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DreamViewModel$special$$inlined$flatMapLatest$1 dreamViewModel$special$$inlined$flatMapLatest$1 = new DreamViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        dreamViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        dreamViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return dreamViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final int intValue = ((Number) this.L$1).intValue();
            DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel = this.this$0.toLockscreenTransitionViewModel;
            dreamingToLockscreenTransitionViewModel.getClass();
            FromDreamingTransitionInteractor.Companion.getClass();
            KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(dreamingToLockscreenTransitionViewModel.transitionAnimation, FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    return Float.valueOf(((Float) obj2).floatValue() * intValue);
                }
            }, 0L, null, null, null, Interpolators.EMPHASIZED, null, 188);
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
