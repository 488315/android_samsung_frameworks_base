package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.AnimateToSceneKt;
import com.android.compose.animation.scene.BaseSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.ObservableTransitionStateKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.TransitionState;
import com.android.systemui.scene.shared.model.SceneDataSource;
import java.util.Collections;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

public final class SceneTransitionLayoutDataSource implements SceneDataSource {
    public final CoroutineScope coroutineScope;
    public final ReadonlyStateFlow currentScene;
    public final MutableSceneTransitionLayoutState state;

    public SceneTransitionLayoutDataSource(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, CoroutineScope coroutineScope) {
        this.state = mutableSceneTransitionLayoutState;
        this.coroutineScope = coroutineScope;
        this.currentScene = FlowKt.stateIn(FlowKt.transformLatest(ObservableTransitionStateKt.observableTransitionState(mutableSceneTransitionLayoutState), new SceneTransitionLayoutDataSource$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), ((BaseSceneTransitionLayoutState) mutableSceneTransitionLayoutState).getTransitionState().getCurrentScene());
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) this.state;
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        AnimateToSceneKt.animateToScene(this.coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final StateFlow getCurrentScene() {
        return this.currentScene;
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void snapToScene(SceneKey sceneKey) {
        BaseSceneTransitionLayoutState baseSceneTransitionLayoutState = (BaseSceneTransitionLayoutState) this.state;
        baseSceneTransitionLayoutState.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        while (!baseSceneTransitionLayoutState.getCurrentTransitions().isEmpty()) {
            TransitionState.Transition transition = (TransitionState.Transition) baseSceneTransitionLayoutState.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().get(0);
            baseSceneTransitionLayoutState.finishTransition$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition, transition.getCurrentScene());
        }
        if (baseSceneTransitionLayoutState.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() != 1) {
            throw new IllegalStateException("Check failed.".toString());
        }
        baseSceneTransitionLayoutState.setTransitionStates(Collections.singletonList(new TransitionState.Idle(sceneKey)));
    }
}
