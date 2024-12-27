package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.settings.UserTrackerImpl;

public final /* synthetic */ class GlobalActionsDialogLite$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GlobalActionsDialogLite$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((GlobalActionsDialogLite) obj).createActionItems();
                break;
            case 1:
                GlobalActionsDialogLite globalActionsDialogLite = ((GlobalActionsDialogLite.LockDownAction) obj).this$0;
                int i2 = ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().id;
                for (int i3 : globalActionsDialogLite.mUserManager.getEnabledProfileIds(i2)) {
                    if (i3 != i2) {
                        globalActionsDialogLite.mTrustManager.setDeviceLockedForUser(i3, true);
                    }
                }
                break;
            default:
                ((GlobalActionsDialogLite.LogoutAction) obj).this$0.mDevicePolicyManager.logoutUser();
                break;
        }
    }
}
