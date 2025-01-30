package com.android.server.biometrics.sensors.face.hidl;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.Surface;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class FaceEnrollClient extends EnrollClient {
  public final Bundle mBundle;
  public final int[] mDisabledFeatures;
  public final int[] mEnrollIgnoreList;
  public final int[] mEnrollIgnoreListVendor;

  public abstract int daemonEnroll(ArrayList arrayList, int i, ArrayList arrayList2);

  public abstract int daemonEnrollCancel();

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
      BiometricContext biometricContext) {
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
    setRequestId(j);
    this.mDisabledFeatures = Arrays.copyOf(iArr, iArr.length);
    this.mEnrollIgnoreList =
        getContext().getResources().getIntArray(R.array.fingerprint_error_vendor);
    this.mEnrollIgnoreListVendor =
        getContext().getResources().getIntArray(R.array.maps_starting_zoom);
    this.mBundle = SemFaceUtils.getBundle();
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public ClientMonitorCallback wrapCallbackForStart(ClientMonitorCallback clientMonitorCallback) {
    return new ClientMonitorCompositeCallback(
        getLogger().getAmbientLightProbe(true), clientMonitorCallback);
  }

  @Override // com.android.server.biometrics.sensors.EnrollClient
  public boolean hasReachedEnrollmentLimit() {
    if (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).size()
        < (SemBiometricFeature.FEATURE_JDM_HAL ? 1 : 2)) {
      return false;
    }
    Slog.w("FaceEnrollClient", "Too many faces registered, user: " + getTargetUserId());
    return true;
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void onAcquired(int i, int i2) {
    boolean listContains;
    if (i == 22) {
      listContains = Utils.listContains(this.mEnrollIgnoreListVendor, i2);
    } else {
      listContains = Utils.listContains(this.mEnrollIgnoreList, i);
    }
    onAcquiredInternal(i, i2, !listContains);
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    ArrayList arrayList = new ArrayList();
    for (byte b : this.mHardwareAuthToken) {
      arrayList.add(Byte.valueOf(b));
    }
    ArrayList arrayList2 = new ArrayList();
    for (int i : this.mDisabledFeatures) {
      arrayList2.add(Integer.valueOf(i));
    }
    try {
      if (daemonEnroll(arrayList, this.mTimeoutSec, arrayList2) != 0) {
        onError(2, 0);
        this.mCallback.onClientFinished(this, false);
      }
    } catch (RemoteException e) {
      Slog.e("FaceEnrollClient", "Remote exception when requesting enroll", e);
      onError(2, 0);
      this.mCallback.onClientFinished(this, false);
    }
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void stopHalOperation() {
    try {
      daemonEnrollCancel();
    } catch (RemoteException e) {
      Slog.e("FaceEnrollClient", "Remote exception when requesting cancel", e);
      onError(1, 0);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public void onImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
    getListener().onImageProcessed(bArr, i, i2, i3, i4, bundle);
  }
}
