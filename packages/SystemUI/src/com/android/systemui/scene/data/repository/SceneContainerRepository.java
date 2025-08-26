package com.android.systemui.scene.data.repository;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.SceneDataSource;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class SceneContainerRepository {
    public final StateFlowImpl _isVisible;
    public final StateFlowImpl _transitionState;
    public final StateFlowImpl activeTransitionAnimationCount;
    public final List allContentKeys;
    public final StateFlow currentOverlays;
    public final StateFlow currentScene;
    public final SceneDataSource dataSource;
    public final ObservableTransitionState.Idle defaultTransitionState;
    public final StateFlowImpl isRemoteUserInputOngoing;
    public final StateFlowImpl isSceneContainerUserInputOngoing;
    public final ReadonlyStateFlow isVisible;
    public final ReadonlyStateFlow transitionState;

    public SceneContainerRepository(CoroutineScope coroutineScope, SceneContainerConfig sceneContainerConfig, SceneDataSource sceneDataSource) {
        this.dataSource = sceneDataSource;
        this.allContentKeys = CollectionsKt___CollectionsKt.plus((Iterable) sceneContainerConfig.overlayKeys, (Collection) sceneContainerConfig.sceneKeys);
        this.currentScene = sceneDataSource.getCurrentScene();
        this.currentOverlays = sceneDataSource.getCurrentOverlays();
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isVisible = stateFlowImplMutableStateFlow;
        this.isVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        Boolean bool = Boolean.FALSE;
        this.isRemoteUserInputOngoing = StateFlowKt.MutableStateFlow(bool);
        this.isSceneContainerUserInputOngoing = StateFlowKt.MutableStateFlow(bool);
        ObservableTransitionState.Idle idle = new ObservableTransitionState.Idle(sceneContainerConfig.initialSceneKey, null, 2, null);
        this.defaultTransitionState = idle;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._transitionState = stateFlowImplMutableStateFlow2;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(stateFlowImplMutableStateFlow2, new SceneContainerRepository$special$$inlined$flatMapLatest$1(null, this));
        SharingStarted.Companion.getClass();
        this.transitionState = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, SharingStarted.Companion.Eagerly, idle);
        this.activeTransitionAnimationCount = StateFlowKt.MutableStateFlow(0);
    }
}
