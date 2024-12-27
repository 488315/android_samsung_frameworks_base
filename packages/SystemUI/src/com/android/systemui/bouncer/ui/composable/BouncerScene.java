package com.android.systemui.bouncer.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BouncerScene implements ComposableScene {
    public final ReadonlyStateFlow destinationScenes;
    public final BouncerDialogFactory dialogFactory;
    public final SceneKey key = Scenes.Bouncer;
    public final BouncerViewModel viewModel;

    public BouncerScene(BouncerViewModel bouncerViewModel, BouncerDialogFactory bouncerDialogFactory) {
        this.destinationScenes = bouncerViewModel.destinationScenes;
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
