package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.LockscreenSceneTransitionRepository;
import com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenSceneTransitionInteractor implements CoreStartable, SceneInteractor.OnSceneAboutToChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public UUID currentTransitionId;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public StandaloneCoroutine progressJob;
    public final LockscreenSceneTransitionRepository repository;
    public final SceneInteractor sceneInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public LockscreenSceneTransitionInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, CoroutineScope coroutineScope, SceneInteractor sceneInteractor, LockscreenSceneTransitionRepository lockscreenSceneTransitionRepository) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.applicationScope = coroutineScope;
        this.sceneInteractor = sceneInteractor;
        this.repository = lockscreenSceneTransitionRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0142, code lost:
    
        if (r15 != r1) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d7, code lost:
    
        if (r15 != r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a9, code lost:
    
        if (r14.transitionKtfTo$1(r15, r0) == r1) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0108, code lost:
    
        if (r14.transitionKtfTo$1(r15, r0) == r1) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0164, code lost:
    
        if (r14.transitionKtfTo$1(r13, r0) == r1) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleTransition(com.android.compose.animation.scene.ObservableTransitionState.Transition r13, com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r14, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.access$handleTransition(com.android.compose.animation.scene.ObservableTransitionState$Transition, com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object finishCurrentTransition$1(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishCurrentTransition$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishCurrentTransition$1 r0 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishCurrentTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishCurrentTransition$1 r0 = new com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishCurrentTransition$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r5 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4c
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.UUID r6 = r5.currentTransitionId
            r6.getClass()
            com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r3 = r5.internalTransitionInteractor
            r4 = 1065353216(0x3f800000, float:1.0)
            java.lang.Object r6 = r3.updateTransition(r6, r4, r2, r0)
            if (r6 != r1) goto L4c
            return r1
        L4c:
            r5.resetTransitionData$1()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.finishCurrentTransition$1(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object finishReversedTransitionTo$1(com.android.systemui.keyguard.shared.model.KeyguardState r12, kotlin.coroutines.Continuation r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1
            if (r0 == 0) goto L13
            r0 = r13
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 r0 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 r0 = new com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1
            r0.<init>(r11, r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L42
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r11 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r11 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r11
            kotlin.ResultKt.throwOnFailure(r13)
            goto L88
        L2e:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L36:
            java.lang.Object r11 = r0.L$1
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r11 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r11
            java.lang.Object r12 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r12 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto L6a
        L42:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.keyguard.shared.model.TransitionInfo r5 = new com.android.systemui.keyguard.shared.model.TransitionInfo
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r13 = r11.internalTransitionInteractor
            com.android.systemui.keyguard.shared.model.TransitionInfo r2 = r13.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = r2.to
            com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r10 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.REVERSE
            java.lang.String r6 = "LockscreenSceneTransitionInteractor"
            r9 = 0
            r8 = r12
            r5.<init>(r6, r7, r8, r9, r10)
            r0.L$0 = r11
            r0.L$1 = r11
            r0.label = r4
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r12 = r13.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r12 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r12
            java.lang.Object r13 = r12.startTransition(r5, r0)
            if (r13 != r1) goto L69
            goto L86
        L69:
            r12 = r11
        L6a:
            java.util.UUID r13 = (java.util.UUID) r13
            r11.currentTransitionId = r13
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r11 = r12.internalTransitionInteractor
            java.util.UUID r13 = r12.currentTransitionId
            r13.getClass()
            com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            r0.L$0 = r12
            r4 = 0
            r0.L$1 = r4
            r0.label = r3
            r3 = 1065353216(0x3f800000, float:1.0)
            java.lang.Object r11 = r11.updateTransition(r13, r3, r2, r0)
            if (r11 != r1) goto L87
        L86:
            return r1
        L87:
            r11 = r12
        L88:
            r11.resetTransitionData$1()
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.finishReversedTransitionTo$1(com.android.systemui.keyguard.shared.model.KeyguardState, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void onSceneAboutToChange(SceneKey sceneKey, Object obj) {
        if (!Intrinsics.areEqual(sceneKey, Scenes.Lockscreen) || obj == null) {
            return;
        }
        if (!(obj instanceof KeyguardState)) {
            throw new IllegalArgumentException("Lockscreen sceneState needs to be a KeyguardState.");
        }
        this.repository.nextLockscreenTargetState.updateState(null, obj);
    }

    public final void resetTransitionData$1() {
        StandaloneCoroutine standaloneCoroutine = this.progressJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressJob = null;
        this.currentTransitionId = null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.sceneInteractor.onSceneAboutToChangeListener.add(this);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1(this, null), 7);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startTransition$1(com.android.systemui.keyguard.shared.model.TransitionInfo r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$startTransition$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$startTransition$1 r0 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$startTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$startTransition$1 r0 = new com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$startTransition$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r4 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4e
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.UUID r6 = r4.currentTransitionId
            if (r6 == 0) goto L3d
            r4.resetTransitionData$1()
        L3d:
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r6 = r4.internalTransitionInteractor
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r6 = r6.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r6 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r6
            java.lang.Object r6 = r6.startTransition(r5, r0)
            if (r6 != r1) goto L4e
            return r1
        L4e:
            java.util.UUID r6 = (java.util.UUID) r6
            r4.currentTransitionId = r6
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.startTransition$1(com.android.systemui.keyguard.shared.model.TransitionInfo, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object transitionKtfTo$1(KeyguardState keyguardState, Continuation continuation) {
        TransitionStep transitionStep = (TransitionStep) this.transitionInteractor.transitionState.$$delegate_0.getValue();
        KeyguardState keyguardState2 = transitionStep.to;
        if (keyguardState2 == keyguardState) {
            if (transitionStep.transitionState == TransitionState.FINISHED) {
                resetTransitionData$1();
                return Unit.INSTANCE;
            }
        }
        if (keyguardState == null || keyguardState2 == keyguardState) {
            Object finishCurrentTransition$1 = finishCurrentTransition$1(continuation);
            return finishCurrentTransition$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? finishCurrentTransition$1 : Unit.INSTANCE;
        }
        Object finishReversedTransitionTo$1 = finishReversedTransitionTo$1(keyguardState, continuation);
        return finishReversedTransitionTo$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? finishReversedTransitionTo$1 : Unit.INSTANCE;
    }
}
