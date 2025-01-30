package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToGone$1", m277f = "FromDreamingTransitionInteractor.kt", m278l = {110}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromDreamingTransitionInteractor$listenForDreamingToGone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDreamingTransitionInteractor$listenForDreamingToGone$1(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, Continuation<? super FromDreamingTransitionInteractor$listenForDreamingToGone$1> continuation) {
        super(2, continuation);
        this.this$0 = fromDreamingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDreamingTransitionInteractor$listenForDreamingToGone$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDreamingTransitionInteractor$listenForDreamingToGone$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final FromDreamingTransitionInteractor fromDreamingTransitionInteractor = this.this$0;
            Flow flow = fromDreamingTransitionInteractor.keyguardInteractor.biometricUnlockState;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToGone$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (((BiometricUnlockModel) obj2) == BiometricUnlockModel.WAKE_AND_UNLOCK_FROM_DREAM) {
                        FromDreamingTransitionInteractor fromDreamingTransitionInteractor2 = FromDreamingTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromDreamingTransitionInteractor2.keyguardTransitionRepository;
                        KeyguardState keyguardState = KeyguardState.DREAMING;
                        KeyguardState keyguardState2 = KeyguardState.GONE;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setInterpolator(Interpolators.LINEAR);
                        valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(FromDreamingTransitionInteractor.DEFAULT_DURATION));
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromDreamingTransitionInteractor2.name, keyguardState, keyguardState2, valueAnimator), false);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
