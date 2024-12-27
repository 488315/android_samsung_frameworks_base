package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ToAodFoldTransitionInteractor$forceToAod$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ToAodFoldTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ToAodFoldTransitionInteractor$forceToAod$1(ToAodFoldTransitionInteractor toAodFoldTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = toAodFoldTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ToAodFoldTransitionInteractor$forceToAod$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ToAodFoldTransitionInteractor$forceToAod$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardTransitionRepository keyguardTransitionRepository = this.this$0.transitionRepository;
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(ToAodFoldTransitionInteractor.TAG, " (Fold transition triggered)");
            KeyguardState currentState = this.this$0.transitionInteractor.getCurrentState();
            KeyguardState keyguardState = (KeyguardState) this.this$0.transitionInteractor.asleepKeyguardState.$$delegate_0.getValue();
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(0L);
            Unit unit = Unit.INSTANCE;
            TransitionInfo transitionInfo = new TransitionInfo(m, currentState, keyguardState, valueAnimator, TransitionModeOnCanceled.LAST_VALUE);
            this.label = 1;
            if (((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(transitionInfo, this) == coroutineSingletons) {
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
