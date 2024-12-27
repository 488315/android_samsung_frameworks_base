package com.android.systemui.scene.domain.resolver;

import com.android.compose.animation.scene.SceneKey;
import kotlinx.coroutines.flow.StateFlow;

public interface SceneResolver {
    StateFlow getResolvedScene();

    SceneKey getTargetFamily();

    boolean includesScene(SceneKey sceneKey);
}
