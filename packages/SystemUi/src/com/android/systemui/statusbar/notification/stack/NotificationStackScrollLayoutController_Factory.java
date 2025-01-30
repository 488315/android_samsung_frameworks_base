package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.controls.p010ui.KeyguardMediaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationStackScrollLayoutController_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider allowLongPressProvider;
    public final Provider centralSurfacesProvider;
    public final Provider configurationControllerProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dismissibilityProvider;
    public final Provider dumpManagerProvider;
    public final Provider dynamicPrivacyControllerProvider;
    public final Provider falsingCollectorProvider;
    public final Provider falsingManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider groupManagerProvider;
    public final Provider headsUpManagerProvider;
    public final Provider jankMonitorProvider;
    public final Provider keyguardBypassControllerProvider;
    public final Provider keyguardMediaControllerProvider;
    public final Provider lockscreenNotificationManagerProvider;
    public final Provider lockscreenShadeTransitionControllerProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider loggerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider notifCollectionProvider;
    public final Provider notifIconAreaControllerProvider;
    public final Provider notifPipelineFlagsProvider;
    public final Provider notifPipelineProvider;
    public final Provider notificationGutsManagerProvider;
    public final Provider notificationRoundnessManagerProvider;
    public final Provider notificationShelfManagerProvider;
    public final Provider notificationStackSizeCalculatorProvider;
    public final Provider notificationSwipeHelperBuilderProvider;
    public final Provider notificationTargetsHelperProvider;
    public final Provider nsslViewModelProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider resourcesProvider;
    public final Provider scrimControllerProvider;
    public final Provider secureSettingsProvider;
    public final Provider seenNotificationsProvider;
    public final Provider shadeControllerProvider;
    public final Provider silentHeaderControllerProvider;
    public final Provider stackLoggerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider tunerServiceProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider viewProvider;
    public final Provider visibilityLocationProviderDelegatorProvider;
    public final Provider visibilityProvider;
    public final Provider zenModeControllerProvider;

    public NotificationStackScrollLayoutController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42, Provider provider43, Provider provider44, Provider provider45, Provider provider46, Provider provider47) {
        this.viewProvider = provider;
        this.allowLongPressProvider = provider2;
        this.notificationGutsManagerProvider = provider3;
        this.visibilityProvider = provider4;
        this.headsUpManagerProvider = provider5;
        this.notificationRoundnessManagerProvider = provider6;
        this.tunerServiceProvider = provider7;
        this.deviceProvisionedControllerProvider = provider8;
        this.dynamicPrivacyControllerProvider = provider9;
        this.configurationControllerProvider = provider10;
        this.statusBarStateControllerProvider = provider11;
        this.keyguardMediaControllerProvider = provider12;
        this.keyguardBypassControllerProvider = provider13;
        this.zenModeControllerProvider = provider14;
        this.lockscreenUserManagerProvider = provider15;
        this.nsslViewModelProvider = provider16;
        this.metricsLoggerProvider = provider17;
        this.dumpManagerProvider = provider18;
        this.falsingCollectorProvider = provider19;
        this.falsingManagerProvider = provider20;
        this.resourcesProvider = provider21;
        this.notificationSwipeHelperBuilderProvider = provider22;
        this.centralSurfacesProvider = provider23;
        this.scrimControllerProvider = provider24;
        this.groupManagerProvider = provider25;
        this.silentHeaderControllerProvider = provider26;
        this.notifPipelineProvider = provider27;
        this.notifPipelineFlagsProvider = provider28;
        this.notifCollectionProvider = provider29;
        this.lockscreenShadeTransitionControllerProvider = provider30;
        this.uiEventLoggerProvider = provider31;
        this.remoteInputManagerProvider = provider32;
        this.visibilityLocationProviderDelegatorProvider = provider33;
        this.seenNotificationsProvider = provider34;
        this.shadeControllerProvider = provider35;
        this.jankMonitorProvider = provider36;
        this.stackLoggerProvider = provider37;
        this.loggerProvider = provider38;
        this.notificationStackSizeCalculatorProvider = provider39;
        this.notifIconAreaControllerProvider = provider40;
        this.featureFlagsProvider = provider41;
        this.notificationTargetsHelperProvider = provider42;
        this.secureSettingsProvider = provider43;
        this.dismissibilityProvider = provider44;
        this.activityStarterProvider = provider45;
        this.notificationShelfManagerProvider = provider46;
        this.lockscreenNotificationManagerProvider = provider47;
    }

    public static NotificationStackScrollLayoutController newInstance(NotificationStackScrollLayout notificationStackScrollLayout, boolean z, NotificationGutsManager notificationGutsManager, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, Optional optional, MetricsLogger metricsLogger, DumpManager dumpManager, FalsingCollector falsingCollector, FalsingManager falsingManager, Resources resources, Object obj, CentralSurfaces centralSurfaces, ScrimController scrimController, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipeline notifPipeline, NotifPipelineFlags notifPipelineFlags, NotifCollection notifCollection, LockscreenShadeTransitionController lockscreenShadeTransitionController, UiEventLogger uiEventLogger, NotificationRemoteInputManager notificationRemoteInputManager, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, SeenNotificationsProvider seenNotificationsProvider, ShadeController shadeController, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationIconAreaController notificationIconAreaController, FeatureFlags featureFlags, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, NotificationShelfManager notificationShelfManager, LockscreenNotificationManager lockscreenNotificationManager) {
        return new NotificationStackScrollLayoutController(notificationStackScrollLayout, z, notificationGutsManager, notificationVisibilityProvider, headsUpManagerPhone, notificationRoundnessManager, tunerService, deviceProvisionedController, dynamicPrivacyController, configurationController, sysuiStatusBarStateController, keyguardMediaController, keyguardBypassController, zenModeController, notificationLockscreenUserManager, optional, metricsLogger, dumpManager, falsingCollector, falsingManager, resources, (NotificationSwipeHelper.Builder) obj, centralSurfaces, scrimController, groupExpansionManager, sectionHeaderController, notifPipeline, notifPipelineFlags, notifCollection, lockscreenShadeTransitionController, uiEventLogger, notificationRemoteInputManager, visibilityLocationProviderDelegator, seenNotificationsProvider, shadeController, interactionJankMonitor, stackStateLogger, notificationStackScrollLogger, notificationStackSizeCalculator, notificationIconAreaController, featureFlags, notificationTargetsHelper, secureSettings, notificationDismissibilityProvider, activityStarter, notificationShelfManager, lockscreenNotificationManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((NotificationStackScrollLayout) this.viewProvider.get(), ((Boolean) this.allowLongPressProvider.get()).booleanValue(), (NotificationGutsManager) this.notificationGutsManagerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (HeadsUpManagerPhone) this.headsUpManagerProvider.get(), (NotificationRoundnessManager) this.notificationRoundnessManagerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardMediaController) this.keyguardMediaControllerProvider.get(), (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (ZenModeController) this.zenModeControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (Optional) this.nsslViewModelProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (Resources) this.resourcesProvider.get(), this.notificationSwipeHelperBuilderProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (ScrimController) this.scrimControllerProvider.get(), (GroupExpansionManager) this.groupManagerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (LockscreenShadeTransitionController) this.lockscreenShadeTransitionControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (VisibilityLocationProviderDelegator) this.visibilityLocationProviderDelegatorProvider.get(), (SeenNotificationsProvider) this.seenNotificationsProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (InteractionJankMonitor) this.jankMonitorProvider.get(), (StackStateLogger) this.stackLoggerProvider.get(), (NotificationStackScrollLogger) this.loggerProvider.get(), (NotificationStackSizeCalculator) this.notificationStackSizeCalculatorProvider.get(), (NotificationIconAreaController) this.notifIconAreaControllerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (NotificationTargetsHelper) this.notificationTargetsHelperProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (NotificationDismissibilityProvider) this.dismissibilityProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationShelfManager) this.notificationShelfManagerProvider.get(), (LockscreenNotificationManager) this.lockscreenNotificationManagerProvider.get());
    }
}
