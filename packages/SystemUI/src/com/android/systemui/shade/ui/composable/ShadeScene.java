package com.android.systemui.shade.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import dagger.Lazy;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeScene implements ComposableScene {
    public final BatteryMeterViewController.Factory batteryMeterViewControllerFactory;
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.Shade;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final Lazy notificationStackScrollView;
    public final SaveableSession shadeSession;
    public final StatusBarIconController statusBarIconController;
    public final TintedIconManager.Factory tintedIconManagerFactory;
    public final ShadeSceneViewModel viewModel;

    public ShadeScene(SaveableSession saveableSession, Lazy lazy, ShadeSceneViewModel shadeSceneViewModel, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController, MediaCarouselController mediaCarouselController, MediaHost mediaHost) {
        this.destinationScenes = shadeSceneViewModel.destinationScenes;
        mediaHost.setExpansion(1.0f);
        Boolean bool = Boolean.TRUE;
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        if (!bool.equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
            mediaHostStateHolder.showsOnlyActiveMedia = true;
            Function0 function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
        mediaHost.init(1);
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
