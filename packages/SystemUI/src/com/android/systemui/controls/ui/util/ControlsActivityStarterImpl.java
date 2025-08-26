package com.android.systemui.controls.ui.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ControlsActivityStarterImpl implements ControlsActivityStarter {
    public final ActivityStarter activityStarter;
    public final ControlsComponent controlsComponent;
    public final PanelInteractor panelInteractor;
    public final StatusBarStateController statusBarStateController;

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

    /* JADX WARN: Removed duplicated region for block: B:22:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startSecControlsActivity(Context context, ActivityTransitionAnimator.Controller controller) {
        Class cls;
        ControlsComponent controlsComponent = this.controlsComponent;
        if (controlsComponent.controlsUiController.isEmpty() || controlsComponent.secControlsUiController.isEmpty() || controlsComponent.controlsUtil.isEmpty()) {
            Log.w("ControlsActivityStarterImpl", "feature:android.software.controls is disabled");
            return;
        }
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) ((SecControlsUiController) controlsComponent.secControlsUiController.get());
        if (!secControlsUiControllerImpl.hidden && secControlsUiControllerImpl.isShowOverLockscreenWhenLocked && this.statusBarStateController.getState() == 0) {
            Log.d("ControlsActivityStarterImpl", "startSecControlsActivity is not Hidden : just close quick panel");
            ((PanelInteractorImpl) this.panelInteractor).collapsePanels();
            return;
        }
        Intent intent = new Intent();
        SecControlsUiControllerImpl secControlsUiControllerImpl2 = (SecControlsUiControllerImpl) ((ControlsUiController) controlsComponent.controlsUiController.get());
        secControlsUiControllerImpl2.loadComponentInfo();
        boolean zIsEmpty = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) secControlsUiControllerImpl2.controlsListingController.get())).getCurrentServices()).isEmpty();
        boolean z = false;
        ControlsUtil controlsUtil = secControlsUiControllerImpl2.controlsUtil;
        if (!zIsEmpty) {
            Context context2 = secControlsUiControllerImpl2.context;
            controlsUtil.getClass();
            if (Prefs.getBoolean(context2, "ControlsOOBEManageAppsCompleted", false)) {
                if (controlsUtil.isSecureLocked() && Settings.Secure.getInt(secControlsUiControllerImpl2.context.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                    z = true;
                }
                secControlsUiControllerImpl2.isShowOverLockscreenWhenLocked = z;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("resolveActivity SecControlsActivity isShowOverLockscreenWhenLocked = ", "SecControlsUiControllerImpl", z);
                cls = SecControlsActivity.class;
            } else {
                secControlsUiControllerImpl2.isShowOverLockscreenWhenLocked = false;
                Log.d("SecControlsUiControllerImpl", "resolveActivity SecControlsProviderSelectorActivity");
                cls = SecControlsProviderSelectorActivity.class;
            }
        }
        intent.setComponent(new ComponentName(context, (Class<?>) cls));
        intent.addFlags(335544320);
        boolean zIsSecureLocked = ((ControlsUtil) controlsComponent.controlsUtil.get()).isSecureLocked();
        ActivityStarter activityStarter = this.activityStarter;
        if (zIsSecureLocked) {
            activityStarter.startActivity(intent, true, controller, secControlsUiControllerImpl.isShowOverLockscreenWhenLocked);
        } else {
            activityStarter.startActivity(intent, true);
        }
    }
}
