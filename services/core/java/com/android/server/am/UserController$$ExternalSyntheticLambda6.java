package com.android.server.am;

import android.os.Handler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class UserController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ UserController$$ExternalSyntheticLambda6(
            int i, int i2, int i3, Object obj) {
        this.$r8$classId = i3;
        this.f$0 = obj;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserController userController = (UserController) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                userController.mHandler.removeMessages(80);
                Handler handler = userController.mHandler;
                handler.sendMessage(handler.obtainMessage(80, i, i2));
                break;
            default:
                UserController.AnonymousClass4 anonymousClass4 =
                        (UserController.AnonymousClass4) this.f$0;
                anonymousClass4.this$0.startUser(this.f$1, this.f$2);
                break;
        }
    }
}
