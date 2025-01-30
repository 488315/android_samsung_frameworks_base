package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.InitController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarNotificationPresenter_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider centralSurfacesProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider dozeScrimControllerProvider;
    public final Provider dynamicPrivacyControllerProvider;
    public final Provider headsUpProvider;
    public final Provider initControllerProvider;
    public final Provider keyguardIndicationControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider lockscreenGestureLoggerProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider notifPipelineFlagsProvider;
    public final Provider notifShadeEventSourceProvider;
    public final Provider notificationGutsManagerProvider;
    public final Provider notificationInterruptStateProvider;
    public final Provider notificationListContainerProvider;
    public final Provider notificationMediaManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider panelProvider;
    public final Provider quickSettingsControllerProvider;
    public final Provider remoteInputManagerCallbackProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider shadeTransitionControllerProvider;
    public final Provider stackScrollerControllerProvider;
    public final Provider statusBarWindowProvider;
    public final Provider sysuiStatusBarStateControllerProvider;

    public StatusBarNotificationPresenter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27) {
        this.contextProvider = provider;
        this.panelProvider = provider2;
        this.quickSettingsControllerProvider = provider3;
        this.headsUpProvider = provider4;
        this.statusBarWindowProvider = provider5;
        this.activityStarterProvider = provider6;
        this.stackScrollerControllerProvider = provider7;
        this.dozeScrimControllerProvider = provider8;
        this.notificationShadeWindowControllerProvider = provider9;
        this.dynamicPrivacyControllerProvider = provider10;
        this.keyguardStateControllerProvider = provider11;
        this.keyguardIndicationControllerProvider = provider12;
        this.centralSurfacesProvider = provider13;
        this.shadeTransitionControllerProvider = provider14;
        this.commandQueueProvider = provider15;
        this.lockscreenUserManagerProvider = provider16;
        this.sysuiStatusBarStateControllerProvider = provider17;
        this.notifShadeEventSourceProvider = provider18;
        this.notificationMediaManagerProvider = provider19;
        this.notificationGutsManagerProvider = provider20;
        this.lockscreenGestureLoggerProvider = provider21;
        this.initControllerProvider = provider22;
        this.notificationInterruptStateProvider = provider23;
        this.remoteInputManagerProvider = provider24;
        this.notifPipelineFlagsProvider = provider25;
        this.remoteInputManagerCallbackProvider = provider26;
        this.notificationListContainerProvider = provider27;
    }

    public static StatusBarNotificationPresenter newInstance(Context context, ShadeViewController shadeViewController, QuickSettingsController quickSettingsController, HeadsUpManagerPhone headsUpManagerPhone, NotificationShadeWindowView notificationShadeWindowView, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, KeyguardIndicationController keyguardIndicationController, CentralSurfaces centralSurfaces, LockscreenShadeTransitionController lockscreenShadeTransitionController, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, LockscreenGestureLogger lockscreenGestureLogger, InitController initController, NotificationInterruptStateProvider notificationInterruptStateProvider, NotificationRemoteInputManager notificationRemoteInputManager, NotifPipelineFlags notifPipelineFlags, NotificationRemoteInputManager.Callback callback, NotificationListContainer notificationListContainer) {
        return new StatusBarNotificationPresenter(context, shadeViewController, quickSettingsController, headsUpManagerPhone, notificationShadeWindowView, activityStarter, notificationStackScrollLayoutController, dozeScrimController, notificationShadeWindowController, dynamicPrivacyController, keyguardStateController, keyguardIndicationController, centralSurfaces, lockscreenShadeTransitionController, commandQueue, notificationLockscreenUserManager, sysuiStatusBarStateController, notifShadeEventSource, notificationMediaManager, notificationGutsManager, lockscreenGestureLogger, initController, notificationInterruptStateProvider, notificationRemoteInputManager, notifPipelineFlags, callback, notificationListContainer);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Context) this.contextProvider.get(), (ShadeViewController) this.panelProvider.get(), (QuickSettingsController) this.quickSettingsControllerProvider.get(), (HeadsUpManagerPhone) this.headsUpProvider.get(), (NotificationShadeWindowView) this.statusBarWindowProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationStackScrollLayoutController) this.stackScrollerControllerProvider.get(), (DozeScrimController) this.dozeScrimControllerProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardIndicationController) this.keyguardIndicationControllerProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (LockscreenShadeTransitionController) this.shadeTransitionControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SysuiStatusBarStateController) this.sysuiStatusBarStateControllerProvider.get(), (NotifShadeEventSource) this.notifShadeEventSourceProvider.get(), (NotificationMediaManager) this.notificationMediaManagerProvider.get(), (NotificationGutsManager) this.notificationGutsManagerProvider.get(), (LockscreenGestureLogger) this.lockscreenGestureLoggerProvider.get(), (InitController) this.initControllerProvider.get(), (NotificationInterruptStateProvider) this.notificationInterruptStateProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (NotificationRemoteInputManager.Callback) this.remoteInputManagerCallbackProvider.get(), (NotificationListContainer) this.notificationListContainerProvider.get());
    }
}
