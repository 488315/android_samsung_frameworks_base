package com.android.systemui.globalactions;

import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                int i2 = ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().id;
                for (int i3 : globalActionsDialogLite.mUserManager.getEnabledProfileIds(i2)) {
                    if (i3 != i2) {
                        globalActionsDialogLite.mTrustManager.setDeviceLockedForUser(i3, true);
                    }
                }
                break;
            default:
                GlobalActionsDialogLite.this.mLogoutInteractor.logOut();
                break;
        }
    }
}
