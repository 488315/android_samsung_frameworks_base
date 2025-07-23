package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

        public AnonymousClass2(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor) {
            this.this$0 = fromPrimaryBouncerTransitionInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0035  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(boolean r8, kotlin.coroutines.Continuation r9) {
            /*
                r7 = this;
                boolean r8 = r9 instanceof com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1
                if (r8 == 0) goto L14
                r8 = r9
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 r8 = (com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1) r8
                int r0 = r8.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = r0 & r1
                if (r2 == 0) goto L14
                int r0 = r0 - r1
                r8.label = r0
            L12:
                r5 = r8
                goto L1a
            L14:
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1 r8 = new com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2$emit$1
                r8.<init>(r7, r9)
                goto L12
            L1a:
                java.lang.Object r8 = r5.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r9 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r0 = r5.label
                r1 = 1
                if (r0 == 0) goto L35
                if (r0 != r1) goto L2d
                java.lang.Object r7 = r5.L$0
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1$2 r7 = (com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1.AnonymousClass2) r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L7b
            L2d:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L35:
                kotlin.ResultKt.throwOnFailure(r8)
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor r0 = r7.this$0
                com.android.keyguard.KeyguardSecurityModel r8 = r0.keyguardSecurityModel
                com.android.systemui.user.domain.interactor.SelectedUserInteractor r2 = r0.selectedUserInteractor
                int r2 = r2.getSelectedUserId()
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r8 = r8.getSecurityMode(r2)
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r2 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Password
                if (r8 != r2) goto L53
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$Companion r8 = com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor.Companion
                r8.getClass()
                long r2 = com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor.TO_GONE_SHORT_DURATION
            L51:
                r8 = r1
                goto L5b
            L53:
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$Companion r8 = com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor.Companion
                r8.getClass()
                long r2 = com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION
                goto L51
            L5b:
                com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                r3 = r2
                android.animation.ValueAnimator r2 = r0.getDefaultAnimatorForTransitionsToState(r1)
                long r3 = kotlin.time.Duration.m3437getInWholeMillisecondsimpl(r3)
                r2.setDuration(r3)
                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r3 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.RESET
                r5.L$0 = r7
                r5.label = r8
                r4 = 0
                r6 = 8
                java.lang.Object r8 = com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r0, r1, r2, r3, r4, r5, r6)
                if (r8 != r9) goto L7b
                return r9
            L7b:
                com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor r7 = r7.this$0
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r8 = r7.communalSceneInteractor
                kotlinx.coroutines.flow.ReadonlyStateFlow r9 = r8.isIdleOnCommunal
                kotlinx.coroutines.flow.StateFlow r9 = r9.$$delegate_0
                java.lang.Object r9 = r9.getValue()
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto Lb6
                kotlinx.coroutines.flow.ReadonlyStateFlow r9 = r8.isLaunchingWidget
                kotlinx.coroutines.flow.StateFlow r9 = r9.$$delegate_0
                java.lang.Object r9 = r9.getValue()
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 != 0) goto Lb6
                kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r8.editModeState
                kotlinx.coroutines.flow.StateFlow r8 = r8.$$delegate_0
                java.lang.Object r8 = r8.getValue()
                if (r8 != 0) goto Lb6
                com.android.compose.animation.scene.SceneKey r1 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
                java.lang.String r2 = "FromPrimaryBouncerTransitionInteractor"
                r3 = 0
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r0 = r7.communalSceneInteractor
                r5 = 12
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor.snapToScene$default(r0, r1, r2, r3, r5)
            Lb6:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1.AnonymousClass2.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
