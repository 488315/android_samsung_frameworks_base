package com.android.systemui.doze;

import android.content.Context;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.FaceWidgetWallpaperUtilsWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class PluginAODManager_Factory implements Provider {
    public final Provider activeNotificationsInteractorProvider;
    public final Provider aodAmbientWallpaperHelperProvider;
    public final Provider aodLoggerProvider;
    public final Provider aodTouchModeManagerProvider;
    public final Provider commonNotifCollectionLazyProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider coverScreenManagerLazyProvider;
    public final Provider displayLifecycleProvider;
    public final Provider dozeParametersProvider;
    public final Provider dozeServiceHostLazyProvider;
    public final Provider dumpManagerProvider;
    public final Provider faceWidgetWallpaperUtilsWrapperProvider;
    public final Provider foldControllerProvider;
    public final Provider keyguardDisplayManagerProvider;
    public final Provider keyguardNotificationVisibilityProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider keyguardViewControllerProvider;
    public final Provider keyguardViewMediatorHelperProvider;
    public final Provider keyguardWallpaperControllerProvider;
    public final Provider keyguardWallpaperProvider;
    public final Provider lockscreenNotificationIconsOnlyControllerProvider;
    public final Provider lockscreenNotificationManagerProvider;
    public final Provider mBatteryMeterViewControllerFactoryProvider;
    public final Provider mEmmProvider;
    public final Provider mKeyguardFastBioUnlockControllerProvider;
    public final Provider mPanelViewControllerLazyProvider;
    public final Provider mScrimControllerProvider;
    public final Provider notificationLockscreenUserManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider notificationsControllerProvider;
    public final Provider pluginFaceWidgetManagerProvider;
    public final Provider pluginLockMediatorProvider;
    public final Provider pluginLockStarManagerProvider;
    public final Provider selectedUserInteractorProvider;
    public final Provider settingsHelperProvider;
    public final Provider subScreenManagerProvider;
    public final Provider subScreenQuickPanelWindowControllerProvider;
    public final Provider wakefulnessLifecycleProvider;

    public PluginAODManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39) {
        this.contextProvider = provider;
        this.selectedUserInteractorProvider = provider2;
        this.lockscreenNotificationManagerProvider = provider3;
        this.keyguardUpdateMonitorProvider = provider4;
        this.keyguardViewControllerProvider = provider5;
        this.pluginFaceWidgetManagerProvider = provider6;
        this.settingsHelperProvider = provider7;
        this.dozeParametersProvider = provider8;
        this.pluginLockMediatorProvider = provider9;
        this.notificationShadeWindowControllerProvider = provider10;
        this.keyguardWallpaperProvider = provider11;
        this.dozeServiceHostLazyProvider = provider12;
        this.subScreenManagerProvider = provider13;
        this.coverScreenManagerLazyProvider = provider14;
        this.faceWidgetWallpaperUtilsWrapperProvider = provider15;
        this.displayLifecycleProvider = provider16;
        this.wakefulnessLifecycleProvider = provider17;
        this.commonNotifCollectionLazyProvider = provider18;
        this.notificationLockscreenUserManagerProvider = provider19;
        this.aodLoggerProvider = provider20;
        this.foldControllerProvider = provider21;
        this.dumpManagerProvider = provider22;
        this.pluginLockStarManagerProvider = provider23;
        this.notificationsControllerProvider = provider24;
        this.activeNotificationsInteractorProvider = provider25;
        this.keyguardNotificationVisibilityProvider = provider26;
        this.lockscreenNotificationIconsOnlyControllerProvider = provider27;
        this.subScreenQuickPanelWindowControllerProvider = provider28;
        this.keyguardViewMediatorHelperProvider = provider29;
        this.aodAmbientWallpaperHelperProvider = provider30;
        this.configurationControllerProvider = provider31;
        this.aodTouchModeManagerProvider = provider32;
        this.keyguardWallpaperControllerProvider = provider33;
        this.keyguardDisplayManagerProvider = provider34;
        this.mEmmProvider = provider35;
        this.mPanelViewControllerLazyProvider = provider36;
        this.mBatteryMeterViewControllerFactoryProvider = provider37;
        this.mScrimControllerProvider = provider38;
        this.mKeyguardFastBioUnlockControllerProvider = provider39;
    }

    public static PluginAODManager newInstance(Context context, SelectedUserInteractor selectedUserInteractor, LockscreenNotificationManager lockscreenNotificationManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, PluginFaceWidgetManager pluginFaceWidgetManager, SettingsHelper settingsHelper, DozeParameters dozeParameters, PluginLockMediator pluginLockMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardWallpaper keyguardWallpaper, Lazy lazy, SubScreenManager subScreenManager, Lazy lazy2, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy3, NotificationLockscreenUserManager notificationLockscreenUserManager, SamsungServiceLogger samsungServiceLogger, KeyguardFoldController keyguardFoldController, DumpManager dumpManager, PluginLockStarManager pluginLockStarManager, NotificationsController notificationsController, ActiveNotificationsInteractor activeNotificationsInteractor, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, KeyguardViewMediatorHelper keyguardViewMediatorHelper, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, ConfigurationController configurationController, AODTouchModeManager aODTouchModeManager, KeyguardWallpaperController keyguardWallpaperController, KeyguardDisplayManager keyguardDisplayManager) {
        return new PluginAODManager(context, selectedUserInteractor, lockscreenNotificationManager, keyguardUpdateMonitor, keyguardViewController, pluginFaceWidgetManager, settingsHelper, dozeParameters, pluginLockMediator, notificationShadeWindowController, keyguardWallpaper, lazy, subScreenManager, lazy2, faceWidgetWallpaperUtilsWrapper, displayLifecycle, wakefulnessLifecycle, lazy3, notificationLockscreenUserManager, samsungServiceLogger, keyguardFoldController, dumpManager, pluginLockStarManager, notificationsController, activeNotificationsInteractor, keyguardNotificationVisibilityProvider, lockscreenNotificationIconsOnlyController, subScreenQuickPanelWindowController, keyguardViewMediatorHelper, aODAmbientWallpaperHelper, configurationController, aODTouchModeManager, keyguardWallpaperController, keyguardDisplayManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        PluginAODManager pluginAODManager = new PluginAODManager((Context) this.contextProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (LockscreenNotificationManager) this.lockscreenNotificationManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (PluginFaceWidgetManager) this.pluginFaceWidgetManagerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (KeyguardWallpaper) this.keyguardWallpaperProvider.get(), DoubleCheck.lazy(this.dozeServiceHostLazyProvider), (SubScreenManager) this.subScreenManagerProvider.get(), DoubleCheck.lazy(this.coverScreenManagerLazyProvider), (FaceWidgetWallpaperUtilsWrapper) this.faceWidgetWallpaperUtilsWrapperProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (NotificationLockscreenUserManager) this.notificationLockscreenUserManagerProvider.get(), (SamsungServiceLogger) this.aodLoggerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (NotificationsController) this.notificationsControllerProvider.get(), (ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (LockscreenNotificationIconsOnlyController) this.lockscreenNotificationIconsOnlyControllerProvider.get(), (SubScreenQuickPanelWindowController) this.subScreenQuickPanelWindowControllerProvider.get(), (KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperProvider.get(), (AODAmbientWallpaperHelper) this.aodAmbientWallpaperHelperProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (AODTouchModeManager) this.aodTouchModeManagerProvider.get(), (KeyguardWallpaperController) this.keyguardWallpaperControllerProvider.get(), (KeyguardDisplayManager) this.keyguardDisplayManagerProvider.get());
        pluginAODManager.mEmm = (EngineeringModeManagerWrapper) this.mEmmProvider.get();
        pluginAODManager.mPanelViewControllerLazy = DoubleCheck.lazy(this.mPanelViewControllerLazyProvider);
        pluginAODManager.mBatteryMeterViewControllerFactory = (BatteryMeterViewController.Factory) this.mBatteryMeterViewControllerFactoryProvider.get();
        pluginAODManager.mScrimController = (ScrimController) this.mScrimControllerProvider.get();
        pluginAODManager.mKeyguardFastBioUnlockController = (KeyguardFastBioUnlockController) this.mKeyguardFastBioUnlockControllerProvider.get();
        return pluginAODManager;
    }
}
