package com.android.systemui.notifications.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.notifications.ui.viewmodel.NotificationsShadeSceneViewModel;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.shade.ui.viewmodel.OverlayShadeViewModel;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class NotificationsShadeScene implements ComposableScene {
    public final BatteryMeterViewController.Factory batteryMeterViewControllerFactory;
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.NotificationsShade;
    public final Lazy lockscreenContent;
    public final NotificationsPlaceholderViewModel notificationsPlaceholderViewModel;
    public final OverlayShadeViewModel overlayShadeViewModel;
    public final ShadeHeaderViewModel shadeHeaderViewModel;
    public final SaveableSession shadeSession;
    public final Lazy stackScrollView;
    public final StatusBarIconController statusBarIconController;
    public final TintedIconManager.Factory tintedIconManagerFactory;

    public NotificationsShadeScene(NotificationsShadeSceneViewModel notificationsShadeSceneViewModel, OverlayShadeViewModel overlayShadeViewModel, ShadeHeaderViewModel shadeHeaderViewModel, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController, SaveableSession saveableSession, Lazy lazy, Lazy lazy2) {
        this.destinationScenes = notificationsShadeSceneViewModel.destinationScenes;
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
