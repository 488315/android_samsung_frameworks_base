package com.android.server.location.gnss.sec;

import android.os.SystemProperties;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
