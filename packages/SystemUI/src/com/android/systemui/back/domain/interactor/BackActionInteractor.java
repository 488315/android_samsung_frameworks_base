package com.android.systemui.back.domain.interactor;

import android.window.OnBackInvokedCallback;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecQuickSettingsControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class BackActionInteractor implements CoreStartable {
    public final BackActionInteractor$callback$2 callback;
    public boolean isCallbackRegistered;
    public final NotificationPanelViewController notificationPanelViewController;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public QuickSettingsController qsController;
    public final CoroutineScope scope;
    public ShadeBackActionInteractor shadeBackActionInteractor;
    public final ShadeController shadeController;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final SysuiStatusBarStateController statusBarStateController;
    public final WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.back.domain.interactor.BackActionInteractor$callback$2] */
    public BackActionInteractor(CoroutineScope coroutineScope, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ShadeController shadeController, NotificationShadeWindowController notificationShadeWindowController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, NotificationPanelViewController notificationPanelViewController) {
        this.scope = coroutineScope;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.shadeController = shadeController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.windowRootViewVisibilityInteractor = windowRootViewVisibilityInteractor;
        this.notificationPanelViewController = notificationPanelViewController;
        Flags.FEATURE_FLAGS.getClass();
        this.callback = new OnBackInvokedCallback() { // from class: com.android.systemui.back.domain.interactor.BackActionInteractor$callback$2
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                BackActionInteractor.this.onBackRequested();
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean onBackRequested() {
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.statusBarKeyguardViewManager;
        boolean canHandleBackPressed = statusBarKeyguardViewManager.canHandleBackPressed();
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if (canHandleBackPressed) {
            statusBarKeyguardViewManager.onBackPressed();
            if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 2) {
                sysuiStatusBarStateController.setState(1);
            }
            return true;
        }
        QuickSettingsController quickSettingsController = this.qsController;
        if (quickSettingsController == null) {
            quickSettingsController = null;
        }
        if (quickSettingsController.isCustomizing()) {
            QuickSettingsController quickSettingsController2 = this.qsController;
            (quickSettingsController2 != null ? quickSettingsController2 : null).closeQsCustomizer();
            return true;
        }
        QuickSettingsController quickSettingsController3 = this.qsController;
        if (quickSettingsController3 == null) {
            quickSettingsController3 = null;
        }
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl$1 = quickSettingsController3.getSecQuickSettingsControllerImpl$1();
        if (secQuickSettingsControllerImpl$1 != null) {
            secQuickSettingsControllerImpl$1.closeQSTooltip();
            SecQSPanelController qsPanelController = secQuickSettingsControllerImpl$1.getQsPanelController();
            if (qsPanelController != null ? qsPanelController.mQSCMainViewController.isShown : false) {
                SecQSPanelController qsPanelController2 = secQuickSettingsControllerImpl$1.getQsPanelController();
                if (qsPanelController2 != null) {
                    qsPanelController2.mQSCMainViewController.backKeyEvent();
                }
                return true;
            }
            QS qs = (QS) secQuickSettingsControllerImpl$1.qsSupplier.get();
            if (qs != null ? qs.isShowingDetail() : false) {
                QS qs2 = (QS) secQuickSettingsControllerImpl$1.qsSupplier.get();
                if (qs2 != null) {
                    qs2.closeDetail();
                }
                return true;
            }
        }
        QuickSettingsController quickSettingsController4 = this.qsController;
        if (quickSettingsController4 == null) {
            quickSettingsController4 = null;
        }
        if (quickSettingsController4.getExpanded$1()) {
            ShadeBackActionInteractor shadeBackActionInteractor = this.shadeBackActionInteractor;
            (shadeBackActionInteractor != null ? shadeBackActionInteractor : null).animateCollapseQs(false);
            return true;
        }
        ShadeBackActionInteractor shadeBackActionInteractor2 = this.shadeBackActionInteractor;
        if (shadeBackActionInteractor2 == null) {
            shadeBackActionInteractor2 = null;
        }
        if (shadeBackActionInteractor2.closeUserSwitcherIfOpen()) {
            return true;
        }
        int i = ((StatusBarStateControllerImpl) this.statusBarStateController).mState;
        if ((i == 1 || i == 2 || this.statusBarKeyguardViewManager.isBouncerShowingOverDream()) ? false : true) {
            ShadeBackActionInteractor shadeBackActionInteractor3 = this.shadeBackActionInteractor;
            if (shadeBackActionInteractor3 == null) {
                shadeBackActionInteractor3 = null;
            }
            if (shadeBackActionInteractor3.canBeCollapsed()) {
                ShadeBackActionInteractor shadeBackActionInteractor4 = this.shadeBackActionInteractor;
                (shadeBackActionInteractor4 != null ? shadeBackActionInteractor4 : null).onBackPressed();
                this.shadeController.animateCollapseShade(0);
            }
            return true;
        }
        if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 2) {
            sysuiStatusBarStateController.setState(1);
            return true;
        }
        NotificationPanelViewController notificationPanelViewController = this.notificationPanelViewController;
        if ((notificationPanelViewController.mPluginLockViewMode == 1) != true) {
            return false;
        }
        notificationPanelViewController.mPluginLockMediator.onEventReceived(AbsAdapter$1$$ExternalSyntheticOutline0.m("action", PluginLock.ACTION_BACK_KEY));
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new BackActionInteractor$start$1(this, null), 3);
    }
}
