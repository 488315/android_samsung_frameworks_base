package com.android.server.power;

import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
