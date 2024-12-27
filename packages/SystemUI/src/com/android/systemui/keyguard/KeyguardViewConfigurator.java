package com.android.systemui.keyguard;

import android.content.Context;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder;
import com.android.systemui.keyguard.ui.view.KeyguardIndicationArea;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import dagger.Lazy;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardViewConfigurator implements CoreStartable {
    public final ChipbarCoordinator chipbarCoordinator;
    public final KeyguardClockInteractor clockInteractor;
    public final ConfigurationState configuration;
    public final Context context;
    public final DeviceEntryHapticsInteractor deviceEntryHapticsInteractor;
    public final Optional deviceEntryUnlockTrackerViewBinder;
    public final FalsingManager falsingManager;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardBlueprintViewModel keyguardBlueprintViewModel;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardIndicationController keyguardIndicationController;
    public final KeyguardRootView keyguardRootView;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final KeyguardViewMediator keyguardViewMediator;
    public final OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel;
    public DisposableHandles rootViewHandle;
    public final SceneKey sceneKey;
    public final ScreenOffAnimationController screenOffAnimationController;
    public final ShadeInteractor shadeInteractor;
    public final KeyguardSmartspaceViewModel smartspaceViewModel;
    public final VibratorHelper vibratorHelper;

    public KeyguardViewConfigurator(KeyguardRootView keyguardRootView, KeyguardRootViewModel keyguardRootViewModel, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, NotificationShadeWindowView notificationShadeWindowView, KeyguardIndicationController keyguardIndicationController, ScreenOffAnimationController screenOffAnimationController, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardStatusViewComponent.Factory factory, ConfigurationState configurationState, Context context, KeyguardIndicationController keyguardIndicationController2, Lazy lazy, ShadeInteractor shadeInteractor, InteractionJankMonitor interactionJankMonitor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, FalsingManager falsingManager, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LockscreenContentViewModel lockscreenContentViewModel, Lazy lazy2, KeyguardClockInteractor keyguardClockInteractor, KeyguardViewMediator keyguardViewMediator, Optional<Object> optional) {
        this.keyguardRootView = keyguardRootView;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.screenOffAnimationController = screenOffAnimationController;
        this.occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
        this.chipbarCoordinator = chipbarCoordinator;
        this.keyguardBlueprintViewModel = keyguardBlueprintViewModel;
        this.configuration = configurationState;
        this.context = context;
        this.keyguardIndicationController = keyguardIndicationController2;
        this.shadeInteractor = shadeInteractor;
        this.interactionJankMonitor = interactionJankMonitor;
        this.deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
        this.vibratorHelper = vibratorHelper;
        this.falsingManager = falsingManager;
        this.keyguardClockViewModel = keyguardClockViewModel;
        this.smartspaceViewModel = keyguardSmartspaceViewModel;
        this.clockInteractor = keyguardClockInteractor;
        this.keyguardViewMediator = keyguardViewMediator;
        this.deviceEntryUnlockTrackerViewBinder = optional;
        new SceneKey("root-view-scene-key", null, 2, null);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
        DisposableHandles disposableHandles = this.rootViewHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
        InteractionJankMonitor interactionJankMonitor = this.interactionJankMonitor;
        this.rootViewHandle = KeyguardRootViewBinder.bind(this.keyguardRootView, this.keyguardRootViewModel, this.keyguardBlueprintViewModel, this.configuration, this.occludingAppDeviceEntryMessageViewModel, this.chipbarCoordinator, this.screenOffAnimationController, this.shadeInteractor, this.clockInteractor, this.keyguardClockViewModel, interactionJankMonitor, this.deviceEntryHapticsInteractor, this.vibratorHelper, this.keyguardViewMediator);
        this.keyguardIndicationController.setIndicationArea(new KeyguardIndicationArea(this.context, null));
        Flags.deviceEntryUdfpsRefactor();
        Flags.sceneContainer();
        Flags.composeLockscreen();
        KeyguardBlueprintViewBinder.bind(this.keyguardBlueprintViewModel, this.keyguardRootView, this.keyguardClockViewModel, this.smartspaceViewModel);
        if (this.deviceEntryUnlockTrackerViewBinder.isPresent()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(this.deviceEntryUnlockTrackerViewBinder.get());
            throw null;
        }
    }
}
