package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsActivityStarterImpl implements ControlsActivityStarter {
    public final ActivityStarter activityStarter;
    public final ControlsComponent controlsComponent;
    public final PanelInteractor panelInteractor;
    public final StatusBarStateController statusBarStateController;

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

    /* JADX WARN: Removed duplicated region for block: B:23:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startSecControlsActivity(android.content.Context r12, com.android.systemui.animation.ActivityTransitionAnimator.Controller r13) {
        /*
            r11 = this;
            com.android.systemui.controls.dagger.ControlsComponent r0 = r11.controlsComponent
            java.util.Optional r1 = r0.controlsUiController
            boolean r1 = r1.isEmpty()
            java.lang.String r2 = "ControlsActivityStarterImpl"
            if (r1 != 0) goto Ld0
            java.util.Optional r1 = r0.secControlsUiController
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto Ld0
            java.util.Optional r1 = r0.controlsUtil
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L1e
            goto Ld0
        L1e:
            java.util.Optional r1 = r0.secControlsUiController
            java.lang.Object r1 = r1.get()
            com.android.systemui.controls.ui.SecControlsUiController r1 = (com.android.systemui.controls.ui.SecControlsUiController) r1
            com.android.systemui.controls.ui.SecControlsUiControllerImpl r1 = (com.android.systemui.controls.ui.SecControlsUiControllerImpl) r1
            boolean r3 = r1.hidden
            if (r3 != 0) goto L46
            boolean r3 = r1.isShowOverLockscreenWhenLocked
            if (r3 == 0) goto L46
            com.android.systemui.plugins.statusbar.StatusBarStateController r3 = r11.statusBarStateController
            int r3 = r3.getState()
            if (r3 != 0) goto L46
            java.lang.String r12 = "startSecControlsActivity is not Hidden : just close quick panel"
            android.util.Log.d(r2, r12)
            com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor r11 = r11.panelInteractor
            com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl r11 = (com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl) r11
            r11.collapsePanels()
            return
        L46:
            android.content.Intent r2 = new android.content.Intent
            r2.<init>()
            android.content.ComponentName r3 = new android.content.ComponentName
            java.util.Optional r4 = r0.controlsUiController
            java.lang.Object r4 = r4.get()
            com.android.systemui.controls.ui.ControlsUiController r4 = (com.android.systemui.controls.ui.ControlsUiController) r4
            com.android.systemui.controls.ui.SecControlsUiControllerImpl r4 = (com.android.systemui.controls.ui.SecControlsUiControllerImpl) r4
            r4.loadComponentInfo()
            dagger.Lazy r5 = r4.controlsListingController
            java.lang.Object r5 = r5.get()
            com.android.systemui.controls.management.ControlsListingController r5 = (com.android.systemui.controls.management.ControlsListingController) r5
            com.android.systemui.controls.management.ControlsListingControllerImpl r5 = (com.android.systemui.controls.management.ControlsListingControllerImpl) r5
            java.util.List r5 = r5.getCurrentServices()
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            boolean r5 = r5.isEmpty()
            r6 = 0
            r7 = 1
            java.lang.String r8 = "SecControlsUiControllerImpl"
            com.android.systemui.controls.util.ControlsUtil r9 = r4.controlsUtil
            if (r5 != 0) goto L8d
            android.content.Context r5 = r4.context
            r9.getClass()
            java.lang.String r10 = "ControlsOOBEManageAppsCompleted"
            boolean r5 = com.android.systemui.Prefs.getBoolean(r5, r10, r6)
            if (r5 != 0) goto L8d
            r4.isShowOverLockscreenWhenLocked = r6
            java.lang.String r4 = "resolveActivity SecControlsProviderSelectorActivity"
            android.util.Log.d(r8, r4)
            java.lang.Class<com.android.systemui.controls.management.SecControlsProviderSelectorActivity> r4 = com.android.systemui.controls.management.SecControlsProviderSelectorActivity.class
            goto Lab
        L8d:
            boolean r5 = r9.isSecureLocked()
            if (r5 == 0) goto La2
            android.content.Context r5 = r4.context
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r9 = "lockscreen_show_controls"
            int r5 = android.provider.Settings.Secure.getInt(r5, r9, r6)
            if (r5 == 0) goto La2
            r6 = r7
        La2:
            r4.isShowOverLockscreenWhenLocked = r6
            java.lang.String r4 = "resolveActivity SecControlsActivity isShowOverLockscreenWhenLocked = "
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r4, r8, r6)
            java.lang.Class<com.android.systemui.controls.ui.SecControlsActivity> r4 = com.android.systemui.controls.ui.SecControlsActivity.class
        Lab:
            r3.<init>(r12, r4)
            r2.setComponent(r3)
            r12 = 335544320(0x14000000, float:6.4623485E-27)
            r2.addFlags(r12)
            java.util.Optional r12 = r0.controlsUtil
            java.lang.Object r12 = r12.get()
            com.android.systemui.controls.util.ControlsUtil r12 = (com.android.systemui.controls.util.ControlsUtil) r12
            boolean r12 = r12.isSecureLocked()
            com.android.systemui.plugins.ActivityStarter r11 = r11.activityStarter
            if (r12 == 0) goto Lcc
            boolean r12 = r1.isShowOverLockscreenWhenLocked
            r11.startActivity(r2, r7, r13, r12)
            return
        Lcc:
            r11.startActivity(r2, r7)
            return
        Ld0:
            java.lang.String r11 = "feature:android.software.controls is disabled"
            android.util.Log.w(r2, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.util.ControlsActivityStarterImpl.startSecControlsActivity(android.content.Context, com.android.systemui.animation.ActivityTransitionAnimator$Controller):void");
    }
}
