package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationsModule_ProvideNotificationLoggerFactory implements Provider {
    public final Provider expansionStateLoggerProvider;
    public final Provider notifLiveDataStoreProvider;
    public final Provider notifPipelineProvider;
    public final Provider notificationListenerProvider;
    public final Provider notificationPanelLoggerProvider;
    public final Provider shadeExpansionStateManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider uiBgExecutorProvider;
    public final Provider visibilityProvider;

    public NotificationsModule_ProvideNotificationLoggerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.notificationListenerProvider = provider;
        this.uiBgExecutorProvider = provider2;
        this.notifLiveDataStoreProvider = provider3;
        this.visibilityProvider = provider4;
        this.notifPipelineProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.shadeExpansionStateManagerProvider = provider7;
        this.expansionStateLoggerProvider = provider8;
        this.notificationPanelLoggerProvider = provider9;
    }

    public static NotificationLogger provideNotificationLogger(NotificationListener notificationListener, Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, NotificationLogger.ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
        return new NotificationLogger(notificationListener, executor, notifLiveDataStore, notificationVisibilityProvider, notifPipeline, statusBarStateController, shadeExpansionStateManager, expansionStateLogger, notificationPanelLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNotificationLogger((NotificationListener) this.notificationListenerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (NotifLiveDataStore) this.notifLiveDataStoreProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (NotificationLogger.ExpansionStateLogger) this.expansionStateLoggerProvider.get(), (NotificationPanelLogger) this.notificationPanelLoggerProvider.get());
    }
}
