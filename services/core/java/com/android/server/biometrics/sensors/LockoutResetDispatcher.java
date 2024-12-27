package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LockoutResetDispatcher implements IBinder.DeathRecipient {
    final ConcurrentLinkedQueue mClientCallbacks = new ConcurrentLinkedQueue();
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientCallback {
        public final IBiometricServiceLockoutResetCallback mCallback;
        public final String mOpPackageName;
        public final PowerManager.WakeLock mWakeLock;

        public ClientCallback(
                Context context,
                IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback,
                String str) {
            PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
            this.mOpPackageName = str;
            this.mCallback = iBiometricServiceLockoutResetCallback;
            this.mWakeLock = powerManager.newWakeLock(1, "LockoutResetMonitor:SendLockoutReset");
        }

        public final void releaseWakelock() {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
    }

    public LockoutResetDispatcher(Context context) {
        this.mContext = context;
    }

    public final synchronized void addCallback(
            IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback,
            String str) {
        if (iBiometricServiceLockoutResetCallback == null) {
            Slog.w("LockoutResetTracker", "Callback from : " + str + " is null");
            return;
        }
        this.mClientCallbacks.add(
                new ClientCallback(this.mContext, iBiometricServiceLockoutResetCallback, str));
        try {
            iBiometricServiceLockoutResetCallback.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
            Slog.e("LockoutResetTracker", "Failed to link to death", e);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {}

    @Override // android.os.IBinder.DeathRecipient
    public final synchronized void binderDied(IBinder iBinder) {
        Slog.e("LockoutResetTracker", "Callback binder died: " + iBinder);
        Iterator it = this.mClientCallbacks.iterator();
        while (it.hasNext()) {
            ClientCallback clientCallback = (ClientCallback) it.next();
            if (clientCallback.mCallback.asBinder().equals(iBinder)) {
                Slog.e(
                        "LockoutResetTracker",
                        "Removing dead callback for: " + clientCallback.mOpPackageName);
                clientCallback.releaseWakelock();
                it.remove();
            }
        }
    }

    public final synchronized void notifyLockoutResetCallbacks(int i) {
        Iterator it = this.mClientCallbacks.iterator();
        while (it.hasNext()) {
            final ClientCallback clientCallback = (ClientCallback) it.next();
            if (clientCallback.mCallback != null) {
                try {
                    clientCallback.mWakeLock.acquire(2000L);
                    clientCallback.mCallback.onLockoutReset(
                            i,
                            new IRemoteCallback
                                    .Stub() { // from class:
                                              // com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback.1
                                public final void sendResult(Bundle bundle) {
                                    ClientCallback.this.releaseWakelock();
                                }
                            });
                } catch (RemoteException e) {
                    Slog.w("LockoutResetTracker", "Failed to invoke onLockoutReset: ", e);
                    clientCallback.releaseWakelock();
                }
            }
        }
    }
}
