package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public interface SceneDataSource {
    void changeScene(SceneKey sceneKey, TransitionKey transitionKey);

    void freezeAndAnimateToCurrentState();

    StateFlow getCurrentOverlays();

    StateFlow getCurrentScene();

    void hideOverlay(OverlayKey overlayKey, TransitionKey transitionKey);

    void instantlyHideOverlay(OverlayKey overlayKey);

    void showOverlay(OverlayKey overlayKey);

    void snapToScene(SceneKey sceneKey);
}
