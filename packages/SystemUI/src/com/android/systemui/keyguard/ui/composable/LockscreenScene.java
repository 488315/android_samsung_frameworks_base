package com.android.systemui.keyguard.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenSceneViewModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
