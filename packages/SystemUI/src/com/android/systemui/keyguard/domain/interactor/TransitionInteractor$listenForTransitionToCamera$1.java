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
final class TransitionInteractor$listenForTransitionToCamera$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardInteractor $keyguardInteractor;
    int label;
    final /* synthetic */ TransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ TransitionInteractor this$0;

        public AnonymousClass1(TransitionInteractor transitionInteractor) {
            this.this$0 = transitionInteractor;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit$1(continuation);
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0099, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r8.this$0, r2, null, r4, "keyguardInteractor.onCameraLaunchDetected", r6, 2) == r0) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x009b, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:
        
            if (r9 == r0) goto L35;
         */
        /* JADX WARN: Removed duplicated region for block: B:20:0x006c  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit$1(kotlin.coroutines.Continuation r9) {
            /*
                r8 = this;
                boolean r0 = r9 instanceof com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1$emit$1
                if (r0 == 0) goto L14
                r0 = r9
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1$emit$1 r0 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L14
                int r1 = r1 - r2
                r0.label = r1
            L12:
                r6 = r0
                goto L1a
            L14:
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1$emit$1 r0 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1$emit$1
                r0.<init>(r8, r9)
                goto L12
            L1a:
                java.lang.Object r9 = r6.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L3d
                if (r1 == r3) goto L35
                if (r1 != r2) goto L2d
                kotlin.ResultKt.throwOnFailure(r9)
                goto L9c
            L2d:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L35:
                java.lang.Object r8 = r6.L$0
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1 r8 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1.AnonymousClass1) r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L64
            L3d:
                kotlin.ResultKt.throwOnFailure(r9)
                boolean r9 = com.android.systemui.LsRune.KEYGUARD_FBE
                if (r9 == 0) goto L57
                java.lang.Class<com.android.keyguard.KeyguardUpdateMonitor> r9 = com.android.keyguard.KeyguardUpdateMonitor.class
                com.android.systemui.Dependency r1 = com.android.systemui.Dependency.sDependency
                java.lang.Object r9 = r1.getDependencyInner(r9)
                com.android.keyguard.KeyguardUpdateMonitor r9 = (com.android.keyguard.KeyguardUpdateMonitor) r9
                boolean r9 = r9.isUserUnlocked$1()
                if (r9 != 0) goto L57
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L57:
                r6.L$0 = r8
                r6.label = r3
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r9 = r8.this$0
                java.lang.Object r9 = r9.maybeHandleInsecurePowerGesture(r6)
                if (r9 != r0) goto L64
                goto L9b
            L64:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 != 0) goto L9f
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r9 = r8.this$0
                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r9 = r9.transitionInteractor
                kotlinx.coroutines.flow.ReadonlyStateFlow r9 = r9.transitionState
                kotlinx.coroutines.flow.StateFlow r9 = r9.$$delegate_0
                java.lang.Object r9 = r9.getValue()
                com.android.systemui.keyguard.shared.model.TransitionStep r9 = (com.android.systemui.keyguard.shared.model.TransitionStep) r9
                com.android.systemui.keyguard.shared.model.KeyguardState r9 = r9.to
                com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                if (r9 != r1) goto L85
                com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r9 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.REVERSE
            L82:
                r4 = r9
                r9 = r2
                goto L88
            L85:
                com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r9 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.RESET
                goto L82
            L88:
                com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
                r1 = 0
                r6.L$0 = r1
                r6.label = r9
                java.lang.String r5 = "keyguardInteractor.onCameraLaunchDetected"
                r7 = 2
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r1 = r8.this$0
                r3 = 0
                java.lang.Object r8 = com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
                if (r8 != r0) goto L9c
            L9b:
                return r0
            L9c:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L9f:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1.AnonymousClass1.emit$1(kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionInteractor$listenForTransitionToCamera$1(TransitionInteractor transitionInteractor, KeyguardInteractor keyguardInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = transitionInteractor;
        this.$keyguardInteractor = keyguardInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TransitionInteractor$listenForTransitionToCamera$1(this.this$0, this.$keyguardInteractor, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TransitionInteractor$listenForTransitionToCamera$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            TransitionInteractor transitionInteractor = this.this$0;
            KeyguardInteractor$special$$inlined$filter$1 keyguardInteractor$special$$inlined$filter$1 = this.$keyguardInteractor.onCameraLaunchDetected;
            transitionInteractor.getClass();
            TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(keyguardInteractor$special$$inlined$filter$1, transitionInteractor);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.collect(anonymousClass1, this) == coroutineSingletons) {
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
