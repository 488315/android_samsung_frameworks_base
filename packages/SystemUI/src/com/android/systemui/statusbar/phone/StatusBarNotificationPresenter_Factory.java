package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.InitController;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationAlertsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarNotificationPresenter_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider deviceUnlockedInteractorProvider;
    public final Provider dozeScrimControllerProvider;
    public final Provider dynamicPrivacyControllerProvider;
    public final Provider headsUpProvider;
    public final Provider initControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider notifShadeEventSourceProvider;
    public final Provider notificationAlertsInteractorProvider;
    public final Provider notificationGutsManagerProvider;
    public final Provider notificationListContainerProvider;
    public final Provider notificationMediaManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider panelExpansionInteractorProvider;
    public final Provider panelProvider;
    public final Provider powerInteractorProvider;
    public final Provider quickSettingsControllerProvider;
    public final Provider remoteInputManagerCallbackProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider shadeTransitionControllerProvider;
    public final Provider stackScrollerControllerProvider;
    public final Provider statusBarWindowProvider;
    public final Provider sysuiStatusBarStateControllerProvider;
    public final Provider visualInterruptionDecisionProvider;

    public StatusBarNotificationPresenter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27) {
        this.contextProvider = provider;
        this.panelProvider = provider2;
        this.panelExpansionInteractorProvider = provider3;
        this.quickSettingsControllerProvider = provider4;
        this.headsUpProvider = provider5;
        this.statusBarWindowProvider = provider6;
        this.activityStarterProvider = provider7;
        this.stackScrollerControllerProvider = provider8;
        this.dozeScrimControllerProvider = provider9;
        this.notificationShadeWindowControllerProvider = provider10;
        this.dynamicPrivacyControllerProvider = provider11;
        this.keyguardStateControllerProvider = provider12;
        this.notificationAlertsInteractorProvider = provider13;
        this.shadeTransitionControllerProvider = provider14;
        this.powerInteractorProvider = provider15;
        this.commandQueueProvider = provider16;
        this.lockscreenUserManagerProvider = provider17;
        this.sysuiStatusBarStateControllerProvider = provider18;
        this.notifShadeEventSourceProvider = provider19;
        this.notificationMediaManagerProvider = provider20;
        this.notificationGutsManagerProvider = provider21;
        this.initControllerProvider = provider22;
        this.visualInterruptionDecisionProvider = provider23;
        this.remoteInputManagerProvider = provider24;
        this.remoteInputManagerCallbackProvider = provider25;
        this.notificationListContainerProvider = provider26;
        this.deviceUnlockedInteractorProvider = provider27;
    }

    public static StatusBarNotificationPresenter newInstance(Context context, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, QuickSettingsController quickSettingsController, HeadsUpManager headsUpManager, NotificationShadeWindowView notificationShadeWindowView, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, NotificationAlertsInteractor notificationAlertsInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, PowerInteractor powerInteractor, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, InitController initController, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, NotificationRemoteInputManager.Callback callback, NotificationListContainer notificationListContainer, DeviceUnlockedInteractor deviceUnlockedInteractor) {
        return new StatusBarNotificationPresenter(context, shadeViewController, panelExpansionInteractor, quickSettingsController, headsUpManager, notificationShadeWindowView, activityStarter, notificationStackScrollLayoutController, dozeScrimController, notificationShadeWindowController, dynamicPrivacyController, keyguardStateController, notificationAlertsInteractor, lockscreenShadeTransitionController, powerInteractor, commandQueue, notificationLockscreenUserManager, sysuiStatusBarStateController, notifShadeEventSource, notificationMediaManager, notificationGutsManager, initController, visualInterruptionDecisionProvider, notificationRemoteInputManager, callback, notificationListContainer, deviceUnlockedInteractor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new StatusBarNotificationPresenter((Context) this.contextProvider.get(), (ShadeViewController) this.panelProvider.get(), (PanelExpansionInteractor) this.panelExpansionInteractorProvider.get(), (QuickSettingsController) this.quickSettingsControllerProvider.get(), (HeadsUpManager) this.headsUpProvider.get(), (NotificationShadeWindowView) this.statusBarWindowProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationStackScrollLayoutController) this.stackScrollerControllerProvider.get(), (DozeScrimController) this.dozeScrimControllerProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationAlertsInteractor) this.notificationAlertsInteractorProvider.get(), (LockscreenShadeTransitionController) this.shadeTransitionControllerProvider.get(), (PowerInteractor) this.powerInteractorProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SysuiStatusBarStateController) this.sysuiStatusBarStateControllerProvider.get(), (NotifShadeEventSource) this.notifShadeEventSourceProvider.get(), (NotificationMediaManager) this.notificationMediaManagerProvider.get(), (NotificationGutsManager) this.notificationGutsManagerProvider.get(), (InitController) this.initControllerProvider.get(), (VisualInterruptionDecisionProvider) this.visualInterruptionDecisionProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotificationRemoteInputManager.Callback) this.remoteInputManagerCallbackProvider.get(), (NotificationListContainer) this.notificationListContainerProvider.get(), (DeviceUnlockedInteractor) this.deviceUnlockedInteractorProvider.get());
    }
}
