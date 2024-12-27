package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SceneDataSource {
    void changeScene(SceneKey sceneKey, TransitionKey transitionKey);

    StateFlow getCurrentScene();

    void snapToScene(SceneKey sceneKey);
}
