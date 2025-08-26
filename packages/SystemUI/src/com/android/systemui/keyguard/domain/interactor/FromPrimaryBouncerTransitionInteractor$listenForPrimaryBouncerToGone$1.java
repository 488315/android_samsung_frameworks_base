package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

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
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromPrimaryBouncerTransitionInteractor.keyguardInteractor.isKeyguardGoingAway, fromPrimaryBouncerTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(0));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(anonymousClass2, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

        public AnonymousClass2(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor) {
            this.this$0 = fromPrimaryBouncerTransitionInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(boolean z, Continuation continuation) {
            FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1;
            long j;
            if (continuation instanceof FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1) {
                fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 = (FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1) continuation;
                int i = fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 = new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1(this, continuation);
                }
            }
            FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12 = fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1;
            Object obj = fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = this.this$0;
                if (fromPrimaryBouncerTransitionInteractor.keyguardSecurityModel.getSecurityMode(fromPrimaryBouncerTransitionInteractor.selectedUserInteractor.getSelectedUserId()) == KeyguardSecurityModel.SecurityMode.Password) {
                    FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                    j = FromPrimaryBouncerTransitionInteractor.TO_GONE_SHORT_DURATION;
                } else {
                    FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                    j = FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION;
                }
                KeyguardState keyguardState = KeyguardState.GONE;
                long j2 = j;
                ValueAnimator defaultAnimatorForTransitionsToState = fromPrimaryBouncerTransitionInteractor.getDefaultAnimatorForTransitionsToState(keyguardState);
                defaultAnimatorForTransitionsToState.setDuration(Duration.m3457getInWholeMillisecondsimpl(j2));
                Unit unit = Unit.INSTANCE;
                TransitionModeOnCanceled transitionModeOnCanceled = TransitionModeOnCanceled.RESET;
                fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12.L$0 = this;
                fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12.label = 1;
                if (TransitionInteractor.startTransitionTo$default(fromPrimaryBouncerTransitionInteractor, keyguardState, defaultAnimatorForTransitionsToState, transitionModeOnCanceled, null, fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12, 8) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (AnonymousClass2) fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$12.L$0;
                ResultKt.throwOnFailure(obj);
            }
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor2 = this.this$0;
            CommunalSceneInteractor communalSceneInteractor = fromPrimaryBouncerTransitionInteractor2.communalSceneInteractor;
            if (((Boolean) communalSceneInteractor.isIdleOnCommunal.$$delegate_0.getValue()).booleanValue() && !((Boolean) communalSceneInteractor.isLaunchingWidget.$$delegate_0.getValue()).booleanValue() && communalSceneInteractor.editModeState.$$delegate_0.getValue() == null) {
                CommunalSceneInteractor.snapToScene$default(fromPrimaryBouncerTransitionInteractor2.communalSceneInteractor, CommunalScenes.Blank, "FromPrimaryBouncerTransitionInteractor", 0L, 12);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
