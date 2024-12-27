package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public interface ComposableScene {
    ReadonlyStateFlow getDestinationScenes();

    SceneKey getKey();
}
