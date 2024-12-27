package com.android.server.policy;

import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneWindowManager.PowerKeyRule f$0;

    public /* synthetic */ PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda0(
            PhoneWindowManager.PowerKeyRule powerKeyRule, int i) {
        this.$r8$classId = i;
        this.f$0 = powerKeyRule;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PhoneWindowManager.PowerKeyRule powerKeyRule = this.f$0;
        switch (i) {
            case 0:
                PhoneWindowManager phoneWindowManager = powerKeyRule.this$0;
                Slog.d("WindowManager", "StemPrimaryKeyRule: executing deferred onKeyUp");
                try {
                    phoneWindowManager.mFocusedTaskInfoOnStemPrimarySingleKeyUp =
                            phoneWindowManager.mActivityManagerService.getFocusedRootTaskInfo();
                } catch (RemoteException e) {
                    Slog.e(
                            "WindowManager",
                            "StemPrimaryKeyRule: onKeyUp: error while getting focused task info.",
                            e);
                }
                PhoneWindowManager.m791$$Nest$mstemPrimaryPress(phoneWindowManager, 1);
                break;
            default:
                PhoneWindowManager.m791$$Nest$mstemPrimaryPress(powerKeyRule.this$0, 1);
                break;
        }
    }
}
