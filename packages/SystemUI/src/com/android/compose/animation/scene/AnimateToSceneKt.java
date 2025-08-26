package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class AnimateToSceneKt {
    public static final Pair animateToScene(CoroutineScope coroutineScope, MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SceneKey sceneKey, TransitionKey transitionKey) {
        TransitionState transitionState = mutableSceneTransitionLayoutStateImpl.getTransitionState();
        if (Intrinsics.areEqual(transitionState.getCurrentScene(), sceneKey)) {
            return null;
        }
        if ((transitionState instanceof TransitionState.Idle) || (transitionState instanceof TransitionState.Transition.ShowOrHideOverlay) || (transitionState instanceof TransitionState.Transition.ReplaceOverlay)) {
            return animateToScene$default(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey, false, null, transitionState.getCurrentScene(), false, 160);
        }
        if (!(transitionState instanceof TransitionState.Transition.ChangeScene)) {
            throw new NoWhenBranchMatchedException();
        }
        TransitionState.Transition.ChangeScene changeScene = (TransitionState.Transition.ChangeScene) transitionState;
        boolean zIsInitiatedByUserInput = changeScene.isInitiatedByUserInput();
        SceneKey sceneKey2 = changeScene.toScene;
        boolean zAreEqual = Intrinsics.areEqual(sceneKey2, sceneKey);
        SceneKey sceneKey3 = changeScene.fromScene;
        if (zAreEqual) {
            if (Intrinsics.areEqual(sceneKey3, transitionState.getCurrentScene())) {
                return animateToScene$default(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey, zIsInitiatedByUserInput, (TransitionState.Transition) transitionState, null, false, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
            }
            throw new IllegalStateException("Check failed.");
        }
        if (Intrinsics.areEqual(sceneKey3, sceneKey)) {
            if (Intrinsics.areEqual(sceneKey2, transitionState.getCurrentScene())) {
                return animateToScene$default(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey, zIsInitiatedByUserInput, changeScene, null, false, 192);
            }
            throw new IllegalStateException("Check failed.");
        }
        ((DefaultInterruptionHandler) mutableSceneTransitionLayoutStateImpl.transitions.interruptionHandler).getClass();
        InterruptionResult interruptionResult = new InterruptionResult(changeScene.getCurrentScene(), true);
        SceneKey sceneKey4 = interruptionResult.animateFrom;
        if (Intrinsics.areEqual(sceneKey4, sceneKey2) || Intrinsics.areEqual(sceneKey4, sceneKey3)) {
            boolean z = interruptionResult.chain;
            if (z && !Intrinsics.areEqual(sceneKey4, transitionState.getCurrentScene())) {
                animateToScene(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey4, null);
            }
            return animateToScene$default(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey, zIsInitiatedByUserInput, null, sceneKey4, z, 32);
        }
        throw new IllegalStateException(("InterruptionResult.animateFrom must be either the fromScene (" + sceneKey3.debugName + ") or the toScene (" + sceneKey2.debugName + ") of the interrupted transition.").toString());
    }

    public static Pair animateToScene$default(CoroutineScope coroutineScope, MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SceneKey sceneKey, TransitionKey transitionKey, boolean z, TransitionState.Transition transition, SceneKey sceneKey2, boolean z2, int i) {
        boolean z3 = (i & 32) == 0;
        SceneKey currentScene = (i & 64) != 0 ? mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentScene() : sceneKey2;
        boolean z4 = (i & 128) == 0 ? z2 : true;
        OneOffAnimation oneOffAnimation = new OneOffAnimation();
        float f = z3 ? 0.0f : 1.0f;
        OneOffSceneTransition oneOffSceneTransition = z3 ? new OneOffSceneTransition(transitionKey, sceneKey, currentScene, sceneKey, z, transition, oneOffAnimation) : new OneOffSceneTransition(transitionKey, currentScene, sceneKey, sceneKey, z, transition, oneOffAnimation);
        oneOffAnimation.onRun = new AnimateContentKt$animateContent$1(oneOffSceneTransition, mutableSceneTransitionLayoutStateImpl, f, oneOffAnimation, null);
        return new Pair(oneOffSceneTransition, mutableSceneTransitionLayoutStateImpl.startTransitionImmediately(coroutineScope, oneOffSceneTransition, z4));
    }
}
