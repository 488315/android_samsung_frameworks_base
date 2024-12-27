package com.android.server.power.batterysaver;

import android.app.NotificationManager;
import android.os.UserHandle;

public final /* synthetic */ class BatterySaverStateMachine$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ BatterySaverStateMachine f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BatterySaverStateMachine$$ExternalSyntheticLambda5(
            BatterySaverStateMachine batterySaverStateMachine, int i) {
        this.f$0 = batterySaverStateMachine;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BatterySaverStateMachine batterySaverStateMachine = this.f$0;
        ((NotificationManager)
                        batterySaverStateMachine.mContext.getSystemService(
                                NotificationManager.class))
                .cancelAsUser("BatterySaverStateMachine", this.f$1, UserHandle.ALL);
    }
}
