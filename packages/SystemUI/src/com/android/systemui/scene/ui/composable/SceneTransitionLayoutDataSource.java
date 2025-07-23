package com.android.systemui.scene.ui.composable;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.compose.animation.scene.AnimateOverlayKt;
import com.android.compose.animation.scene.AnimateToSceneKt;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda1;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.systemui.scene.shared.model.SceneDataSource;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneTransitionLayoutDataSource implements SceneDataSource {
    public final CoroutineScope coroutineScope;
    public final ReadonlyStateFlow currentOverlays;
    public final ReadonlyStateFlow currentScene;
    public final MutableSceneTransitionLayoutState state;

    public SceneTransitionLayoutDataSource(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, CoroutineScope coroutineScope) {
        this.state = mutableSceneTransitionLayoutState;
        this.coroutineScope = coroutineScope;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(FlowKt.distinctUntilChanged(SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(mutableSceneTransitionLayoutState, 5))), new SceneTransitionLayoutDataSource$special$$inlined$flatMapLatest$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.currentScene = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState).getTransitionState().getCurrentScene());
        this.currentOverlays = FlowKt.stateIn(SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.systemui.scene.ui.composable.SceneTransitionLayoutDataSource$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((MutableSceneTransitionLayoutStateImpl) SceneTransitionLayoutDataSource.this.state).getTransitionState().getCurrentOverlays();
            }
        }), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptySet.INSTANCE);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void changeScene(SceneKey sceneKey, TransitionKey transitionKey) {
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) this.state;
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        AnimateToSceneKt.animateToScene(this.coroutineScope, mutableSceneTransitionLayoutStateImpl, sceneKey, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void freezeAndAnimateToCurrentState() {
        TransitionState.Transition currentTransition = this.state.getCurrentTransition();
        if (currentTransition != null) {
            currentTransition.freezeAndAnimateToCurrentState();
        }
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
        ((MutableSceneTransitionLayoutStateImpl) this.state).hideOverlay(overlayKey, this.coroutineScope, transitionKey);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void instantlyHideOverlay(OverlayKey overlayKey) {
        MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState = this.state;
        MutableSceneTransitionLayoutState.snapTo$default(mutableSceneTransitionLayoutState, null, SetsKt___SetsKt.minus(((MutableSceneTransitionLayoutStateImpl) mutableSceneTransitionLayoutState).getTransitionState().getCurrentOverlays(), overlayKey), 1);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void showOverlay(OverlayKey overlayKey) {
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) this.state;
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        TransitionState transitionState = mutableSceneTransitionLayoutStateImpl.getTransitionState();
        if (transitionState.getCurrentOverlays().contains(overlayKey)) {
            return;
        }
        SceneKey currentScene = transitionState.getCurrentScene();
        boolean z = transitionState instanceof TransitionState.Transition.ShowOrHideOverlay;
        CoroutineScope coroutineScope = this.coroutineScope;
        if (z) {
            TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (TransitionState.Transition.ShowOrHideOverlay) transitionState;
            if (Intrinsics.areEqual(showOrHideOverlay.overlay, overlayKey) && Intrinsics.areEqual(showOrHideOverlay.fromOrToScene, currentScene)) {
                AnimateOverlayKt.showOrHideOverlay(coroutineScope, mutableSceneTransitionLayoutStateImpl, overlayKey, currentScene, true, null, showOrHideOverlay, Intrinsics.areEqual(overlayKey, showOrHideOverlay.fromContent));
                return;
            }
        }
        AnimateOverlayKt.showOrHideOverlay(coroutineScope, mutableSceneTransitionLayoutStateImpl, overlayKey, currentScene, true, null, null, false);
    }

    @Override // com.android.systemui.scene.shared.model.SceneDataSource
    public final void snapToScene(SceneKey sceneKey) {
        MutableSceneTransitionLayoutState.snapTo$default(this.state, sceneKey, null, 2);
    }
}
