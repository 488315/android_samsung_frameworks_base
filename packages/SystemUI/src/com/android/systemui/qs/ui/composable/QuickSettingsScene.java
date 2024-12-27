package com.android.systemui.qs.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import dagger.Lazy;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QuickSettingsScene implements ComposableScene {
    public final BatteryMeterViewController.Factory batteryMeterViewControllerFactory;
    public final ReadonlyStateFlow destinationScenes;
    public final SceneKey key = Scenes.QuickSettings;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final Lazy notificationStackScrollView;
    public final NotificationsPlaceholderViewModel notificationsPlaceholderViewModel;
    public final SaveableSession shadeSession;
    public final StatusBarIconController statusBarIconController;
    public final TintedIconManager.Factory tintedIconManagerFactory;
    public final QuickSettingsSceneViewModel viewModel;

    public QuickSettingsScene(CoroutineScope coroutineScope, SaveableSession saveableSession, Lazy lazy, QuickSettingsSceneViewModel quickSettingsSceneViewModel, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController, MediaCarouselController mediaCarouselController, MediaHost mediaHost) {
        this.destinationScenes = FlowKt.stateIn(quickSettingsSceneViewModel.destinationScenes, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), MapsKt__MapsKt.emptyMap());
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
