package com.android.systemui.doze;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.FaceWidgetWallpaperUtilsWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.DisplayLifecycle;
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
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PluginAODManager_Factory implements Provider {
    public final Provider aodAmbientWallpaperHelperProvider;
    public final Provider aodLoggerProvider;
    public final Provider aodTouchModeManagerProvider;
    public final Provider commonNotifCollectionLazyProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider coverScreenManagerLazyProvider;
    public final Provider displayLifecycleProvider;
    public final Provider dozeParametersProvider;
    public final Provider dozeServiceHostProvider;
    public final Provider dumpManagerProvider;
    public final Provider faceWidgetWallpaperUtilsWrapperProvider;
    public final Provider foldControllerProvider;
    public final Provider keyguardNotificationVisibilityProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider keyguardViewControllerProvider;
    public final Provider keyguardViewMediatorHelperProvider;
    public final Provider keyguardWallpaperControllerProvider;
    public final Provider keyguardWallpaperProvider;
    public final Provider lockscreenNotificationIconsOnlyControllerProvider;
    public final Provider lockscreenNotificationManagerProvider;
    public final Provider mEmmProvider;
    public final Provider notificationLockscreenUserManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider notificationsControllerProvider;
    public final Provider pluginFaceWidgetManagerProvider;
    public final Provider pluginLockMediatorProvider;
    public final Provider pluginLockStarManagerProvider;
    public final Provider settingsHelperProvider;
    public final Provider subScreenManagerProvider;
    public final Provider subScreenQuickPanelWindowControllerProvider;
    public final Provider wakefulnessLifecycleProvider;

    public PluginAODManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32) {
        this.contextProvider = provider;
        this.lockscreenNotificationManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.keyguardViewControllerProvider = provider4;
        this.pluginFaceWidgetManagerProvider = provider5;
        this.settingsHelperProvider = provider6;
        this.dozeParametersProvider = provider7;
        this.pluginLockMediatorProvider = provider8;
        this.notificationShadeWindowControllerProvider = provider9;
        this.keyguardWallpaperProvider = provider10;
        this.dozeServiceHostProvider = provider11;
        this.subScreenManagerProvider = provider12;
        this.coverScreenManagerLazyProvider = provider13;
        this.faceWidgetWallpaperUtilsWrapperProvider = provider14;
        this.displayLifecycleProvider = provider15;
        this.wakefulnessLifecycleProvider = provider16;
        this.commonNotifCollectionLazyProvider = provider17;
        this.notificationLockscreenUserManagerProvider = provider18;
        this.aodLoggerProvider = provider19;
        this.foldControllerProvider = provider20;
        this.dumpManagerProvider = provider21;
        this.pluginLockStarManagerProvider = provider22;
        this.notificationsControllerProvider = provider23;
        this.keyguardNotificationVisibilityProvider = provider24;
        this.lockscreenNotificationIconsOnlyControllerProvider = provider25;
        this.subScreenQuickPanelWindowControllerProvider = provider26;
        this.keyguardViewMediatorHelperProvider = provider27;
        this.aodAmbientWallpaperHelperProvider = provider28;
        this.configurationControllerProvider = provider29;
        this.aodTouchModeManagerProvider = provider30;
        this.keyguardWallpaperControllerProvider = provider31;
        this.mEmmProvider = provider32;
    }

    public static PluginAODManager newInstance(Context context, LockscreenNotificationManager lockscreenNotificationManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, PluginFaceWidgetManager pluginFaceWidgetManager, SettingsHelper settingsHelper, DozeParameters dozeParameters, PluginLockMediator pluginLockMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardWallpaper keyguardWallpaper, DozeServiceHost dozeServiceHost, SubScreenManager subScreenManager, Lazy lazy, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, NotificationLockscreenUserManager notificationLockscreenUserManager, SamsungServiceLogger samsungServiceLogger, KeyguardFoldController keyguardFoldController, DumpManager dumpManager, PluginLockStarManager pluginLockStarManager, NotificationsController notificationsController, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, KeyguardViewMediatorHelper keyguardViewMediatorHelper, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, ConfigurationController configurationController, AODTouchModeManager aODTouchModeManager, KeyguardWallpaperController keyguardWallpaperController) {
        return new PluginAODManager(context, lockscreenNotificationManager, keyguardUpdateMonitor, keyguardViewController, pluginFaceWidgetManager, settingsHelper, dozeParameters, pluginLockMediator, notificationShadeWindowController, keyguardWallpaper, dozeServiceHost, subScreenManager, lazy, faceWidgetWallpaperUtilsWrapper, displayLifecycle, wakefulnessLifecycle, lazy2, notificationLockscreenUserManager, samsungServiceLogger, keyguardFoldController, dumpManager, pluginLockStarManager, notificationsController, keyguardNotificationVisibilityProvider, lockscreenNotificationIconsOnlyController, subScreenQuickPanelWindowController, keyguardViewMediatorHelper, aODAmbientWallpaperHelper, configurationController, aODTouchModeManager, keyguardWallpaperController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        PluginAODManager newInstance = newInstance((Context) this.contextProvider.get(), (LockscreenNotificationManager) this.lockscreenNotificationManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (PluginFaceWidgetManager) this.pluginFaceWidgetManagerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (KeyguardWallpaper) this.keyguardWallpaperProvider.get(), (DozeServiceHost) this.dozeServiceHostProvider.get(), (SubScreenManager) this.subScreenManagerProvider.get(), DoubleCheck.lazy(this.coverScreenManagerLazyProvider), (FaceWidgetWallpaperUtilsWrapper) this.faceWidgetWallpaperUtilsWrapperProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (NotificationLockscreenUserManager) this.notificationLockscreenUserManagerProvider.get(), (SamsungServiceLogger) this.aodLoggerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (NotificationsController) this.notificationsControllerProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (LockscreenNotificationIconsOnlyController) this.lockscreenNotificationIconsOnlyControllerProvider.get(), (SubScreenQuickPanelWindowController) this.subScreenQuickPanelWindowControllerProvider.get(), (KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperProvider.get(), (AODAmbientWallpaperHelper) this.aodAmbientWallpaperHelperProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (AODTouchModeManager) this.aodTouchModeManagerProvider.get(), (KeyguardWallpaperController) this.keyguardWallpaperControllerProvider.get());
        newInstance.mEmm = (EngineeringModeManagerWrapper) this.mEmmProvider.get();
        return newInstance;
    }
}
