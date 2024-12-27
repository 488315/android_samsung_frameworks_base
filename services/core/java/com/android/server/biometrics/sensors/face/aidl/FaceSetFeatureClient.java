package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.HalClientMonitor;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FaceSetFeatureClient extends HalClientMonitor implements ErrorConsumer {
    public final boolean mEnabled;
    public final int mFeature;
    public final HardwareAuthToken mHardwareAuthToken;

    public FaceSetFeatureClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            String str,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            int i3,
            boolean z,
            byte[] bArr) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                0,
                i2,
                biometricLogger,
                biometricContext,
                false);
        this.mFeature = i3;
        this.mEnabled = z;
        this.mHardwareAuthToken = HardwareAuthTokenUtils.toHardwareAuthToken(bArr);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 8;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        try {
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i3 = this.mFeature;
            IFaceServiceReceiver iFaceServiceReceiver =
                    clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onFeatureSet(false, i3);
            }
        } catch (RemoteException e) {
            Slog.e("FaceSetFeatureClient", "Remote exception", e);
        }
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((AidlSession) this.mLazyDaemon.get())
                    .mSession.setFeature(
                            this.mHardwareAuthToken,
                            AidlConversionUtils.convertFrameworkToAidlFeature(this.mFeature),
                            this.mEnabled);
            Slog.w(
                    "FaceSetFeatureClient",
                    "setFeature FINISH f=" + this.mFeature + ", enabled=" + this.mEnabled);
        } catch (RemoteException | IllegalArgumentException e) {
            Slog.e(
                    "FaceSetFeatureClient",
                    "Unable to set feature: " + this.mFeature + " to enabled: " + this.mEnabled,
                    e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        try {
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mFeature;
            IFaceServiceReceiver iFaceServiceReceiver =
                    clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onFeatureSet(false, i);
            }
        } catch (RemoteException e) {
            Slog.e("FaceSetFeatureClient", "Unable to send error", e);
        }
    }
}
