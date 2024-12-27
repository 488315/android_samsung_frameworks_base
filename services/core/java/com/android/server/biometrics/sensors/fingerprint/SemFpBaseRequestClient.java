package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession;

import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;

import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;
import vendor.samsung.hardware.biometrics.fingerprint.SehResult;

import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class SemFpBaseRequestClient extends HalClientMonitor {
    public final int mCommand;
    public final byte[] mInputBuffer;
    public final boolean mIsAsyncOperation;
    public final byte[] mOutputBuffer;
    public final int mParam;
    public int mRequestResult;
    public final boolean mUseScheduler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientMonitorCallback {
        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {}

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientStarted(BaseClientMonitor baseClientMonitor) {}
    }

    public SemFpBaseRequestClient(
            Context context,
            BiometricContext biometricContext,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            int i2,
            String str,
            boolean z,
            int i3,
            int i4,
            byte[] bArr,
            byte[] bArr2) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i2,
                str,
                0,
                i,
                new BiometricLogger(context, 1, 0, 0, null),
                biometricContext,
                false);
        this.mUseScheduler = z;
        this.mCommand = i3;
        this.mParam = i4;
        this.mRequestResult = -1;
        this.mOutputBuffer = bArr2;
        if (clientMonitorCallbackConverter != null) {
            this.mIsAsyncOperation = true;
        }
        if (bArr == null || bArr.length == 0) {
            this.mInputBuffer = new byte[0];
        } else {
            this.mInputBuffer = (byte[]) bArr.clone();
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 0;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        int i = this.mCommand;
        return i == 3 || i == 4 || i == 5 || i == 7 || i == 9;
    }

    public void onRequestResult(int i) {
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
        if (clientMonitorCallbackConverter == null) {
            return;
        }
        try {
            ISemFingerprintRequestCallback iSemFingerprintRequestCallback =
                    clientMonitorCallbackConverter.mFingerprintRequestReceiver;
            if (iSemFingerprintRequestCallback != null) {
                iSemFingerprintRequestCallback.onResult(i);
            }
        } catch (RemoteException e) {
            Slog.w(
                    "FingerprintRequestClient",
                    "Failed to invoke sendEvent with onRequestResult",
                    e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public void setRequestResultForTest(int i) {
        this.mRequestResult = i;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        long currentTimeMillis = System.currentTimeMillis();
        startHalOperation();
        StringBuilder sb = new StringBuilder("request[");
        sb.append(this.mCommand);
        sb.append("] FP_FINISH (");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append("ms) RESULT: ");
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                sb, this.mRequestResult, "FingerprintRequestClient");
        boolean z = this.mRequestResult >= 0;
        if (this.mUseScheduler && z && this.mIsAsyncOperation) {
            return;
        }
        this.mCallback.onClientFinished(this, z);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        byte[] bArr;
        byte[] bArr2;
        Object obj = this.mLazyDaemon.get();
        ISehFingerprint iSehFingerprint = null;
        if (obj instanceof AidlSession) {
            int i = this.mSensorId;
            Function function = ((AidlSession) obj).mLazySehFingerprint;
            if (function != null) {
                iSehFingerprint = (ISehFingerprint) function.apply(Integer.valueOf(i));
            }
        } else if (obj instanceof ISehFingerprint) {
            iSehFingerprint = (ISehFingerprint) obj;
        }
        if (iSehFingerprint == null) {
            if (this.mUseScheduler) {
                unableToStart();
                return;
            } else {
                this.mRequestResult = 0;
                return;
            }
        }
        try {
            int i2 = this.mSensorId;
            int i3 = this.mCommand;
            int i4 = this.mParam;
            byte[] bArr3 = this.mInputBuffer;
            if (bArr3 == null) {
                bArr3 = new byte[0];
            }
            SehResult sehRequest = iSehFingerprint.sehRequest(i2, i3, i4, bArr3);
            if (sehRequest == null) {
                return;
            }
            int i5 = sehRequest.retValue;
            this.mRequestResult = i5;
            if (i5 == 0
                    && (bArr = sehRequest.data) != null
                    && bArr.length != 0
                    && (bArr2 = this.mOutputBuffer) != null
                    && bArr2.length != 0) {
                int min = Math.min(bArr.length, bArr2.length);
                System.arraycopy(bArr, 0, this.mOutputBuffer, 0, min);
                this.mRequestResult = min;
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintRequestClient", "Remote exception", e);
        }
    }

    public final int startWithoutScheduler() {
        if (this.mUseScheduler) {
            Slog.w(
                    "FingerprintRequestClient",
                    "startWithoutScheduler: It must be started by the scheduler!!");
            throw new UnsupportedOperationException("useScheduler option is set");
        }
        start(new AnonymousClass1());
        return this.mRequestResult;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        this.mRequestResult = -1;
        this.mCallback.onClientFinished(this, false);
    }
}
