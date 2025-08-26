package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthController;

/* loaded from: classes.dex */
public final /* synthetic */ class AuthController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthController f$0;

    public /* synthetic */ AuthController$$ExternalSyntheticLambda3(AuthController authController, int i) {
        this.$r8$classId = i;
        this.f$0 = authController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AuthController authController = this.f$0;
        switch (i) {
            case 0:
                int i2 = AuthController.$r8$clinit;
                authController.updateUdfpsLocation();
                break;
            default:
                int i3 = AuthController.AnonymousClass1.$r8$clinit;
                int i4 = AuthController.$r8$clinit;
                authController.mExecution.assertIsMainThread();
                authController.closeDialog(3, "owner not in foreground");
                break;
        }
    }
}
