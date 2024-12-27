package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.face.aidl.FaceDetectClient;

import java.util.NoSuchElementException;

public abstract class BaseClientMonitor implements IBinder.DeathRecipient {
    public static int sCount;
    public final BiometricContext mBiometricContext;
    public final Context mContext;
    public final int mCookie;
    public final int mHashID;
    public ClientMonitorCallbackConverter mListener;
    public final BiometricLogger mLogger;
    public final String mOwner;
    public long mRequestId;
    public final int mSensorId;
    public final int mSequentialId;
    public final int mTargetUserId;
    public IBinder mToken;
    public boolean mAlreadyDone = false;
    public ClientMonitorCallback mCallback = new AnonymousClass1();

    /* renamed from: com.android.server.biometrics.sensors.BaseClientMonitor$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientMonitorCallback {
        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            Slog.e(
                    "BaseClientMonitor",
                    "mCallback onClientFinished: called before set (should not happen)");
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Slog.e(
                    "BaseClientMonitor",
                    "mCallback onClientStarted: called before set (should not happen)");
        }
    }

    public BaseClientMonitor(
            Context context,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            String str,
            int i2,
            int i3,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext) {
        int i4 = sCount;
        sCount = i4 + 1;
        this.mSequentialId = i4;
        this.mContext = context;
        this.mToken = iBinder;
        this.mRequestId = -1L;
        this.mListener =
                clientMonitorCallbackConverter == null
                        ? new ClientMonitorCallbackConverter(
                                (IBiometricSensorReceiver) new IBiometricSensorReceiver.Default())
                        : clientMonitorCallbackConverter;
        this.mTargetUserId = i;
        this.mOwner = str;
        this.mCookie = i2;
        this.mSensorId = i3;
        this.mLogger = biometricLogger;
        this.mBiometricContext = biometricContext;
        this.mHashID = hashCode();
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.w("BaseClientMonitor", "caught remote exception in linkToDeath: ", e);
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        binderDiedInternal(true);
    }

    public final void binderDiedInternal(final boolean z) {
        int i = this.mLogger.mStatsModality;
        BiometricHandlerProvider biometricHandlerProvider =
                BiometricHandlerProvider.sBiometricHandlerProvider;
        (i == 1
                        ? biometricHandlerProvider.getFingerprintHandler()
                        : i == 4
                                ? biometricHandlerProvider.getFaceHandler()
                                : biometricHandlerProvider.getBiometricCallbackHandler())
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.biometrics.sensors.BaseClientMonitor$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BaseClientMonitor baseClientMonitor = BaseClientMonitor.this;
                                boolean z2 = z;
                                baseClientMonitor.getClass();
                                Slog.e(
                                        "BaseClientMonitor",
                                        "Binder died, operation: " + baseClientMonitor);
                                if (baseClientMonitor.mAlreadyDone) {
                                    Slog.w(
                                            "BaseClientMonitor",
                                            "Binder died but client is finished, ignoring");
                                    return;
                                }
                                if (baseClientMonitor.isInterruptable()) {
                                    Slog.e("BaseClientMonitor", "Binder died, cancelling client");
                                    baseClientMonitor.cancel();
                                }
                                baseClientMonitor.mToken = null;
                                if (z2) {
                                    baseClientMonitor.mListener =
                                            new ClientMonitorCallbackConverter(
                                                    (IBiometricSensorReceiver)
                                                            new IBiometricSensorReceiver.Default());
                                }
                            }
                        });
    }

    public void cancel() {
        cancelWithoutStarting(this.mCallback);
    }

    public void cancelWithoutStarting(ClientMonitorCallback clientMonitorCallback) {
        Slog.d("BaseClientMonitor", "cancelWithoutStarting: " + this);
        try {
            this.mListener.onError(this.mSensorId, this.mCookie, 5, 0);
        } catch (RemoteException e) {
            Slog.w("BaseClientMonitor", "Failed to invoke sendError", e);
        }
        clientMonitorCallback.onClientFinished(this, true);
    }

    public void destroy() {
        this.mAlreadyDone = true;
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
                Slog.e("BaseClientMonitor", "destroy(): " + this + ":", new Exception("here"));
            }
            this.mToken = null;
        }
    }

    public ClientMonitorCallback getCallback() {
        return this.mCallback;
    }

    public abstract int getProtoEnum();

    public boolean interruptsPrecedingClients() {
        return this instanceof FaceDetectClient;
    }

    public boolean isCryptoOperation() {
        return false;
    }

    public boolean isInterruptable() {
        return false;
    }

    public final void setRequestId(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("request id must be positive");
        }
        this.mRequestId = j;
    }

    public void start(ClientMonitorCallback clientMonitorCallback) {
        ClientMonitorCallback wrapCallbackForStart = wrapCallbackForStart(clientMonitorCallback);
        this.mCallback = wrapCallbackForStart;
        wrapCallbackForStart.onClientStarted(this);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{[");
        sb.append(this.mSequentialId);
        sb.append("] ");
        sb.append(getClass().getName());
        sb.append(", proto=");
        sb.append(getProtoEnum());
        sb.append(", owner=");
        sb.append(this.mOwner);
        sb.append(", cookie=");
        sb.append(this.mCookie);
        sb.append(", requestId=");
        sb.append(this.mRequestId);
        sb.append(", userId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mTargetUserId, sb, "}");
    }

    public ClientMonitorCallback wrapCallbackForStart(ClientMonitorCallback clientMonitorCallback) {
        return clientMonitorCallback;
    }
}
