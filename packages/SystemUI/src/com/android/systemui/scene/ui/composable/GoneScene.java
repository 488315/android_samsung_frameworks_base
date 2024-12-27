package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
