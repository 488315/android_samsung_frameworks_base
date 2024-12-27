package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StartUserClient;

public final class FaceStartUserClient extends StartUserClient {
    public final ISessionCallback mSessionCallback;

    public FaceStartUserClient(
            Context context,
            Sensor$$ExternalSyntheticLambda4 sensor$$ExternalSyntheticLambda4,
            IBinder iBinder,
            int i,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            AidlResponseHandler aidlResponseHandler,
            Sensor$$ExternalSyntheticLambda3 sensor$$ExternalSyntheticLambda3) {
        super(
                context,
                sensor$$ExternalSyntheticLambda4,
                iBinder,
                i,
                i2,
                biometricLogger,
                biometricContext,
                sensor$$ExternalSyntheticLambda3);
        this.mSessionCallback = aidlResponseHandler;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            SemFaceServiceExImpl.getInstance().mUserId = this.mTargetUserId;
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().mISessionCallback = this.mSessionCallback;
                int interfaceVersion = ((IFace) this.mLazyDaemon.get()).getInterfaceVersion();
                if (SemFaceServiceExImpl.getInstance().mISession != null) {
                    Binder.allowBlocking(SemFaceServiceExImpl.getInstance().mISession.asBinder());
                    this.mUserStartedCallback.onUserStarted(
                            this.mTargetUserId,
                            interfaceVersion,
                            SemFaceServiceExImpl.getInstance().mISession);
                }
                SemFaceServiceExImpl.getInstance().daemonEnumerateUser();
            } else {
                IFace iFace = (IFace) this.mLazyDaemon.get();
                int interfaceVersion2 = iFace.getInterfaceVersion();
                ISession createSession =
                        iFace.createSession(
                                this.mSensorId, this.mTargetUserId, this.mSessionCallback);
                if (createSession == null) {
                    Slog.e("FaceStartUserClient", "createSession() is null");
                    getCallback().onClientFinished(this, false);
                    return;
                } else {
                    Binder.allowBlocking(createSession.asBinder());
                    this.mUserStartedCallback.onUserStarted(
                            this.mTargetUserId, interfaceVersion2, createSession);
                }
            }
            getCallback().onClientFinished(this, true);
        } catch (RemoteException e) {
            Slog.e("FaceStartUserClient", "Remote exception", e);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
