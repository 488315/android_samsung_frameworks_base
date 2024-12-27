package com.android.systemui.scene.data.repository;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.SceneDataSource;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class SceneContainerRepository {
    public final StateFlowImpl _isVisible;
    public final StateFlowImpl _transitionState;
    public final SceneContainerConfig config;
    public final StateFlow currentScene;
    public final SceneDataSource dataSource;
    public final ObservableTransitionState.Idle defaultTransitionState;
    public final ReadonlyStateFlow transitionState;
    public final ReadonlyStateFlow isVisible = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Boolean.TRUE));
    public final StateFlowImpl isRemoteUserInteractionOngoing = StateFlowKt.MutableStateFlow(Boolean.FALSE);

    public SceneContainerRepository(CoroutineScope coroutineScope, SceneContainerConfig sceneContainerConfig, SceneDataSource sceneDataSource) {
        this.config = sceneContainerConfig;
        this.dataSource = sceneDataSource;
        this.currentScene = sceneDataSource.getCurrentScene();
        ObservableTransitionState.Idle idle = new ObservableTransitionState.Idle(sceneContainerConfig.initialSceneKey);
        this.defaultTransitionState = idle;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(StateFlowKt.MutableStateFlow(null), new SceneContainerRepository$special$$inlined$flatMapLatest$1(null, this));
        SharingStarted.Companion.getClass();
        this.transitionState = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.Eagerly, idle);
    }
}
