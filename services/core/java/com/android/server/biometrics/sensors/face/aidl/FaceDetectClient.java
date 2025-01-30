package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.face.FaceAuthenticateOptions;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.DetectionConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceDetectClient extends AcquisitionClient implements DetectionConsumer {
  public ICancellationSignal mCancellationSignal;
  public final boolean mIsStrongBiometric;
  public final FaceAuthenticateOptions mOptions;
  public SensorPrivacyManager mSensorPrivacyManager;

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public int getProtoEnum() {
    return 13;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean interruptsPrecedingClients() {
    return true;
  }

  public FaceDetectClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FaceAuthenticateOptions faceAuthenticateOptions,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      boolean z) {
    this(
        context,
        supplier,
        iBinder,
        j,
        clientMonitorCallbackConverter,
        faceAuthenticateOptions,
        biometricLogger,
        biometricContext,
        z,
        (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class));
  }

  public FaceDetectClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FaceAuthenticateOptions faceAuthenticateOptions,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      boolean z,
      SensorPrivacyManager sensorPrivacyManager) {
    super(
        context,
        supplier,
        iBinder,
        clientMonitorCallbackConverter,
        faceAuthenticateOptions.getUserId(),
        faceAuthenticateOptions.getOpPackageName(),
        0,
        faceAuthenticateOptions.getSensorId(),
        false,
        biometricLogger,
        biometricContext);
    setRequestId(j);
    this.mIsStrongBiometric = z;
    this.mSensorPrivacyManager = sensorPrivacyManager;
    this.mOptions = faceAuthenticateOptions;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    startHalOperation();
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void stopHalOperation() {
    unsubscribeBiometricContext();
    ICancellationSignal iCancellationSignal = this.mCancellationSignal;
    if (iCancellationSignal != null) {
      try {
        iCancellationSignal.cancel();
      } catch (RemoteException e) {
        Slog.e("FaceDetectClient", "Remote exception", e);
        this.mCallback.onClientFinished(this, false);
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    SensorPrivacyManager sensorPrivacyManager = this.mSensorPrivacyManager;
    if (sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
      onError(1, 0);
      this.mCallback.onClientFinished(this, false);
      return;
    }
    try {
      this.mCancellationSignal = doDetectInteraction();
    } catch (RemoteException e) {
      Slog.e("FaceDetectClient", "Remote exception when requesting face detect", e);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public final ICancellationSignal doDetectInteraction() {
    final AidlSession aidlSession = (AidlSession) getFreshDaemon();
    if (aidlSession.hasContextMethods()) {
      OperationContextExt operationContext = getOperationContext();
      ICancellationSignal detectInteractionWithContext =
          aidlSession
              .getSession()
              .detectInteractionWithContext(operationContext.toAidlContext(this.mOptions));
      getBiometricContext()
          .subscribe(
              operationContext,
              new Consumer() { // from class:
                               // com.android.server.biometrics.sensors.face.aidl.FaceDetectClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  FaceDetectClient.lambda$doDetectInteraction$0(
                      AidlSession.this, (OperationContext) obj);
                }
              });
      return detectInteractionWithContext;
    }
    return aidlSession.getSession().detectInteraction();
  }

  public static /* synthetic */ void lambda$doDetectInteraction$0(
      AidlSession aidlSession, OperationContext operationContext) {
    try {
      aidlSession.getSession().onContextChanged(operationContext);
    } catch (RemoteException e) {
      Slog.e("FaceDetectClient", "Unable to notify context changed", e);
    }
  }

  public void onInteractionDetected() {
    vibrateSuccess();
    try {
      getListener().onDetected(getSensorId(), getTargetUserId(), this.mIsStrongBiometric);
      this.mCallback.onClientFinished(this, true);
    } catch (RemoteException e) {
      Slog.e("FaceDetectClient", "Remote exception when sending onDetected", e);
      this.mCallback.onClientFinished(this, false);
    }
  }
}
