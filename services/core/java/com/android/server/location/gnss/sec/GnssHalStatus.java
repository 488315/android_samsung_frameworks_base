package com.android.server.location.gnss.sec;

import android.os.SystemProperties;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class GnssHalStatus {
    public boolean isHalStatusChecked = false;
    public ExecutorService excutorService = null;

    public void triggerCheckingHalStatus() {
        triggerCheckingHalStatus(3000L);
    }

    public void triggerCheckingHalStatus(final long j) {
        this.isHalStatusChecked = false;
        Runnable runnable = new Runnable() { // from class: com.android.server.location.gnss.sec.GnssHalStatus$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GnssHalStatus.this.lambda$triggerCheckingHalStatus$0(j);
            }
        };
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.excutorService = newSingleThreadExecutor;
        newSingleThreadExecutor.submit(runnable);
        this.excutorService.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerCheckingHalStatus$0(long j) {
        try {
            Thread.sleep(j);
            checkHalStatusAndReset();
        } catch (InterruptedException unused) {
            Log.e("GnssHalStatus", "checkHalStatusAndReset() failed.");
        }
    }

    public void updateHalStatusChecked(boolean z) {
        this.isHalStatusChecked = z;
        ExecutorService executorService = this.excutorService;
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        this.excutorService.shutdownNow();
    }

    public final void checkHalStatusAndReset() {
        if (this.isHalStatusChecked) {
            return;
        }
        Log.e("GnssHalStatus", "Calling GnssNative was failed. It will be recovered.");
        SystemProperties.set("dev.gnss.initializehal", "ON");
    }
}
