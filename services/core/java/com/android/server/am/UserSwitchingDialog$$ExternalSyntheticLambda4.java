package com.android.server.am;

import java.util.concurrent.atomic.AtomicBoolean;

public final /* synthetic */ class UserSwitchingDialog$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ UserSwitchingDialog f$0;
    public final /* synthetic */ AtomicBoolean f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda4(
            UserSwitchingDialog userSwitchingDialog,
            AtomicBoolean atomicBoolean,
            Runnable runnable) {
        this.f$0 = userSwitchingDialog;
        this.f$1 = atomicBoolean;
        this.f$2 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UserSwitchingDialog userSwitchingDialog = this.f$0;
        AtomicBoolean atomicBoolean = this.f$1;
        Runnable runnable = this.f$2;
        userSwitchingDialog.getClass();
        if (atomicBoolean.getAndSet(false)) {
            userSwitchingDialog.mHandler.removeCallbacksAndMessages(null);
            runnable.run();
        }
    }
}
