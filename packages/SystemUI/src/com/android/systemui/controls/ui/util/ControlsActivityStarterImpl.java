package com.android.systemui.controls.ui.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Prefs;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.SecControlsProviderSelectorActivity;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.controls.ui.SecControlsActivity;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsActivityStarterImpl implements ControlsActivityStarter {
    public final ActivityStarter activityStarter;
    public final ControlsComponent controlsComponent;
    public final PanelInteractor panelInteractor;
    public final StatusBarStateController statusBarStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ControlsActivityStarterImpl(ActivityStarter activityStarter, ControlsComponent controlsComponent, StatusBarStateController statusBarStateController, PanelInteractor panelInteractor) {
        this.activityStarter = activityStarter;
        this.controlsComponent = controlsComponent;
        this.statusBarStateController = statusBarStateController;
        this.panelInteractor = panelInteractor;
    }

    public final void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, (Class<?>) cls);
        intent.addFlags(335544320);
        this.activityStarter.startActivity(intent, true);
    }

    public final void startSecControlsActivity(Context context, ActivityTransitionAnimator.Controller controller) {
        Class cls;
        ControlsComponent controlsComponent = this.controlsComponent;
        if (!controlsComponent.secControlsUiController.isPresent()) {
            Log.w("ControlsActivityStarterImpl", "feature:android.software.controls is disabled");
            return;
        }
        SecControlsUiController secControlsUiController = (SecControlsUiController) controlsComponent.secControlsUiController.get();
        if (secControlsUiController != null) {
            SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) secControlsUiController;
            if (!secControlsUiControllerImpl.hidden && secControlsUiControllerImpl.isShowOverLockscreenWhenLocked && this.statusBarStateController.getState() == 0) {
                Log.d("ControlsActivityStarterImpl", "startSecControlsActivity is not Hidden : just close quick panel");
                this.panelInteractor.collapsePanels();
                return;
            }
        }
        Intent intent = new Intent();
        SecControlsUiControllerImpl secControlsUiControllerImpl2 = (SecControlsUiControllerImpl) ((ControlsUiController) controlsComponent.controlsUiController.get());
        secControlsUiControllerImpl2.loadComponentInfo();
        boolean z = !((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) secControlsUiControllerImpl2.controlsListingController.get())).getCurrentServices()).isEmpty();
        boolean z2 = false;
        ControlsUtil controlsUtil = secControlsUiControllerImpl2.controlsUtil;
        if (z) {
            Context context2 = secControlsUiControllerImpl2.context;
            controlsUtil.getClass();
            if (!Prefs.get(context2).getBoolean("ControlsOOBEManageAppsCompleted", false)) {
                secControlsUiControllerImpl2.isShowOverLockscreenWhenLocked = false;
                Log.d("SecControlsUiControllerImpl", "resolveActivity SecControlsProviderSelectorActivity");
                cls = SecControlsProviderSelectorActivity.class;
                intent.setComponent(new ComponentName(context, (Class<?>) cls));
                intent.addFlags(335544320);
                this.activityStarter.startActivity(intent, true, controller, ((SecControlsUiControllerImpl) ((SecControlsUiController) controlsComponent.secControlsUiController.get())).isShowOverLockscreenWhenLocked);
            }
        }
        if (controlsUtil.isSecureLocked() && Settings.Secure.getInt(secControlsUiControllerImpl2.context.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
            z2 = true;
        }
        secControlsUiControllerImpl2.isShowOverLockscreenWhenLocked = z2;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("resolveActivity SecControlsActivity isShowOverLockscreenWhenLocked = ", "SecControlsUiControllerImpl", z2);
        cls = SecControlsActivity.class;
        intent.setComponent(new ComponentName(context, (Class<?>) cls));
        intent.addFlags(335544320);
        this.activityStarter.startActivity(intent, true, controller, ((SecControlsUiControllerImpl) ((SecControlsUiController) controlsComponent.secControlsUiController.get())).isShowOverLockscreenWhenLocked);
    }
}
