package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.face.ISession;
import android.hardware.face.IFaceServiceReceiver;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FaceGetFeatureClient extends HalClientMonitor implements ErrorConsumer {
    public final int mFeature;
    public final int mUserId;

    public FaceGetFeatureClient(Context context, Supplier supplier, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, int i3) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext, false);
        this.mUserId = i;
        this.mFeature = i3;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 9;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        try {
            int[] iArr = new int[0];
            boolean[] zArr = new boolean[0];
            IFaceServiceReceiver iFaceServiceReceiver = this.mListener.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onFeatureGet(false, iArr, zArr);
            }
        } catch (RemoteException e) {
            Slog.e("FaceGetFeatureClient", "Remote exception", e);
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
            ISession iSession = ((AidlSession) this.mLazyDaemon.get()).mSession;
            if (iSession instanceof HidlToAidlSessionAdapter) {
                ((HidlToAidlSessionAdapter) iSession).mFeature = this.mFeature;
            }
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().daemonGetFeatures();
            } else {
                iSession.getFeatures();
            }
        } catch (RemoteException e) {
            Slog.e("FaceGetFeatureClient", "Unable to getFeature", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        this.mCallback.onClientFinished(this, false);
    }
}
