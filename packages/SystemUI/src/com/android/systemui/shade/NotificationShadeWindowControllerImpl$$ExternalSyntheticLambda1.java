package com.android.systemui.shade;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.shade.data.repository.SecNotificationShadeWindowStateRepository;
import com.android.systemui.util.SafeUIState;
import java.io.PrintWriter;

public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowControllerImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                notificationShadeWindowControllerImpl.mHelper.applyHelper((NotificationShadeWindowState) this.f$1);
                break;
            case 1:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = this.f$0;
                NotificationShadeWindowState notificationShadeWindowState = (NotificationShadeWindowState) this.f$1;
                SecNotificationShadeWindowStateRepository secNotificationShadeWindowStateRepository = notificationShadeWindowControllerImpl2.mSecNotificationShadeWindowStateInteractor.repository;
                secNotificationShadeWindowStateRepository._state.updateState(null, notificationShadeWindowState);
                secNotificationShadeWindowStateRepository._shadeOrQsExpanded.updateState(null, Boolean.valueOf(notificationShadeWindowState.shadeOrQsExpanded));
                secNotificationShadeWindowStateRepository._statusBarState.updateState(null, Integer.valueOf(notificationShadeWindowState.statusBarState));
                break;
            case 2:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl3 = this.f$0;
                NotificationShadeWindowState notificationShadeWindowState2 = (NotificationShadeWindowState) this.f$1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl3.mHelper;
                secNotificationShadeWindowControllerHelperImpl.getClass();
                if ((LsRune.SECURITY_BOUNCER_WINDOW || SafeUIState.isSysUiSafeModeEnabled()) && secNotificationShadeWindowControllerHelperImpl.bouncerContainer != null) {
                    secNotificationShadeWindowControllerHelperImpl.applyBouncer(notificationShadeWindowState2);
                    break;
                }
                break;
            default:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl4 = this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl4.mHelper;
                secNotificationShadeWindowControllerHelperImpl2.getClass();
                if (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW) {
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  EMM=", secNotificationShadeWindowControllerHelperImpl2.engineerModeManager.isCaptureEnabled, printWriter);
                    break;
                }
                break;
        }
    }
}
