package com.android.systemui.biometrics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                authController.updateUdfpsLocation();
                break;
            default:
                authController.cancelIfOwnerIsNotInForeground();
                break;
        }
    }
}
