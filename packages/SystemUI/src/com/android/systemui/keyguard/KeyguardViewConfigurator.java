package com.android.systemui.keyguard;

import android.content.Context;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardJankBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder;
import com.android.systemui.keyguard.ui.view.KeyguardIndicationArea;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardJankViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperFocalAreaViewModel;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperViewModel;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.Optional;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardViewConfigurator implements CoreStartable {
    public final LogBuffer blueprintLog;
    public final ChipbarCoordinator chipbarCoordinator;
    public final KeyguardClockInteractor clockInteractor;
    public final ConfigurationState configuration;
    public final Context context;
    public final DeviceEntryHapticsInteractor deviceEntryHapticsInteractor;
    public final Optional deviceEntryUnlockTrackerViewBinder;
    public final FalsingManager falsingManager;
    public final InteractionJankMonitor interactionJankMonitor;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 jankHandle;
    public final KeyguardBlueprintViewModel keyguardBlueprintViewModel;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardIndicationController keyguardIndicationController;
    public final KeyguardJankViewModel keyguardJankViewModel;
    public final KeyguardRootView keyguardRootView;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final KeyguardViewMediator keyguardViewMediator;
    public final CoroutineDispatcher mainDispatcher;
    public final MSDLPlayer msdlPlayer;
    public final OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel;
    public DisposableHandles rootViewHandle;
    public final ShadeInteractor shadeInteractor;
    public final KeyguardSmartspaceViewModel smartspaceViewModel;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final VibratorHelper vibratorHelper;
    public final WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel;

    public KeyguardViewConfigurator(KeyguardRootView keyguardRootView, KeyguardRootViewModel keyguardRootViewModel, KeyguardJankViewModel keyguardJankViewModel, ScreenOffAnimationController screenOffAnimationController, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, Context context, KeyguardIndicationController keyguardIndicationController, ShadeInteractor shadeInteractor, InteractionJankMonitor interactionJankMonitor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, FalsingManager falsingManager, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockInteractor keyguardClockInteractor, WallpaperFocalAreaInteractor wallpaperFocalAreaInteractor, KeyguardViewMediator keyguardViewMediator, Optional<Object> optional, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LightRevealScrimViewModel lightRevealScrimViewModel, LightRevealScrim lightRevealScrim, WallpaperViewModel wallpaperViewModel, CoroutineDispatcher coroutineDispatcher, MSDLPlayer mSDLPlayer, LogBuffer logBuffer, WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel) {
        this.keyguardRootView = keyguardRootView;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.keyguardJankViewModel = keyguardJankViewModel;
        this.occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
        this.chipbarCoordinator = chipbarCoordinator;
        this.keyguardBlueprintViewModel = keyguardBlueprintViewModel;
        this.configuration = configurationState;
        this.context = context;
        this.keyguardIndicationController = keyguardIndicationController;
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
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mainDispatcher = coroutineDispatcher;
        this.msdlPlayer = mSDLPlayer;
        this.blueprintLog = logBuffer;
        this.wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        DisposableHandles disposableHandles = this.rootViewHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
        this.rootViewHandle = KeyguardRootViewBinder.bind(this.keyguardRootView, this.keyguardRootViewModel, this.keyguardBlueprintViewModel, this.configuration, this.occludingAppDeviceEntryMessageViewModel, this.chipbarCoordinator, this.shadeInteractor, this.smartspaceViewModel, this.deviceEntryHapticsInteractor, this.vibratorHelper, this.falsingManager, this.mainDispatcher, this.msdlPlayer, this.blueprintLog, this.wallpaperFocalAreaViewModel);
        RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttachedKt$repeatWhenAttached$1 = this.jankHandle;
        if (repeatWhenAttachedKt$repeatWhenAttached$1 != null) {
            repeatWhenAttachedKt$repeatWhenAttached$1.dispose();
        }
        this.jankHandle = KeyguardJankBinder.bind(this.keyguardRootView, this.keyguardJankViewModel, this.interactionJankMonitor, this.clockInteractor, this.keyguardViewMediator, this.mainDispatcher);
        this.keyguardIndicationController.setIndicationArea(new KeyguardIndicationArea(this.context, null));
        KeyguardBlueprintViewBinder.bind(this.keyguardRootView, this.keyguardBlueprintViewModel, this.keyguardClockViewModel, this.smartspaceViewModel, this.blueprintLog);
        if (this.deviceEntryUnlockTrackerViewBinder.isPresent()) {
            this.deviceEntryUnlockTrackerViewBinder.get().getClass();
            throw new ClassCastException();
        }
    }
}
