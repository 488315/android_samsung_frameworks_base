package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.data.repository.LockscreenSceneTransitionRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LockscreenSceneTransitionInteractor implements CoreStartable {
    public final CoroutineScope applicationScope;
    public UUID currentTransitionId;
    public Job progressJob;
    public final LockscreenSceneTransitionRepository repository;
    public final SceneInteractor sceneInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public LockscreenSceneTransitionInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, SceneInteractor sceneInteractor, LockscreenSceneTransitionRepository lockscreenSceneTransitionRepository) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.applicationScope = coroutineScope;
        this.sceneInteractor = sceneInteractor;
        this.repository = lockscreenSceneTransitionRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleTransition(com.android.compose.animation.scene.ObservableTransitionState.Transition r13, com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r14, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.access$handleTransition(com.android.compose.animation.scene.ObservableTransitionState$Transition, com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object finishReversedTransitionTo(com.android.systemui.keyguard.shared.model.KeyguardState r11, kotlin.coroutines.Continuation r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1
            if (r0 == 0) goto L13
            r0 = r12
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
            r0.<init>(r10, r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r10 = r0.L$1
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r10 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r10
            java.lang.Object r11 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor r11 = (com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L66
        L2f:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L37:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.keyguard.shared.model.TransitionInfo r12 = new com.android.systemui.keyguard.shared.model.TransitionInfo
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r2 = r10.transitionInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r2.currentTransitionInfoInternal
            kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.getValue()
            com.android.systemui.keyguard.shared.model.TransitionInfo r4 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r4
            com.android.systemui.keyguard.shared.model.KeyguardState r6 = r4.to
            com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r9 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.REVERSE
            java.lang.String r5 = "LockscreenSceneTransitionInteractor"
            r8 = 0
            r4 = r12
            r7 = r11
            r4.<init>(r5, r6, r7, r8, r9)
            r0.L$0 = r10
            r0.L$1 = r10
            r0.label = r3
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r11 = r2.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r11 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r11
            java.lang.Object r12 = r11.startTransition(r12, r0)
            if (r12 != r1) goto L65
            return r1
        L65:
            r11 = r10
        L66:
            java.util.UUID r12 = (java.util.UUID) r12
            r10.currentTransitionId = r12
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r10 = r11.transitionInteractor
            java.util.UUID r12 = r11.currentTransitionId
            kotlin.jvm.internal.Intrinsics.checkNotNull(r12)
            com.android.systemui.keyguard.shared.model.TransitionState r0 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r10 = r10.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r10 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r10
            r1 = 1065353216(0x3f800000, float:1.0)
            r10.updateTransition(r12, r1, r0)
            r11.resetTransitionData()
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.finishReversedTransitionTo(com.android.systemui.keyguard.shared.model.KeyguardState, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void resetTransitionData() {
        Job job = this.progressJob;
        if (job != null) {
            job.cancel(null);
        }
        this.progressJob = null;
        this.currentTransitionId = null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.sceneInteractor.onSceneAboutToChangeListener.add(this);
        BuildersKt.launch$default(this.applicationScope, null, null, new LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1(this, null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo r5, kotlin.coroutines.Continuation r6) {
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
            r4.resetTransitionData()
        L3d:
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r6 = r4.transitionInteractor
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor.startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object transitionKtfTo(KeyguardState keyguardState, Continuation continuation) {
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.transitionInteractor;
        TransitionStep transitionStep = (TransitionStep) keyguardTransitionInteractor.transitionState.$$delegate_0.getValue();
        KeyguardState keyguardState2 = transitionStep.to;
        if (keyguardState2 == keyguardState) {
            if (transitionStep.transitionState == TransitionState.FINISHED) {
                resetTransitionData();
                return Unit.INSTANCE;
            }
        }
        if (keyguardState2 != keyguardState) {
            Object finishReversedTransitionTo = finishReversedTransitionTo(keyguardState, continuation);
            return finishReversedTransitionTo == CoroutineSingletons.COROUTINE_SUSPENDED ? finishReversedTransitionTo : Unit.INSTANCE;
        }
        UUID uuid = this.currentTransitionId;
        Intrinsics.checkNotNull(uuid);
        ((KeyguardTransitionRepositoryImpl) keyguardTransitionInteractor.repository).updateTransition(uuid, 1.0f, TransitionState.FINISHED);
        resetTransitionData();
        return Unit.INSTANCE;
    }
}
