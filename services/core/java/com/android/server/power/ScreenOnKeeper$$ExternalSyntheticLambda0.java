package com.android.server.power;

import android.content.Intent;
import android.os.UserHandle;

public final /* synthetic */ class ScreenOnKeeper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenOnKeeper f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ ScreenOnKeeper$$ExternalSyntheticLambda0(
            ScreenOnKeeper screenOnKeeper, Intent intent, int i) {
        this.$r8$classId = i;
        this.f$0 = screenOnKeeper;
        this.f$1 = intent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenOnKeeper screenOnKeeper = this.f$0;
                screenOnKeeper.mContext.sendBroadcastAsUser(this.f$1, UserHandle.CURRENT);
                break;
            default:
                ScreenOnKeeper screenOnKeeper2 = this.f$0;
                screenOnKeeper2.mContext.sendBroadcastAsUser(this.f$1, UserHandle.CURRENT);
                break;
        }
    }
}
