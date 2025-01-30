package com.android.keyguard;

import android.os.UserHandle;
import com.android.keyguard.AdminSecondaryLockScreenController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AdminSecondaryLockScreenController.IKeyguardCallbackStubC06302 f$0;

    public /* synthetic */ AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0(AdminSecondaryLockScreenController.IKeyguardCallbackStubC06302 iKeyguardCallbackStubC06302, int i) {
        this.$r8$classId = i;
        this.f$0 = iKeyguardCallbackStubC06302;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.this$0.dismiss(UserHandle.getCallingUserId());
                break;
            default:
                this.f$0.this$0.dismiss(KeyguardUpdateMonitor.getCurrentUser());
                break;
        }
    }
}
