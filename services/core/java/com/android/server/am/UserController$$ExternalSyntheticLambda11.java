package com.android.server.am;

import android.os.Handler;

public final /* synthetic */ class UserController$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserController f$0;

    public /* synthetic */ UserController$$ExternalSyntheticLambda11(
            UserController userController, int i) {
        this.$r8$classId = i;
        this.f$0 = userController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UserController userController = this.f$0;
        switch (i) {
            case 0:
                userController.endUserSwitch();
                break;
            default:
                if (!userController.mHandler.hasMessages(40)) {
                    Handler handler = userController.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(40), 1000L);
                    break;
                }
                break;
        }
    }
}
