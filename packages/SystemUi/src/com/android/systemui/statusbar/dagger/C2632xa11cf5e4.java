package com.android.systemui.statusbar.dagger;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.ActionClickLogger;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideNotificationRemoteInputManagerFactory */
/* loaded from: classes2.dex */
public final class C2632xa11cf5e4 implements Provider {
    public final Provider actionClickLoggerProvider;
    public final Provider centralSurfacesOptionalLazyProvider;
    public final Provider clickNotifierProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider notifPipelineFlagsProvider;
    public final Provider remoteInputControllerLoggerProvider;
    public final Provider remoteInputUriControllerProvider;
    public final Provider smartReplyControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider visibilityProvider;

    public C2632xa11cf5e4(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.notifPipelineFlagsProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.smartReplyControllerProvider = provider4;
        this.visibilityProvider = provider5;
        this.centralSurfacesOptionalLazyProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.remoteInputUriControllerProvider = provider8;
        this.remoteInputControllerLoggerProvider = provider9;
        this.clickNotifierProvider = provider10;
        this.actionClickLoggerProvider = provider11;
        this.dumpManagerProvider = provider12;
    }

    public static NotificationRemoteInputManager provideNotificationRemoteInputManager(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, Lazy lazy, StatusBarStateController statusBarStateController, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger, NotificationClickNotifier notificationClickNotifier, ActionClickLogger actionClickLogger, DumpManager dumpManager) {
        return new NotificationRemoteInputManager(context, notifPipelineFlags, notificationLockscreenUserManager, smartReplyController, notificationVisibilityProvider, lazy, statusBarStateController, remoteInputUriController, remoteInputControllerLogger, notificationClickNotifier, actionClickLogger, dumpManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNotificationRemoteInputManager((Context) this.contextProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SmartReplyController) this.smartReplyControllerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (RemoteInputUriController) this.remoteInputUriControllerProvider.get(), (RemoteInputControllerLogger) this.remoteInputControllerLoggerProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get(), (ActionClickLogger) this.actionClickLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
