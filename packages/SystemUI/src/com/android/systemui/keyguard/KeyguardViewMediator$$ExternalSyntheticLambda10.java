package com.android.systemui.keyguard;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.sdp.internal.SdpAuthenticator;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda10(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        switch (i) {
            case 0:
                if (keyguardViewMediatorHelperImpl.fastUnlockController.isFastWakeAndUnlockMode()) {
                    return;
                }
                synchronized (keyguardViewMediatorHelperImpl.lock$delegate.getValue()) {
                    keyguardViewMediatorHelperImpl.drawnCallback = null;
                    Unit unit = Unit.INSTANCE;
                }
                return;
            case 1:
                keyguardViewMediatorHelperImpl.removeShowMsgOnCoverOpened();
                return;
            case 2:
                keyguardViewMediatorHelperImpl.onAbortKeyguardDone();
                return;
            case 3:
                keyguardViewMediatorHelperImpl.onKeyguardExitFinished$2();
                return;
            case 4:
                keyguardViewMediatorHelperImpl.getClass();
                return;
            case 5:
                keyguardViewMediatorHelperImpl.getClass();
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                    KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                    if (keyguardFoldControllerImpl.initShowTime == 0) {
                        keyguardFoldControllerImpl.initShowTime = System.currentTimeMillis();
                    }
                }
                keyguardViewMediatorHelperImpl.fastUnlockController.reset();
                keyguardViewMediatorHelperImpl.updateMonitor.setUnlockingKeyguard(false);
                keyguardViewMediatorHelperImpl.hidingByDisabled = false;
                return;
            case 6:
                if (keyguardViewMediatorHelperImpl.updateMonitor.getUserHasTrust(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId())) {
                    return;
                }
                int selectedUserId = ((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl.knoxStateMonitor).mSelectedUserInteractor.getSelectedUserId();
                if (!SemPersonaManager.isDoEnabled(selectedUserId)) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "lockSdp :: Maybe keyguard shown as user ", "KnoxStateMonitorImpl");
                    return;
                }
                android.util.Log.d("KnoxStateMonitorImpl", "lockSdp :: Device Owner has been locked");
                try {
                    SdpAuthenticator.getInstance().onDeviceOwnerLocked(selectedUserId);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 7:
                if (keyguardViewMediatorHelperImpl.isSecure$2()) {
                    keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1(keyguardViewMediatorHelperImpl));
                    return;
                }
                return;
            default:
                keyguardViewMediatorHelperImpl.onAbortHandleStartKeyguardExitAnimation();
                return;
        }
    }
}
