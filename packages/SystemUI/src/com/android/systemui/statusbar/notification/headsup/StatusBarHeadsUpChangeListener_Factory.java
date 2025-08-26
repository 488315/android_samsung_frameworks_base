package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class StatusBarHeadsUpChangeListener_Factory implements Provider {
    public final Provider headsUpManagerProvider;
    public final Provider keyguardBypassControllerProvider;
    public final Provider notificationRemoteInputManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider nsslControllerProvider;
    public final Provider panelExpansionInteractorProvider;
    public final Provider shadeViewControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowControllerStoreProvider;

    public StatusBarHeadsUpChangeListener_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.notificationShadeWindowControllerProvider = provider;
        this.statusBarWindowControllerStoreProvider = provider2;
        this.shadeViewControllerProvider = provider3;
        this.panelExpansionInteractorProvider = provider4;
        this.nsslControllerProvider = provider5;
        this.keyguardBypassControllerProvider = provider6;
        this.headsUpManagerProvider = provider7;
        this.statusBarStateControllerProvider = provider8;
        this.notificationRemoteInputManagerProvider = provider9;
    }

    public static StatusBarHeadsUpChangeListener newInstance(NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowControllerStore statusBarWindowControllerStore, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardBypassController keyguardBypassController, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, NotificationRemoteInputManager notificationRemoteInputManager) {
        return new StatusBarHeadsUpChangeListener(notificationShadeWindowController, statusBarWindowControllerStore, shadeViewController, panelExpansionInteractor, notificationStackScrollLayoutController, keyguardBypassController, headsUpManager, statusBarStateController, notificationRemoteInputManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new StatusBarHeadsUpChangeListener((NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (StatusBarWindowControllerStore) this.statusBarWindowControllerStoreProvider.get(), (ShadeViewController) this.shadeViewControllerProvider.get(), (PanelExpansionInteractor) this.panelExpansionInteractorProvider.get(), (NotificationStackScrollLayoutController) this.nsslControllerProvider.get(), (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (NotificationRemoteInputManager) this.notificationRemoteInputManagerProvider.get());
    }
}
