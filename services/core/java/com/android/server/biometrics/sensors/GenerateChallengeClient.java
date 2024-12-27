package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class GenerateChallengeClient extends HalClientMonitor {
    public GenerateChallengeClient(Context context, Supplier supplier, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 10;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        try {
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mSensorId;
            int i2 = this.mTargetUserId;
            IFaceServiceReceiver iFaceServiceReceiver = clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onChallengeGenerated(i, i2, 0L);
            } else {
                IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                if (iFingerprintServiceReceiver != null) {
                    iFingerprintServiceReceiver.onChallengeGenerated(i, i2, 0L);
                }
            }
        } catch (RemoteException e) {
            Slog.e("GenerateChallengeClient", "Unable to send error", e);
        }
    }
}
