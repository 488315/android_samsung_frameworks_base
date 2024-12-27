package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
