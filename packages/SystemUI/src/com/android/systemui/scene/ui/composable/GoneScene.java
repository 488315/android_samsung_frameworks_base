package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class GoneScene implements ComposableScene {
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.Gone;
    public final Lazy notificationStackScrolLView;
    public final NotificationsPlaceholderViewModel notificationsPlaceholderViewModel;

    public GoneScene(Lazy lazy, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, GoneSceneViewModel goneSceneViewModel) {
        this.destinationScenes = goneSceneViewModel.destinationScenes;
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
