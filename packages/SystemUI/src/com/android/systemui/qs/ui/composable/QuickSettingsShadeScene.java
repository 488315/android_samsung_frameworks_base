package com.android.systemui.qs.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeSceneViewModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import dagger.Lazy;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QuickSettingsShadeScene implements ComposableScene {
    public final BatteryMeterViewController.Factory batteryMeterViewControllerFactory;
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.QuickSettingsShade;
    public final Lazy lockscreenContent;
    public final ShadeHeaderViewModel shadeHeaderViewModel;
    public final StatusBarIconController statusBarIconController;
    public final TintedIconManager.Factory tintedIconManagerFactory;
    public final QuickSettingsShadeSceneViewModel viewModel;

    public QuickSettingsShadeScene(QuickSettingsShadeSceneViewModel quickSettingsShadeSceneViewModel, Lazy lazy, ShadeHeaderViewModel shadeHeaderViewModel, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController) {
        this.destinationScenes = quickSettingsShadeSceneViewModel.destinationScenes;
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
