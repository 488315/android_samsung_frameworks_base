package com.android.systemui.keyguard.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenSceneViewModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class LockscreenScene implements ComposableScene {
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.Lockscreen;
    public final Lazy lockscreenContent;

    public LockscreenScene(LockscreenSceneViewModel lockscreenSceneViewModel, Lazy lazy) {
        this.destinationScenes = lockscreenSceneViewModel.destinationScenes;
    }

    @Override // com.android.systemui.scene.ui.composable.ComposableScene
    public final ReadonlyStateFlow getDestinationScenes() {
        return this.destinationScenes;
    }

    @Override // com.android.systemui.scene.ui.composable.ComposableScene
    public final SceneKey getKey() {
        return this.key;
    }
}
