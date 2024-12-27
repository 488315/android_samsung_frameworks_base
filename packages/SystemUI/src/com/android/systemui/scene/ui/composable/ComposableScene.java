package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ComposableScene {
    ReadonlyStateFlow getDestinationScenes();

    SceneKey getKey();
}
