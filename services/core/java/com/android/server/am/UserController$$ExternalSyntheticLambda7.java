package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserController$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserController f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ UserController$$ExternalSyntheticLambda7(
            UserController userController,
            UserController$$ExternalSyntheticLambda6 userController$$ExternalSyntheticLambda6,
            int i) {
        this.$r8$classId = i;
        this.f$0 = userController;
        this.f$1 = userController$$ExternalSyntheticLambda6;
    }

    public /* synthetic */ UserController$$ExternalSyntheticLambda7(
            UserController userController, Runnable runnable) {
        this.$r8$classId = 1;
        this.f$0 = userController;
        this.f$1 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserController userController = this.f$0;
                userController.mUiHandler.post(
                        new UserController$$ExternalSyntheticLambda7(userController, this.f$1));
                break;
            case 1:
                UserController userController2 = this.f$0;
                userController2.mInjector.dismissUserSwitchingDialog(this.f$1);
                break;
            default:
                UserController userController3 = this.f$0;
                userController3.mUiHandler.post(
                        new UserController$$ExternalSyntheticLambda7(userController3, this.f$1));
                break;
        }
    }
}
