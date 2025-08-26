package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public interface MutableSceneTransitionLayoutState {
    static boolean isTransitioning$default(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, SceneKey sceneKey, int i) {
        if ((i & 2) != 0) {
            sceneKey = null;
        }
        TransitionState.Transition currentTransition = ((MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState).getCurrentTransition();
        if (currentTransition != null) {
            if (sceneKey == null) {
                currentTransition.getClass();
            } else if (Intrinsics.areEqual(currentTransition.toContent, sceneKey)) {
            }
            return true;
        }
        return false;
    }

    static Pair setTargetScene$default(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, SceneKey sceneKey, CoroutineScope coroutineScope) {
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState;
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        return AnimateToSceneKt.animateToScene(coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, null);
    }

    static void snapTo$default(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, SceneKey sceneKey, Set set, int i) {
        if ((i & 1) != 0) {
            sceneKey = ((MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState).getTransitionState().getCurrentScene();
        }
        if ((i & 2) != 0) {
            set = ((MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState).getTransitionState().getCurrentOverlays();
        }
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState;
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        List currentTransitions = mutableSceneTransitionLayoutStateImpl.getCurrentTransitions();
        int size = currentTransitions.size();
        for (int i2 = 0; i2 < size; i2++) {
            mutableSceneTransitionLayoutStateImpl.finishTransition((TransitionState.Transition) currentTransitions.get(i2));
        }
        if (mutableSceneTransitionLayoutStateImpl.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() != 1) {
            throw new IllegalStateException("Check failed.");
        }
        if (!mutableSceneTransitionLayoutStateImpl.getCurrentTransitions().isEmpty()) {
            throw new IllegalStateException("Check failed.");
        }
        ((SnapshotMutableStateImpl) mutableSceneTransitionLayoutStateImpl.transitionStates$delegate).setValue(Collections.singletonList(new TransitionState.Idle(sceneKey, set)));
    }

    default TransitionState.Transition getCurrentTransition() {
        TransitionState transitionState = ((MutableSceneTransitionLayoutStateImpl) this).getTransitionState();
        if (transitionState instanceof TransitionState.Transition) {
            return (TransitionState.Transition) transitionState;
        }
        return null;
    }
}
