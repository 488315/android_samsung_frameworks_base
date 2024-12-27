package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromPrimaryBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = this.this$0;
            Flow flow = fromPrimaryBouncerTransitionInteractor.keyguardInteractor.isKeyguardGoingAway;
            AnonymousClass1 anonymousClass1 = new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    return bool;
                }
            };
            fromPrimaryBouncerTransitionInteractor.getClass();
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(flow, fromPrimaryBouncerTransitionInteractor, anonymousClass1);
            final FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    long j;
                    ((Boolean) obj2).getClass();
                    FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor3 = FromPrimaryBouncerTransitionInteractor.this;
                    if (fromPrimaryBouncerTransitionInteractor3.keyguardSecurityModel.getSecurityMode(fromPrimaryBouncerTransitionInteractor3.selectedUserInteractor.getSelectedUserId(false)) == KeyguardSecurityModel.SecurityMode.Password) {
                        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                        j = FromPrimaryBouncerTransitionInteractor.TO_GONE_SHORT_DURATION;
                    } else {
                        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                        j = FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION;
                    }
                    KeyguardState keyguardState = KeyguardState.GONE;
                    ValueAnimator defaultAnimatorForTransitionsToState = fromPrimaryBouncerTransitionInteractor3.getDefaultAnimatorForTransitionsToState(keyguardState);
                    defaultAnimatorForTransitionsToState.setDuration(Duration.m2538getInWholeMillisecondsimpl(j));
                    Unit unit = Unit.INSTANCE;
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(fromPrimaryBouncerTransitionInteractor3, keyguardState, defaultAnimatorForTransitionsToState, TransitionModeOnCanceled.RESET, null, continuation, 8);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
