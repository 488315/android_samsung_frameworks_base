package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthController f$0;

    public /* synthetic */ AuthController$$ExternalSyntheticLambda1(AuthController authController, int i) {
        this.$r8$classId = i;
        this.f$0 = authController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.cancelIfOwnerIsNotInForeground();
                break;
            case 1:
                this.f$0.updateUdfpsLocation();
                break;
            default:
                AuthController authController = this.f$0;
                int i = AuthController.TaskStackListenerC10551.$r8$clinit;
                authController.cancelIfOwnerIsNotInForeground();
                break;
        }
    }
}
