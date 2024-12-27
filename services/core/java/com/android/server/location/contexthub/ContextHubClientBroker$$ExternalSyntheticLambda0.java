package com.android.server.location.contexthub;

import android.util.Log;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContextHubClientBroker f$0;

    public /* synthetic */ ContextHubClientBroker$$ExternalSyntheticLambda0(
            ContextHubClientBroker contextHubClientBroker, int i) {
        this.$r8$classId = i;
        this.f$0 = contextHubClientBroker;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        ContextHubClientBroker contextHubClientBroker = this.f$0;
        switch (i) {
            case 0:
                contextHubClientBroker.mIsWakelockUsable.set(false);
                while (contextHubClientBroker.mWakeLock.isHeld()) {
                    try {
                        contextHubClientBroker.mWakeLock.release();
                    } catch (RuntimeException e) {
                        Log.e(
                                "ContextHubClientBroker",
                                "Releasing the wakelock for all acquisitions fails - ",
                                e);
                        return;
                    }
                }
                break;
            case 1:
                if (contextHubClientBroker.mWakeLock.isHeld()) {
                    try {
                        contextHubClientBroker.mWakeLock.release();
                        break;
                    } catch (RuntimeException e2) {
                        Log.e("ContextHubClientBroker", "Releasing the wakelock fails - ", e2);
                        return;
                    }
                }
                break;
            default:
                if (contextHubClientBroker.mIsWakelockUsable.get()) {
                    contextHubClientBroker.mWakeLock.acquire(5000L);
                    break;
                }
                break;
        }
    }
}
