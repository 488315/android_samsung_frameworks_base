package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.scene.ui.composable.SceneTransitionLayoutDataSource;
import kotlin.collections.EmptySet;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneDataSourceDelegator implements SceneDataSource {
    public final ReadonlyStateFlow currentOverlays;
    public final ReadonlyStateFlow currentScene;
    public final StateFlowImpl delegateMutable;
    public final NoOpSceneDataSource noOpDelegate;

    public SceneDataSourceDelegator(CoroutineScope coroutineScope, SceneContainerConfig sceneContainerConfig) {
        NoOpSceneDataSource noOpSceneDataSource = new NoOpSceneDataSource(sceneContainerConfig.initialSceneKey);
        this.noOpDelegate = noOpSceneDataSource;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(noOpSceneDataSource);
        this.delegateMutable = MutableStateFlow;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(MutableStateFlow, new SceneDataSourceDelegator$special$$inlined$flatMapLatest$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.currentScene = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), sceneContainerConfig.initialSceneKey);
        this.currentOverlays = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SceneDataSourceDelegator$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptySet.INSTANCE);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).changeScene(sceneKey, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void freezeAndAnimateToCurrentState() {
        ((SceneDataSource) this.delegateMutable.getValue()).freezeAndAnimateToCurrentState();
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final StateFlow getCurrentOverlays() {
        return this.currentOverlays;
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final StateFlow getCurrentScene() {
        return this.currentScene;
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void hideOverlay(OverlayKey overlayKey, TransitionKey transitionKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).hideOverlay(overlayKey, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void instantlyHideOverlay(OverlayKey overlayKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).instantlyHideOverlay(overlayKey);
    }

    public final void setDelegate(SceneTransitionLayoutDataSource sceneTransitionLayoutDataSource) {
        SceneDataSource sceneDataSource = sceneTransitionLayoutDataSource;
        if (sceneTransitionLayoutDataSource == null) {
            sceneDataSource = this.noOpDelegate;
        }
        this.delegateMutable.setValue(sceneDataSource);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void showOverlay(OverlayKey overlayKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).showOverlay(overlayKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void snapToScene(SceneKey sceneKey) {
        ((SceneDataSource) this.delegateMutable.getValue()).snapToScene(sceneKey);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoOpSceneDataSource implements SceneDataSource {
        public final ReadonlyStateFlow currentOverlays = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(EmptySet.INSTANCE));
        public final ReadonlyStateFlow currentScene;

        public NoOpSceneDataSource(SceneKey sceneKey) {
            this.currentScene = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(sceneKey));
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final StateFlow getCurrentOverlays() {
            return this.currentOverlays;
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final StateFlow getCurrentScene() {
            return this.currentScene;
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void instantlyHideOverlay(OverlayKey overlayKey) {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void showOverlay(OverlayKey overlayKey) {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void snapToScene(SceneKey sceneKey) {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void freezeAndAnimateToCurrentState() {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        }

        @Override // com.android.systemui.scene.shared.model.SceneDataSource
        public final void hideOverlay(OverlayKey overlayKey, TransitionKey transitionKey) {
        }
    }
}
