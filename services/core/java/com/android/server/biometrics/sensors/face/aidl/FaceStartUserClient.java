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
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceStartUserClient extends StartUserClient {
  public final ISessionCallback mSessionCallback;

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void unableToStart() {}

  public FaceStartUserClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      int i,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      ISessionCallback iSessionCallback,
      StartUserClient.UserStartedCallback userStartedCallback) {
    super(
        context, supplier, iBinder, i, i2, biometricLogger, biometricContext, userStartedCallback);
    this.mSessionCallback = iSessionCallback;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    startHalOperation();
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    try {
      SemFaceServiceExImpl.getInstance().setUserId(getTargetUserId());
      if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
        SemFaceServiceExImpl.getInstance().setISessionCallback(this.mSessionCallback);
        int interfaceVersion = ((IFace) getFreshDaemon()).getInterfaceVersion();
        if (SemFaceServiceExImpl.getInstance().getISession() != null) {
          Binder.allowBlocking(SemFaceServiceExImpl.getInstance().getISession().asBinder());
          this.mUserStartedCallback.onUserStarted(
              getTargetUserId(),
              SemFaceServiceExImpl.getInstance().getISession(),
              interfaceVersion);
        }
        SemFaceServiceExImpl.getInstance().daemonEnumerateUser();
      } else {
        IFace iFace = (IFace) getFreshDaemon();
        int interfaceVersion2 = iFace.getInterfaceVersion();
        ISession createSession =
            iFace.createSession(getSensorId(), getTargetUserId(), this.mSessionCallback);
        if (createSession == null) {
          Slog.e("FaceStartUserClient", "createSession() is null");
          getCallback().onClientFinished(this, false);
          return;
        } else {
          Binder.allowBlocking(createSession.asBinder());
          this.mUserStartedCallback.onUserStarted(
              getTargetUserId(), createSession, interfaceVersion2);
        }
      }
      getCallback().onClientFinished(this, true);
    } catch (RemoteException e) {
      Slog.e("FaceStartUserClient", "Remote exception", e);
      getCallback().onClientFinished(this, false);
    }
  }
}
