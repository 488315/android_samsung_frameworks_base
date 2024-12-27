package com.android.server.location.gnss.sec;

import android.os.SystemProperties;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class GnssHalStatus {
    public boolean isHalStatusChecked = false;
    public ExecutorService excutorService = null;

    public final void triggerCheckingHalStatus(final long j) {
        this.isHalStatusChecked = false;
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.location.gnss.sec.GnssHalStatus$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GnssHalStatus gnssHalStatus = GnssHalStatus.this;
                        long j2 = j;
                        gnssHalStatus.getClass();
                        try {
                            Thread.sleep(j2);
                            if (gnssHalStatus.isHalStatusChecked) {
                                return;
                            }
                            Log.e(
                                    "GnssHalStatus",
                                    "Calling GnssNative was failed. It will be recovered.");
                            SystemProperties.set("dev.gnss.initializehal", "ON");
                        } catch (InterruptedException unused) {
                            Log.e("GnssHalStatus", "checkHalStatusAndReset() failed.");
                        }
                    }
                };
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.excutorService = newSingleThreadExecutor;
        newSingleThreadExecutor.submit(runnable);
        this.excutorService.shutdown();
    }

    public final void updateHalStatusChecked() {
        this.isHalStatusChecked = true;
        ExecutorService executorService = this.excutorService;
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        this.excutorService.shutdownNow();
    }
}
