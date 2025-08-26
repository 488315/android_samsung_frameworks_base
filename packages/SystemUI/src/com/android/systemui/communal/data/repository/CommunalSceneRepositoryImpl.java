package com.android.systemui.communal.data.repository;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.scene.shared.model.SceneDataSource;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class CommunalSceneRepositoryImpl implements CommunalSceneRepository {
    public final StateFlowImpl _communalContainerOrientation;
    public final StateFlowImpl _transitionState;
    public final ReadonlyStateFlow communalContainerOrientation;
    public final StateFlow currentScene;
    public final ObservableTransitionState.Idle defaultTransitionState;
    public final SceneDataSource sceneDataSource;
    public final ReadonlyStateFlow transitionState;

    public CommunalSceneRepositoryImpl(CoroutineScope coroutineScope, SceneDataSource sceneDataSource) {
        this.sceneDataSource = sceneDataSource;
        this.currentScene = sceneDataSource.getCurrentScene();
        ObservableTransitionState.Idle idle = new ObservableTransitionState.Idle(CommunalScenes.Default, null, 2, null);
        this.defaultTransitionState = idle;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._transitionState = stateFlowImplMutableStateFlow;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(stateFlowImplMutableStateFlow, new CommunalSceneRepositoryImpl$special$$inlined$flatMapLatest$1(null, this));
        SharingStarted.Companion.getClass();
        this.transitionState = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, SharingStarted.Companion.Lazily, idle);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(0);
        this._communalContainerOrientation = stateFlowImplMutableStateFlow2;
        this.communalContainerOrientation = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }
}
