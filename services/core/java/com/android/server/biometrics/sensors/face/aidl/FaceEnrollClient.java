package com.android.server.biometrics.sensors.face.aidl;

import android.R;
import android.content.Context;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.common.NativeHandle;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.Surface;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.face.FaceService;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceEnrollClient extends EnrollClient {
  public ICancellationSignal mCancellationSignal;
  public final boolean mDebugConsent;
  public final int[] mDisabledFeatures;
  public final int[] mEnrollIgnoreList;
  public final int[] mEnrollIgnoreListVendor;
  public NativeHandle mHwPreviewHandle;
  public final int mMaxTemplatesPerUser;
  public android.os.NativeHandle mOsPreviewHandle;
  public final ClientMonitorCallback mPreviewHandleDeleterCallback;
  public final Surface mPreviewSurface;
  public final ClientMonitorCallback mSemCancelDaemonCallback;

  public FaceEnrollClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      int i,
      byte[] bArr,
      String str,
      long j,
      BiometricUtils biometricUtils,
      int[] iArr,
      int i2,
      Surface surface,
      int i3,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      int i4,
      boolean z) {
    super(
        context,
        supplier,
        iBinder,
        clientMonitorCallbackConverter,
        i,
        bArr,
        str,
        biometricUtils,
        i2,
        i3,
        false,
        biometricLogger,
        biometricContext);
    this.mPreviewHandleDeleterCallback =
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.1
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {}

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z2) {
            FaceEnrollClient.this.releaseSurfaceHandlesIfNeeded();
          }
        };
    this.mSemCancelDaemonCallback =
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.2
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {}

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z2) {
            FaceEnrollClient.this
                .getServiceExtImpl()
                .daemonCancel(FaceEnrollClient.this.mCancellationSignal, true);
          }
        };
    setRequestId(j);
    this.mEnrollIgnoreList =
        getContext().getResources().getIntArray(R.array.fingerprint_error_vendor);
    this.mEnrollIgnoreListVendor =
        getContext().getResources().getIntArray(R.array.maps_starting_zoom);
    this.mMaxTemplatesPerUser = i4;
    this.mDebugConsent = z;
    this.mDisabledFeatures = iArr;
    this.mPreviewSurface = surface;
  }

  @Override // com.android.server.biometrics.sensors.EnrollClient,
            // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    BiometricNotificationUtils.cancelReEnrollNotification(getContext());
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public ClientMonitorCallback wrapCallbackForStart(ClientMonitorCallback clientMonitorCallback) {
    return new ClientMonitorCompositeCallback(
        this.mPreviewHandleDeleterCallback,
        this.mSemCancelDaemonCallback,
        getLogger().getAmbientLightProbe(true),
        clientMonitorCallback);
  }

  @Override // com.android.server.biometrics.sensors.EnrollClient
  public boolean hasReachedEnrollmentLimit() {
    return FaceUtils.getInstance(getSensorId())
            .getBiometricsForUser(getContext(), getTargetUserId())
            .size()
        >= this.mMaxTemplatesPerUser;
  }

  public final boolean shouldSendAcquiredMessage(int i, int i2) {
    if (i == 22) {
      if (!Utils.listContains(this.mEnrollIgnoreListVendor, i2)) {
        return true;
      }
    } else if (!Utils.listContains(this.mEnrollIgnoreList, i)) {
      return true;
    }
    return false;
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void onAcquired(int i, int i2) {
    onAcquiredInternal(i, i2, shouldSendAcquiredMessage(i, i2));
  }

  public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) {
    int acquiredInfo = faceEnrollFrame.getData().getAcquiredInfo();
    int vendorCode = faceEnrollFrame.getData().getVendorCode();
    onAcquiredInternal(acquiredInfo, vendorCode, false);
    if (!shouldSendAcquiredMessage(acquiredInfo, vendorCode) || getListener() == null) {
      return;
    }
    try {
      getListener().onEnrollmentFrame(faceEnrollFrame);
    } catch (RemoteException e) {
      Slog.w("FaceEnrollClient", "Failed to send enrollment frame", e);
      this.mCallback.onClientFinished(this, false);
    }
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    obtainSurfaceHandlesIfNeeded();
    try {
      ArrayList arrayList = new ArrayList();
      if (this.mDebugConsent) {
        arrayList.add((byte) 2);
      }
      boolean z = true;
      for (int i : this.mDisabledFeatures) {
        if (AidlConversionUtils.convertFrameworkToAidlFeature(i) == 1) {
          z = false;
        }
      }
      if (z) {
        arrayList.add((byte) 1);
      }
      byte[] bArr = new byte[arrayList.size()];
      for (int i2 = 0; i2 < arrayList.size(); i2++) {
        bArr[i2] = ((Byte) arrayList.get(i2)).byteValue();
      }
      getServiceExtImpl().startOperation(1);
      Slog.w("FaceEnrollClient", "enroll START");
      long currentTimeMillis = System.currentTimeMillis();
      if (getServiceExtImpl().isUsingSehAPI()) {
        this.mCancellationSignal =
            getServiceExtImpl()
                .daemonEnroll(
                    HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken),
                    (byte) 0,
                    bArr,
                    this.mHwPreviewHandle);
      } else {
        this.mCancellationSignal = doEnroll(bArr);
      }
      Slog.w(
          "FaceEnrollClient",
          "enroll FINISH ("
              + (System.currentTimeMillis() - currentTimeMillis)
              + "ms) RESULT: "
              + this.mCancellationSignal);
    } catch (RemoteException | IllegalArgumentException e) {
      Slog.e("FaceEnrollClient", "Exception when requesting enroll", e);
      onError(2, 0);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public final ICancellationSignal doEnroll(byte[] bArr) {
    final AidlSession aidlSession = (AidlSession) getFreshDaemon();
    HardwareAuthToken hardwareAuthToken =
        HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
    if (aidlSession.hasContextMethods()) {
      OperationContextExt operationContext = getOperationContext();
      ICancellationSignal enrollWithContext =
          aidlSession
              .getSession()
              .enrollWithContext(
                  hardwareAuthToken,
                  (byte) 0,
                  bArr,
                  this.mHwPreviewHandle,
                  operationContext.toAidlContext());
      getBiometricContext()
          .subscribe(
              operationContext,
              new Consumer() { // from class:
                               // com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  FaceEnrollClient.lambda$doEnroll$0(AidlSession.this, (OperationContext) obj);
                }
              });
      return enrollWithContext;
    }
    return aidlSession
        .getSession()
        .enroll(hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle);
  }

  public static /* synthetic */ void lambda$doEnroll$0(
      AidlSession aidlSession, OperationContext operationContext) {
    try {
      aidlSession.getSession().onContextChanged(operationContext);
    } catch (RemoteException e) {
      Slog.e("FaceEnrollClient", "Unable to notify context changed", e);
    }
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void stopHalOperation() {
    unsubscribeBiometricContext();
    if (this.mCancellationSignal != null) {
      try {
        getServiceExtImpl().daemonCancel(this.mCancellationSignal, false);
      } catch (RemoteException e) {
        Slog.e("FaceEnrollClient", "Remote exception when requesting cancel", e);
        onError(1, 0);
        this.mCallback.onClientFinished(this, false);
      }
    }
  }

  public final void obtainSurfaceHandlesIfNeeded() {
    Surface surface = this.mPreviewSurface;
    if (surface != null) {
      android.os.NativeHandle acquireSurfaceHandle = FaceService.acquireSurfaceHandle(surface);
      this.mOsPreviewHandle = acquireSurfaceHandle;
      try {
        this.mHwPreviewHandle = AidlNativeHandleUtils.dup(acquireSurfaceHandle);
        Slog.v("FaceEnrollClient", "Obtained handles for the preview surface.");
      } catch (IOException e) {
        this.mHwPreviewHandle = null;
        Slog.e("FaceEnrollClient", "Failed to dup mOsPreviewHandle", e);
      }
    }
  }

  public final void releaseSurfaceHandlesIfNeeded() {
    if (this.mPreviewSurface != null && this.mHwPreviewHandle == null) {
      Slog.w(
          "FaceEnrollClient", "mHwPreviewHandle is null even though mPreviewSurface is not null.");
    }
    if (this.mHwPreviewHandle != null) {
      try {
        Slog.v("FaceEnrollClient", "Closing mHwPreviewHandle");
        AidlNativeHandleUtils.close(this.mHwPreviewHandle);
      } catch (IOException e) {
        Slog.e("FaceEnrollClient", "Failed to close mPreviewSurface", e);
      }
      this.mHwPreviewHandle = null;
    }
    if (this.mOsPreviewHandle != null) {
      Slog.v("FaceEnrollClient", "Releasing mOsPreviewHandle");
      FaceService.releaseSurfaceHandle(this.mOsPreviewHandle);
      this.mOsPreviewHandle = null;
    }
    if (this.mPreviewSurface != null) {
      Slog.v("FaceEnrollClient", "Releasing mPreviewSurface");
      this.mPreviewSurface.release();
    }
  }

  public SemFaceServiceExImpl getServiceExtImpl() {
    return SemFaceServiceExImpl.getInstance();
  }
}
