package com.android.systemui.dagger;

import android.app.INotificationManager;
import android.content.Context;
import android.service.dreams.IDreamManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.wmshell.BubblesManager;
import dagger.internal.Preconditions;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIModule_ProvideBubblesManagerFactory implements Provider {
    public final Provider broadcastDispatcherProvider;
    public final Provider bubblesOptionalProvider;
    public final Provider contextProvider;
    public final Provider dreamManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider notifCollectionProvider;
    public final Provider notifPipelineFlagsProvider;
    public final Provider notifPipelineProvider;
    public final Provider notifUserManagerProvider;
    public final Provider notificationManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider shadeControllerProvider;
    public final Provider statusBarServiceProvider;
    public final Provider sysUiStateProvider;
    public final Provider sysuiMainExecutorProvider;
    public final Provider visibilityProvider;
    public final Provider visualInterruptionDecisionProvider;
    public final Provider zenModeControllerProvider;

    public SystemUIModule_ProvideBubblesManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.contextProvider = provider;
        this.bubblesOptionalProvider = provider2;
        this.notificationShadeWindowControllerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.shadeControllerProvider = provider5;
        this.statusBarServiceProvider = provider6;
        this.notificationManagerProvider = provider7;
        this.dreamManagerProvider = provider8;
        this.visibilityProvider = provider9;
        this.visualInterruptionDecisionProvider = provider10;
        this.zenModeControllerProvider = provider11;
        this.notifUserManagerProvider = provider12;
        this.notifCollectionProvider = provider13;
        this.notifPipelineProvider = provider14;
        this.sysUiStateProvider = provider15;
        this.featureFlagsProvider = provider16;
        this.notifPipelineFlagsProvider = provider17;
        this.sysuiMainExecutorProvider = provider18;
        this.broadcastDispatcherProvider = provider19;
    }

    public static Optional provideBubblesManager(Context context, Optional optional, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, ShadeController shadeController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, IDreamManager iDreamManager, NotificationVisibilityProvider notificationVisibilityProvider, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, FeatureFlags featureFlags, NotifPipelineFlags notifPipelineFlags, Executor executor, BroadcastDispatcher broadcastDispatcher) {
        Optional ofNullable = Optional.ofNullable(optional.isPresent() ? new BubblesManager(context, (Bubbles) optional.get(), notificationShadeWindowController, keyguardStateController, shadeController, iStatusBarService, iNotificationManager, iDreamManager, notificationVisibilityProvider, visualInterruptionDecisionProvider, zenModeController, notificationLockscreenUserManager, commonNotifCollection, notifPipeline, sysUiState, featureFlags, notifPipelineFlags, executor, broadcastDispatcher) : null);
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBubblesManager((Context) this.contextProvider.get(), (Optional) this.bubblesOptionalProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (INotificationManager) this.notificationManagerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (VisualInterruptionDecisionProvider) this.visualInterruptionDecisionProvider.get(), (ZenModeController) this.zenModeControllerProvider.get(), (NotificationLockscreenUserManager) this.notifUserManagerProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (SysUiState) this.sysUiStateProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (Executor) this.sysuiMainExecutorProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
