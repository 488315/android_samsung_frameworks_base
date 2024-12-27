package com.android.systemui.bouncer.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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
