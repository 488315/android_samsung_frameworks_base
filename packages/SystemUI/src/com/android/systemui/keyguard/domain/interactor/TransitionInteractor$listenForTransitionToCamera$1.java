package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class TransitionInteractor$listenForTransitionToCamera$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardInteractor $keyguardInteractor;
    int label;
    final /* synthetic */ TransitionInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* JADX WARN: Removed duplicated region for block: B:20:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x003c  */
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
                if (r1 == 0) goto L3c
                if (r1 == r3) goto L34
                if (r1 != r2) goto L2c
                kotlin.ResultKt.throwOnFailure(r9)
                goto L82
            L2c:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L34:
                java.lang.Object r8 = r6.L$0
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1 r8 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1.AnonymousClass1) r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L63
            L3c:
                kotlin.ResultKt.throwOnFailure(r9)
                boolean r9 = com.android.systemui.LsRune.KEYGUARD_FBE
                if (r9 == 0) goto L56
                java.lang.Class<com.android.keyguard.KeyguardUpdateMonitor> r9 = com.android.keyguard.KeyguardUpdateMonitor.class
                com.android.systemui.Dependency r1 = com.android.systemui.Dependency.sDependency
                java.lang.Object r9 = r1.getDependencyInner(r9)
                com.android.keyguard.KeyguardUpdateMonitor r9 = (com.android.keyguard.KeyguardUpdateMonitor) r9
                boolean r9 = r9.isUserUnlocked()
                if (r9 != 0) goto L56
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L56:
                r6.L$0 = r8
                r6.label = r3
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r9 = r8.this$0
                java.lang.Object r9 = r9.maybeHandleInsecurePowerGesture(r6)
                if (r9 != r0) goto L63
                return r0
            L63:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 != 0) goto L85
                com.android.systemui.keyguard.domain.interactor.TransitionInteractor r1 = r8.this$0
                com.android.systemui.keyguard.shared.model.KeyguardState r8 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
                com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r4 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.RESET
                r9 = 0
                r6.L$0 = r9
                r6.label = r2
                java.lang.String r5 = "keyguardInteractor.onCameraLaunchDetected"
                r7 = 2
                r3 = 0
                r2 = r8
                java.lang.Object r8 = com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
                if (r8 != r0) goto L82
                return r0
            L82:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L85:
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
            Flow flow = this.$keyguardInteractor.onCameraLaunchDetected;
            transitionInteractor.getClass();
            TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(flow, transitionInteractor);
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
