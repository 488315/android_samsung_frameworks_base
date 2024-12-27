package com.android.server.backup.transport;

import android.provider.DeviceConfig;
import android.util.Slog;

import com.android.internal.backup.ITransportStatusCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TransportStatusCallback extends ITransportStatusCallback.Stub {
    public boolean mHasCompletedOperation;
    public int mOperationStatus;
    public final long mOperationTimeout;

    public TransportStatusCallback() {
        this.mOperationStatus = 0;
        this.mHasCompletedOperation = false;
        this.mOperationTimeout =
                DeviceConfig.getLong(
                        "backup_and_restore", "backup_transport_callback_timeout_millis", 300000L);
    }

    public TransportStatusCallback(int i) {
        this.mOperationStatus = 0;
        this.mHasCompletedOperation = false;
        this.mOperationTimeout = i;
    }

    public final synchronized int getOperationStatus() {
        if (this.mHasCompletedOperation) {
            return this.mOperationStatus;
        }
        long j = this.mOperationTimeout;
        while (!this.mHasCompletedOperation && j > 0) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                wait(j);
                if (this.mHasCompletedOperation) {
                    return this.mOperationStatus;
                }
                j -= System.currentTimeMillis() - currentTimeMillis;
            } catch (InterruptedException e) {
                Slog.w(
                        "TransportStatusCallback",
                        "Couldn't get operation status from transport: ",
                        e);
            }
        }
        Slog.w("TransportStatusCallback", "Couldn't get operation status from transport");
        return -1000;
    }

    public final synchronized void onOperationComplete() {
        onOperationCompleteWithStatus(0);
    }

    public final synchronized void onOperationCompleteWithStatus(int i) {
        this.mHasCompletedOperation = true;
        this.mOperationStatus = i;
        notifyAll();
    }
}
