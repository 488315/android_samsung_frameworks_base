package com.android.server.devicepolicy;

import android.os.Bundle;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda158 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda158(DevicePolicyManagerService devicePolicyManagerService, boolean z, boolean z2) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = z;
        this.f$2 = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.InterruptedException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.util.concurrent.locks.ReentrantLock] */
    public final void runOrThrow() {
        NetworkLogger networkLogger;
        NetworkLoggingHandler networkLoggingHandler;
        Bundle bundle;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        devicePolicyManagerService.getClass();
        if (z || z2) {
            SecurityLogMonitor securityLogMonitor = devicePolicyManagerService.mSecurityLogMonitor;
            ((ReentrantLock) securityLogMonitor.mLock).lock();
            try {
                if (securityLogMonitor.mPaused) {
                    securityLogMonitor.mPaused = false;
                    securityLogMonitor.mAllowedToRetrieve = false;
                    ((ReentrantLock) securityLogMonitor.mLock).unlock();
                    Slog.i("SecurityLogMonitor", "Resumed.");
                    try {
                        securityLogMonitor.notifyDeviceOwnerOrProfileOwnerIfNeeded(false);
                        securityLogMonitor = securityLogMonitor;
                    } catch (InterruptedException e) {
                        Log.w("SecurityLogMonitor", "Thread interrupted.", e);
                        securityLogMonitor = e;
                    }
                } else {
                    Log.d("SecurityLogMonitor", "Attempted to resume, but logging is not paused.");
                }
            } finally {
                ((ReentrantLock) securityLogMonitor.mLock).unlock();
            }
        }
        if ((!z && devicePolicyManagerService.mOwners.hasDeviceOwner()) || (networkLogger = devicePolicyManagerService.mNetworkLogger) == null || (networkLoggingHandler = networkLogger.mNetworkLoggingHandler) == null) {
            return;
        }
        synchronized (networkLoggingHandler) {
            try {
                if (!networkLoggingHandler.mPaused) {
                    Slog.d("NetworkLoggingHandler", "Attempted to resume network logging, but logging is not paused.");
                    return;
                }
                Slog.d("NetworkLoggingHandler", "Resumed network logging. Current batch=" + networkLoggingHandler.mCurrentBatchToken + ", LastRetrievedBatch=" + networkLoggingHandler.mLastRetrievedBatchToken);
                networkLoggingHandler.mPaused = false;
                if (networkLoggingHandler.mBatches.size() <= 0 || networkLoggingHandler.mLastRetrievedBatchToken == networkLoggingHandler.mCurrentBatchToken) {
                    bundle = null;
                } else {
                    networkLoggingHandler.scheduleBatchFinalization();
                    bundle = networkLoggingHandler.buildAdminMessageLocked();
                }
                if (bundle != null) {
                    networkLoggingHandler.notifyDeviceOwnerOrProfileOwner(bundle);
                }
            } finally {
            }
        }
    }
}
