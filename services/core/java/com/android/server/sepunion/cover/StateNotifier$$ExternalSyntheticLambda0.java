package com.android.server.sepunion.cover;

import android.os.SystemClock;

import com.samsung.android.sepunion.Log;

public final /* synthetic */ class StateNotifier$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ StateNotifier f$0;

    @Override // java.lang.Runnable
    public final void run() {
        StateNotifier stateNotifier = this.f$0;
        stateNotifier.getClass();
        Log.addLogString(
                "CoverManager_",
                "goToSleep by cover close after boot complete : mLcdOffDisabledByApp is false");
        stateNotifier.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
    }
}
