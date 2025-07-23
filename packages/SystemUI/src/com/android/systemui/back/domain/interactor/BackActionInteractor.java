package com.android.systemui.back.domain.interactor;

import android.util.Log;
import android.window.OnBackInvokedCallback;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecQuickSettingsControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackActionInteractor implements CoreStartable {
    public final BackActionInteractor$callback$2 callback = new OnBackInvokedCallback() { // from class: com.android.systemui.back.domain.interactor.BackActionInteractor$callback$2
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            BackActionInteractor.this.onBackRequested();
        }
    };
    public boolean isCallbackRegistered;
    public final NotificationPanelViewController notificationPanelViewController;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public final QuickSettingsController qsController;
    public final CoroutineScope scope;
    public final ShadeBackActionInteractor shadeBackActionInteractor;
    public final ShadeController shadeController;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final SysuiStatusBarStateController statusBarStateController;
    public final WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.back.domain.interactor.BackActionInteractor$callback$2] */
    public BackActionInteractor(CoroutineScope coroutineScope, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ShadeController shadeController, NotificationShadeWindowController notificationShadeWindowController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, ShadeBackActionInteractor shadeBackActionInteractor, QuickSettingsController quickSettingsController, NotificationPanelViewController notificationPanelViewController) {
        this.scope = coroutineScope;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.shadeController = shadeController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.windowRootViewVisibilityInteractor = windowRootViewVisibilityInteractor;
        this.shadeBackActionInteractor = shadeBackActionInteractor;
        this.qsController = quickSettingsController;
        this.notificationPanelViewController = notificationPanelViewController;
    }

    public final boolean onBackRequested() {
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null && pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
            Log.d("PluginFaceWidgetManager", "onBackRequested: ");
            try {
                pluginFaceWidgetManager.mFaceWidgetPlugin.onBackRequested();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.statusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager.canHandleBackPressed()) {
            statusBarKeyguardViewManager.onBackPressed();
            Log.d("BackActionInteractor", "onBackRequested: statusBarKeyguardViewManager.onBackPressed()");
            return true;
        }
        QuickSettingsController quickSettingsController = this.qsController;
        if (quickSettingsController.isCustomizing()) {
            quickSettingsController.closeQsCustomizer();
            Log.d("BackActionInteractor", "onBackRequested: qsController.closeQsCustomizer()");
            return true;
        }
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl$1 = quickSettingsController.getSecQuickSettingsControllerImpl$1();
        if (secQuickSettingsControllerImpl$1 != null) {
            secQuickSettingsControllerImpl$1.closeQSTooltip();
            SecQSPanelController qsPanelController = secQuickSettingsControllerImpl$1.getQsPanelController();
            if (qsPanelController != null ? qsPanelController.mQSCMainViewController.isShown : false) {
                SecQSPanelController qsPanelController2 = secQuickSettingsControllerImpl$1.getQsPanelController();
                if (qsPanelController2 != null) {
                    qsPanelController2.mQSCMainViewController.backKeyEvent();
                }
                Log.d("BackActionInteractor", "onBackRequested: secQuickSettingsControllerImpl.closeCustomizer()");
                return true;
            }
            QS qs = (QS) secQuickSettingsControllerImpl$1.qsSupplier.get();
            if (qs != null ? qs.isShowingDetail() : false) {
                QS qs2 = (QS) secQuickSettingsControllerImpl$1.qsSupplier.get();
                if (qs2 != null) {
                    qs2.closeDetail();
                }
                Log.d("BackActionInteractor", "onBackRequested: secQuickSettingsControllerImpl.closeDetail()");
                return true;
            }
        }
        boolean expanded = quickSettingsController.getExpanded();
        ShadeBackActionInteractor shadeBackActionInteractor = this.shadeBackActionInteractor;
        if (expanded) {
            shadeBackActionInteractor.animateCollapseQs(false);
            Log.d("BackActionInteractor", "onBackRequested: shadeBackActionInteractor.animateCollapseQs()");
            return true;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if ((sysuiStatusBarStateController.getState() == 1 || sysuiStatusBarStateController.getState() == 2 || statusBarKeyguardViewManager.isBouncerShowingOverDream()) ? false : true) {
            if (shadeBackActionInteractor.canBeCollapsed()) {
                shadeBackActionInteractor.onBackPressed();
                this.shadeController.animateCollapseShade(0);
                Log.d("BackActionInteractor", "onBackRequested: shadeController.animateCollapseShade()");
            } else {
                Log.d("BackActionInteractor", "onBackRequested: !shadeBackActionInteractor.canBeCollapsed()");
            }
        } else {
            if (sysuiStatusBarStateController.getState() == 2) {
                sysuiStatusBarStateController.setState(1);
                return true;
            }
            NotificationPanelViewController notificationPanelViewController = this.notificationPanelViewController;
            if (!(notificationPanelViewController.mPluginLockViewMode == 1)) {
                return false;
            }
            notificationPanelViewController.mPluginLockMediator.onEventReceived(KeyguardSecPatternView$$ExternalSyntheticOutline0.m("action", PluginLock.ACTION_BACK_KEY));
        }
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new BackActionInteractor$start$1(this, null), 7);
    }
}
