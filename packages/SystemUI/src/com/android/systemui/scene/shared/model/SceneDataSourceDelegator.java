package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.scene.ui.composable.SceneTransitionLayoutDataSource;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class SceneDataSourceDelegator implements SceneDataSource {
    public final ReadonlyStateFlow currentScene;
    public final StateFlowImpl delegateMutable;
    public final NoOpSceneDataSource noOpDelegate;

    public SceneDataSourceDelegator(CoroutineScope coroutineScope, SceneContainerConfig sceneContainerConfig) {
        SceneKey sceneKey = sceneContainerConfig.initialSceneKey;
        NoOpSceneDataSource noOpSceneDataSource = new NoOpSceneDataSource(sceneKey);
        this.noOpDelegate = noOpSceneDataSource;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(noOpSceneDataSource);
        this.delegateMutable = MutableStateFlow;
        this.currentScene = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SceneDataSourceDelegator$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), sceneKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).changeScene(sceneKey, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final StateFlow getCurrentScene() {
        return this.currentScene;
    }

    public final void setDelegate(SceneTransitionLayoutDataSource sceneTransitionLayoutDataSource) {
        SceneDataSource sceneDataSource = sceneTransitionLayoutDataSource;
        if (sceneTransitionLayoutDataSource == null) {
            sceneDataSource = this.noOpDelegate;
        }
        this.delegateMutable.setValue(sceneDataSource);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void snapToScene(SceneKey sceneKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).snapToScene(sceneKey);
    }

    public final class NoOpSceneDataSource implements SceneDataSource {
        public final ReadonlyStateFlow currentScene;

        public NoOpSceneDataSource(SceneKey sceneKey) {
            this.currentScene = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(sceneKey));
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final StateFlow getCurrentScene() {
            return this.currentScene;
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void snapToScene(SceneKey sceneKey) {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        }
    }
}
