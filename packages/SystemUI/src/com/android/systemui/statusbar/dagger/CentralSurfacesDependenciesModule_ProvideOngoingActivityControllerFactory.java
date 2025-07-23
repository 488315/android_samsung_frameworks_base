package com.android.systemui.statusbar.dagger;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CentralSurfacesDependenciesModule_ProvideOngoingActivityControllerFactory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider commonNotifCollectionProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider faceWidgetNotificationControllerWrapperProvider;
    public final Provider headsUpManagerProvider;
    public final Provider indicatorGardenPresenterProvider;
    public final Provider indicatorScaleGardenerProvider;
    public final Provider mediaDataManagerProvider;
    public final Provider mediaHostProvider;
    public final Provider notifCollectionProvider;
    public final Provider notificationIconAreaControllerProvider;
    public final Provider ongoingCallControllerProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider screenStatusProvider;
    public final Provider settingsHelperProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowStateControllerProvider;
    public final Provider taskStackChangeListenersProvider;
    public final Provider userManagerProvider;

    public CentralSurfacesDependenciesModule_ProvideOngoingActivityControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22) {
        this.contextProvider = provider;
        this.commonNotifCollectionProvider = provider2;
        this.activityStarterProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.indicatorGardenPresenterProvider = provider5;
        this.headsUpManagerProvider = provider6;
        this.ongoingCallControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.broadcastDispatcherProvider = provider9;
        this.taskStackChangeListenersProvider = provider10;
        this.indicatorScaleGardenerProvider = provider11;
        this.notifCollectionProvider = provider12;
        this.userManagerProvider = provider13;
        this.remoteInputManagerProvider = provider14;
        this.statusBarWindowStateControllerProvider = provider15;
        this.dumpManagerProvider = provider16;
        this.mediaHostProvider = provider17;
        this.settingsHelperProvider = provider18;
        this.notificationIconAreaControllerProvider = provider19;
        this.mediaDataManagerProvider = provider20;
        this.screenStatusProvider = provider21;
        this.faceWidgetNotificationControllerWrapperProvider = provider22;
    }

    public static OngoingActivityController provideOngoingActivityController(Context context, CommonNotifCollection commonNotifCollection, ActivityStarter activityStarter, StatusBarStateController statusBarStateController, IndicatorGardenPresenter indicatorGardenPresenter, HeadsUpManager headsUpManager, OngoingCallController ongoingCallController, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, TaskStackChangeListeners taskStackChangeListeners, IndicatorScaleGardener indicatorScaleGardener, NotifCollection notifCollection, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarWindowStateController statusBarWindowStateController, DumpManager dumpManager, SecMediaHost secMediaHost, SettingsHelper settingsHelper, NotificationIconAreaController notificationIconAreaController, MediaDataManager mediaDataManager, LifecycleScreenStatusProvider lifecycleScreenStatusProvider, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        return new OngoingActivityController(context, commonNotifCollection, activityStarter, statusBarStateController, indicatorGardenPresenter, headsUpManager, ongoingCallController, configurationController, broadcastDispatcher, taskStackChangeListeners, indicatorScaleGardener, notifCollection, notificationLockscreenUserManager, notificationRemoteInputManager, statusBarWindowStateController, dumpManager, secMediaHost, settingsHelper, notificationIconAreaController, mediaDataManager, lifecycleScreenStatusProvider, faceWidgetNotificationControllerWrapper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new OngoingActivityController((Context) this.contextProvider.get(), (CommonNotifCollection) this.commonNotifCollectionProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (IndicatorGardenPresenter) this.indicatorGardenPresenterProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (OngoingCallController) this.ongoingCallControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (TaskStackChangeListeners) this.taskStackChangeListenersProvider.get(), (IndicatorScaleGardener) this.indicatorScaleGardenerProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (NotificationLockscreenUserManager) this.userManagerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (StatusBarWindowStateController) this.statusBarWindowStateControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (SecMediaHost) this.mediaHostProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (MediaDataManager) this.mediaDataManagerProvider.get(), (ScreenStatusProvider) this.screenStatusProvider.get(), (FaceWidgetNotificationControllerWrapper) this.faceWidgetNotificationControllerWrapperProvider.get());
    }
}
