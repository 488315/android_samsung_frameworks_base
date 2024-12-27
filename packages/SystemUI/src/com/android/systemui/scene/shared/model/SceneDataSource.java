package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import kotlinx.coroutines.flow.StateFlow;

public interface SceneDataSource {
    void changeScene(SceneKey sceneKey, TransitionKey transitionKey);

    StateFlow getCurrentScene();

    void snapToScene(SceneKey sceneKey);
}
