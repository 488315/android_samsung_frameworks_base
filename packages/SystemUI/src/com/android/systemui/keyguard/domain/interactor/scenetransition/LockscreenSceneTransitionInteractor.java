package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.data.repository.LockscreenSceneTransitionRepository;
import com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d7, code lost:
    
        if (r15 != r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0142, code lost:
    
        if (r15 != r1) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0164, code lost:
    
        if (r14.transitionKtfTo$1(r13, r0) == r1) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ad A[PHI: r13 r14
      0x00ad: PHI (r13v3 com.android.compose.animation.scene.ObservableTransitionState$Transition) = 
      (r13v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r13v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r13v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r13v14 com.android.compose.animation.scene.ObservableTransitionState$Transition)
     binds: [B:25:0x0083, B:27:0x008f, B:29:0x00a9, B:21:0x0068] A[DONT_GENERATE, DONT_INLINE]
      0x00ad: PHI (r14v1 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) = 
      (r14v0 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
      (r14v0 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
      (r14v0 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
      (r14v6 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
     binds: [B:25:0x0083, B:27:0x008f, B:29:0x00a9, B:21:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010b A[PHI: r13 r14
      0x010b: PHI (r13v7 com.android.compose.animation.scene.ObservableTransitionState$Transition) = 
      (r13v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r13v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r13v18 com.android.compose.animation.scene.ObservableTransitionState$Transition)
     binds: [B:44:0x00fa, B:46:0x0108, B:19:0x004e] A[DONT_GENERATE, DONT_INLINE]
      0x010b: PHI (r14v3 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor) = 
      (r14v0 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
      (r14v0 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
      (r14v10 com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor)
     binds: [B:44:0x00fa, B:46:0x0108, B:19:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleTransition(ObservableTransitionState.Transition transition, LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor, Continuation continuation) throws Throwable {
        LockscreenSceneTransitionInteractor$handleTransition$1 lockscreenSceneTransitionInteractor$handleTransition$1;
        Object objStartTransition$1;
        KeyguardState keyguardState;
        Object objStartTransition$12;
        lockscreenSceneTransitionInteractor.getClass();
        if (continuation instanceof LockscreenSceneTransitionInteractor$handleTransition$1) {
            lockscreenSceneTransitionInteractor$handleTransition$1 = (LockscreenSceneTransitionInteractor$handleTransition$1) continuation;
            int i = lockscreenSceneTransitionInteractor$handleTransition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                lockscreenSceneTransitionInteractor$handleTransition$1.label = i - Integer.MIN_VALUE;
            } else {
                lockscreenSceneTransitionInteractor$handleTransition$1 = new LockscreenSceneTransitionInteractor$handleTransition$1(lockscreenSceneTransitionInteractor, continuation);
            }
        }
        Object obj = lockscreenSceneTransitionInteractor$handleTransition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = lockscreenSceneTransitionInteractor$handleTransition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ContentKey contentKey = transition.fromContent;
            SceneKey sceneKey = Scenes.Lockscreen;
            if (Intrinsics.areEqual(contentKey, sceneKey)) {
                if (lockscreenSceneTransitionInteractor.currentTransitionId == null || lockscreenSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to != KeyguardState.UNDEFINED) {
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                    lockscreenSceneTransitionInteractor$handleTransition$1.label = 2;
                    TransitionInfo transitionInfo = new TransitionInfo("LockscreenSceneTransitionInteractor", lockscreenSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, KeyguardState.UNDEFINED, null, TransitionModeOnCanceled.RESET);
                    lockscreenSceneTransitionInteractor.repository.nextLockscreenTargetState.setValue(null);
                    objStartTransition$1 = lockscreenSceneTransitionInteractor.startTransition$1(transitionInfo, lockscreenSceneTransitionInteractor$handleTransition$1);
                    if (objStartTransition$1 != coroutineSingletons) {
                    }
                } else {
                    KeyguardState keyguardState2 = ((TransitionStep) lockscreenSceneTransitionInteractor.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue()).from;
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                    lockscreenSceneTransitionInteractor$handleTransition$1.label = 1;
                    if (lockscreenSceneTransitionInteractor.transitionKtfTo$1(keyguardState2, lockscreenSceneTransitionInteractor$handleTransition$1) != coroutineSingletons) {
                    }
                }
            } else if (!Intrinsics.areEqual(transition.toContent, sceneKey)) {
                KeyguardState keyguardState3 = KeyguardState.UNDEFINED;
                lockscreenSceneTransitionInteractor$handleTransition$1.label = 5;
            } else if (lockscreenSceneTransitionInteractor.currentTransitionId != null) {
                KeyguardState keyguardState4 = KeyguardState.UNDEFINED;
                lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
                lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                lockscreenSceneTransitionInteractor$handleTransition$1.label = 3;
                if (lockscreenSceneTransitionInteractor.transitionKtfTo$1(keyguardState4, lockscreenSceneTransitionInteractor$handleTransition$1) != coroutineSingletons) {
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
                    lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                    lockscreenSceneTransitionInteractor$handleTransition$1.label = 4;
                    lockscreenSceneTransitionInteractor.getClass();
                    KeyguardState keyguardState5 = KeyguardState.UNDEFINED;
                    LockscreenSceneTransitionRepository lockscreenSceneTransitionRepository = lockscreenSceneTransitionInteractor.repository;
                    keyguardState = (KeyguardState) lockscreenSceneTransitionRepository.nextLockscreenTargetState.getValue();
                    if (keyguardState == null) {
                    }
                    TransitionInfo transitionInfo2 = new TransitionInfo("LockscreenSceneTransitionInteractor", keyguardState5, keyguardState, null, TransitionModeOnCanceled.RESET);
                    lockscreenSceneTransitionRepository.nextLockscreenTargetState.setValue(null);
                    objStartTransition$12 = lockscreenSceneTransitionInteractor.startTransition$1(transitionInfo2, lockscreenSceneTransitionInteractor$handleTransition$1);
                    if (objStartTransition$12 != coroutineSingletons) {
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            transition = (ObservableTransitionState.Transition) lockscreenSceneTransitionInteractor$handleTransition$1.L$1;
            lockscreenSceneTransitionInteractor = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$handleTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
            lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
            lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
            lockscreenSceneTransitionInteractor$handleTransition$1.label = 2;
            TransitionInfo transitionInfo3 = new TransitionInfo("LockscreenSceneTransitionInteractor", lockscreenSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, KeyguardState.UNDEFINED, null, TransitionModeOnCanceled.RESET);
            lockscreenSceneTransitionInteractor.repository.nextLockscreenTargetState.setValue(null);
            objStartTransition$1 = lockscreenSceneTransitionInteractor.startTransition$1(transitionInfo3, lockscreenSceneTransitionInteractor$handleTransition$1);
            if (objStartTransition$1 != coroutineSingletons) {
                objStartTransition$1 = Unit.INSTANCE;
            }
        } else if (i2 == 2) {
            transition = (ObservableTransitionState.Transition) lockscreenSceneTransitionInteractor$handleTransition$1.L$1;
            lockscreenSceneTransitionInteractor = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$handleTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
            StandaloneCoroutine standaloneCoroutine = lockscreenSceneTransitionInteractor.progressJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            lockscreenSceneTransitionInteractor.progressJob = CoroutineTracingKt.launchTraced$default(lockscreenSceneTransitionInteractor.applicationScope, null, null, new LockscreenSceneTransitionInteractor$collectProgress$1(transition, lockscreenSceneTransitionInteractor, null), 7);
        } else if (i2 == 3) {
            transition = (ObservableTransitionState.Transition) lockscreenSceneTransitionInteractor$handleTransition$1.L$1;
            lockscreenSceneTransitionInteractor = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$handleTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
            lockscreenSceneTransitionInteractor$handleTransition$1.L$0 = lockscreenSceneTransitionInteractor;
            lockscreenSceneTransitionInteractor$handleTransition$1.L$1 = transition;
            lockscreenSceneTransitionInteractor$handleTransition$1.label = 4;
            lockscreenSceneTransitionInteractor.getClass();
            KeyguardState keyguardState52 = KeyguardState.UNDEFINED;
            LockscreenSceneTransitionRepository lockscreenSceneTransitionRepository2 = lockscreenSceneTransitionInteractor.repository;
            keyguardState = (KeyguardState) lockscreenSceneTransitionRepository2.nextLockscreenTargetState.getValue();
            if (keyguardState == null) {
                LockscreenSceneTransitionRepository.Companion.getClass();
                keyguardState = LockscreenSceneTransitionRepository.DEFAULT_STATE;
            }
            TransitionInfo transitionInfo22 = new TransitionInfo("LockscreenSceneTransitionInteractor", keyguardState52, keyguardState, null, TransitionModeOnCanceled.RESET);
            lockscreenSceneTransitionRepository2.nextLockscreenTargetState.setValue(null);
            objStartTransition$12 = lockscreenSceneTransitionInteractor.startTransition$1(transitionInfo22, lockscreenSceneTransitionInteractor$handleTransition$1);
            if (objStartTransition$12 != coroutineSingletons) {
                objStartTransition$12 = Unit.INSTANCE;
            }
        } else {
            if (i2 != 4) {
                if (i2 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            transition = (ObservableTransitionState.Transition) lockscreenSceneTransitionInteractor$handleTransition$1.L$1;
            lockscreenSceneTransitionInteractor = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$handleTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
            StandaloneCoroutine standaloneCoroutine2 = lockscreenSceneTransitionInteractor.progressJob;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
            }
            lockscreenSceneTransitionInteractor.progressJob = CoroutineTracingKt.launchTraced$default(lockscreenSceneTransitionInteractor.applicationScope, null, null, new LockscreenSceneTransitionInteractor$collectProgress$1(transition, lockscreenSceneTransitionInteractor, null), 7);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object finishCurrentTransition$1(Continuation continuation) {
        LockscreenSceneTransitionInteractor$finishCurrentTransition$1 lockscreenSceneTransitionInteractor$finishCurrentTransition$1;
        if (continuation instanceof LockscreenSceneTransitionInteractor$finishCurrentTransition$1) {
            lockscreenSceneTransitionInteractor$finishCurrentTransition$1 = (LockscreenSceneTransitionInteractor$finishCurrentTransition$1) continuation;
            int i = lockscreenSceneTransitionInteractor$finishCurrentTransition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                lockscreenSceneTransitionInteractor$finishCurrentTransition$1.label = i - Integer.MIN_VALUE;
            } else {
                lockscreenSceneTransitionInteractor$finishCurrentTransition$1 = new LockscreenSceneTransitionInteractor$finishCurrentTransition$1(this, continuation);
            }
        }
        Object obj = lockscreenSceneTransitionInteractor$finishCurrentTransition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = lockscreenSceneTransitionInteractor$finishCurrentTransition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            UUID uuid = this.currentTransitionId;
            uuid.getClass();
            TransitionState transitionState = TransitionState.FINISHED;
            lockscreenSceneTransitionInteractor$finishCurrentTransition$1.L$0 = this;
            lockscreenSceneTransitionInteractor$finishCurrentTransition$1.label = 1;
            if (this.internalTransitionInteractor.updateTransition(uuid, 1.0f, transitionState, lockscreenSceneTransitionInteractor$finishCurrentTransition$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$finishCurrentTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.resetTransitionData$1();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object finishReversedTransitionTo$1(KeyguardState keyguardState, Continuation continuation) throws Throwable {
        LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1;
        LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor;
        LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor2;
        if (continuation instanceof LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1) {
            lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 = (LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1) continuation;
            int i = lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.label = i - Integer.MIN_VALUE;
            } else {
                lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 = new LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1(this, continuation);
            }
        }
        Object objStartTransition = lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStartTransition);
            InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor = this.internalTransitionInteractor;
            TransitionInfo transitionInfo = new TransitionInfo("LockscreenSceneTransitionInteractor", internalKeyguardTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, keyguardState, null, TransitionModeOnCanceled.REVERSE);
            lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$0 = this;
            lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$1 = this;
            lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.label = 1;
            objStartTransition = ((KeyguardTransitionRepositoryImpl) internalKeyguardTransitionInteractor.repository).startTransition(transitionInfo, lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1);
            if (objStartTransition != coroutineSingletons) {
                lockscreenSceneTransitionInteractor = this;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            lockscreenSceneTransitionInteractor2 = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$0;
            ResultKt.throwOnFailure(objStartTransition);
            lockscreenSceneTransitionInteractor2.resetTransitionData$1();
            return Unit.INSTANCE;
        }
        this = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$1;
        lockscreenSceneTransitionInteractor = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$0;
        ResultKt.throwOnFailure(objStartTransition);
        this.currentTransitionId = (UUID) objStartTransition;
        InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor2 = lockscreenSceneTransitionInteractor.internalTransitionInteractor;
        UUID uuid = lockscreenSceneTransitionInteractor.currentTransitionId;
        uuid.getClass();
        TransitionState transitionState = TransitionState.FINISHED;
        lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$0 = lockscreenSceneTransitionInteractor;
        lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.L$1 = null;
        lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1.label = 2;
        if (internalKeyguardTransitionInteractor2.updateTransition(uuid, 1.0f, transitionState, lockscreenSceneTransitionInteractor$finishReversedTransitionTo$1) != coroutineSingletons) {
            lockscreenSceneTransitionInteractor2 = lockscreenSceneTransitionInteractor;
            lockscreenSceneTransitionInteractor2.resetTransitionData$1();
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object startTransition$1(TransitionInfo transitionInfo, ContinuationImpl continuationImpl) throws Throwable {
        LockscreenSceneTransitionInteractor$startTransition$1 lockscreenSceneTransitionInteractor$startTransition$1;
        if (continuationImpl instanceof LockscreenSceneTransitionInteractor$startTransition$1) {
            lockscreenSceneTransitionInteractor$startTransition$1 = (LockscreenSceneTransitionInteractor$startTransition$1) continuationImpl;
            int i = lockscreenSceneTransitionInteractor$startTransition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                lockscreenSceneTransitionInteractor$startTransition$1.label = i - Integer.MIN_VALUE;
            } else {
                lockscreenSceneTransitionInteractor$startTransition$1 = new LockscreenSceneTransitionInteractor$startTransition$1(this, continuationImpl);
            }
        }
        Object objStartTransition = lockscreenSceneTransitionInteractor$startTransition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = lockscreenSceneTransitionInteractor$startTransition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStartTransition);
            if (this.currentTransitionId != null) {
                resetTransitionData$1();
            }
            lockscreenSceneTransitionInteractor$startTransition$1.L$0 = this;
            lockscreenSceneTransitionInteractor$startTransition$1.label = 1;
            objStartTransition = ((KeyguardTransitionRepositoryImpl) this.internalTransitionInteractor.repository).startTransition(transitionInfo, lockscreenSceneTransitionInteractor$startTransition$1);
            if (objStartTransition == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (LockscreenSceneTransitionInteractor) lockscreenSceneTransitionInteractor$startTransition$1.L$0;
            ResultKt.throwOnFailure(objStartTransition);
        }
        this.currentTransitionId = (UUID) objStartTransition;
        return Unit.INSTANCE;
    }

    public final Object transitionKtfTo$1(KeyguardState keyguardState, Continuation continuation) throws Throwable {
        TransitionStep transitionStep = (TransitionStep) this.transitionInteractor.transitionState.$$delegate_0.getValue();
        KeyguardState keyguardState2 = transitionStep.to;
        if (keyguardState2 == keyguardState) {
            if (transitionStep.transitionState == TransitionState.FINISHED) {
                resetTransitionData$1();
                return Unit.INSTANCE;
            }
        }
        if (keyguardState == null || keyguardState2 == keyguardState) {
            Object objFinishCurrentTransition$1 = finishCurrentTransition$1(continuation);
            return objFinishCurrentTransition$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? objFinishCurrentTransition$1 : Unit.INSTANCE;
        }
        Object objFinishReversedTransitionTo$1 = finishReversedTransitionTo$1(keyguardState, continuation);
        return objFinishReversedTransitionTo$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? objFinishReversedTransitionTo$1 : Unit.INSTANCE;
    }
}
