package com.android.keyguard;

import android.os.UserHandle;
import com.android.keyguard.AdminSecondaryLockScreenController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AdminSecondaryLockScreenController.AnonymousClass2 f$0;

    public /* synthetic */ AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0(AdminSecondaryLockScreenController.AnonymousClass2 anonymousClass2, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AdminSecondaryLockScreenController.AnonymousClass2 anonymousClass2 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass2.this$0.dismiss(UserHandle.getCallingUserId());
                break;
            default:
                AdminSecondaryLockScreenController adminSecondaryLockScreenController = anonymousClass2.this$0;
                adminSecondaryLockScreenController.dismiss(adminSecondaryLockScreenController.mSelectedUserInteractor.getSelectedUserId());
                break;
        }
    }
}
