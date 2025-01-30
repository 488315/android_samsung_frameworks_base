package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.HalClientMonitor;
import java.util.ArrayList;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;
import vendor.samsung.hardware.biometrics.fingerprint.SehResult;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* loaded from: classes.dex */
public class SemFpBaseRequestClient extends HalClientMonitor implements SemFpRequestCommands {
  public final int mCommand;
  public byte[] mInputBuffer;
  public final byte[] mOutputBuffer;
  public final int mParam;
  public int mRequestResult;
  public final boolean mUseScheduler;

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public int getProtoEnum() {
    return 0;
  }

  public class Builder {
    public final BiometricContext mBiometricContext;
    public int mCommand;
    public final Context mContext;
    public byte[] mInputBuffer;
    public final Supplier mLazyDaemon;
    public ClientMonitorCallbackConverter mListener;
    public byte[] mOutputBuffer;
    public int mParam;
    public final int mSensorId;
    public IBinder mToken;
    public boolean mUseScheduler;
    public int mUserId = 0;
    public String mOwner = "FingerprintRequestClient";

    public Builder(Context context, BiometricContext biometricContext, Supplier supplier, int i) {
      this.mContext = context;
      this.mBiometricContext = biometricContext;
      this.mLazyDaemon = supplier;
      this.mSensorId = i;
    }

    public Builder setUserId(int i) {
      this.mUserId = i;
      return this;
    }

    public Builder setCommand(int i) {
      this.mCommand = i;
      return this;
    }

    public Builder setParam(int i) {
      this.mParam = i;
      return this;
    }

    public Builder setInputBuffer(byte[] bArr) {
      this.mInputBuffer = bArr;
      return this;
    }

    public Builder setOutputBuffer(byte[] bArr) {
      this.mOutputBuffer = bArr;
      return this;
    }

    public Builder setUseScheduler() {
      this.mUseScheduler = true;
      return this;
    }

    public SemFpBaseRequestClient build() {
      return new SemFpBaseRequestClient(
          this.mContext,
          this.mBiometricContext,
          this.mLazyDaemon,
          this.mToken,
          this.mListener,
          this.mSensorId,
          this.mUserId,
          this.mOwner,
          this.mUseScheduler,
          this.mCommand,
          this.mParam,
          this.mInputBuffer,
          this.mOutputBuffer);
    }
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
        new BiometricLogger(context, 1, 0, 0),
        biometricContext);
    this.mUseScheduler = z;
    this.mCommand = i3;
    this.mParam = i4;
    this.mRequestResult = -1;
    this.mOutputBuffer = bArr2;
    if (bArr == null || bArr.length == 0) {
      this.mInputBuffer = new byte[0];
    } else {
      this.mInputBuffer = (byte[]) bArr.clone();
    }
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean interruptsPrecedingClients() {
    int command = getCommand();
    return command == 3 || command == 4 || command == 5 || command == 7 || command == 9;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    long currentTimeMillis = System.currentTimeMillis();
    startHalOperation();
    Slog.i(
        "FingerprintRequestClient",
        "request["
            + this.mCommand
            + "] FP_FINISH ("
            + (System.currentTimeMillis() - currentTimeMillis)
            + "ms) RESULT: "
            + this.mRequestResult);
    boolean z = true;
    boolean z2 = this.mRequestResult >= 0;
    if (this.mUseScheduler && z2 && isAsyncOperation()) {
      z = false;
    }
    if (z) {
      this.mCallback.onClientFinished(this, z2);
    }
  }

  public final boolean isAsyncOperation() {
    return getListener() != null;
  }

  public void stopHalOperation() {
    Object obj = this.mLazyDaemon.get();
    if (obj instanceof ISehBiometricsFingerprint) {
      try {
        ((ISehBiometricsFingerprint) obj).cancel();
      } catch (RemoteException e) {
        Slog.e("FingerprintRequestClient", "Remote exception when requesting cancel", e);
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    Object obj = this.mLazyDaemon.get();
    if (obj instanceof ISehFingerprint) {
      startAidlHalOperation((ISehFingerprint) obj);
    } else if (obj instanceof ISehBiometricsFingerprint) {
      startHidlHalOperation((ISehBiometricsFingerprint) obj);
    } else {
      unableToStart();
    }
  }

  public final void startAidlHalOperation(ISehFingerprint iSehFingerprint) {
    try {
      SehResult sehRequest =
          iSehFingerprint.sehRequest(
              getSensorId(),
              getCommand(),
              getParam(),
              getInputBuffer() != null ? getInputBuffer() : new byte[0]);
      if (sehRequest == null) {
        return;
      }
      int i = sehRequest.retValue;
      this.mRequestResult = i;
      if (i == 0) {
        fillOutputBuffer(sehRequest.data);
      }
    } catch (RemoteException e) {
      Slog.e("FingerprintRequestClient", "Remote exception", e);
    }
  }

  public final void startHidlHalOperation(ISehBiometricsFingerprint iSehBiometricsFingerprint) {
    try {
      iSehBiometricsFingerprint.sehRequest(
          getCommand(),
          getParam(),
          getInputBufferAsList(),
          new ISehBiometricsFingerprint
              .sehRequestCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient$$ExternalSyntheticLambda0
            @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint.sehRequestCallback
            public final void onValues(int i, ArrayList arrayList) {
              SemFpBaseRequestClient.this.lambda$startHidlHalOperation$0(i, arrayList);
            }
          });
    } catch (RemoteException e) {
      Slog.e("FingerprintRequestClient", "Remote exception", e);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startHidlHalOperation$0(int i, ArrayList arrayList) {
    this.mRequestResult = i;
    if (i == 0) {
      fillOutputBuffer(listToByteArray(arrayList));
    }
  }

  public final byte[] listToByteArray(ArrayList arrayList) {
    byte[] bArr = new byte[arrayList.size()];
    for (int i = 0; i < arrayList.size(); i++) {
      bArr[i] = ((Byte) arrayList.get(i)).byteValue();
    }
    return bArr;
  }

  public final ArrayList getInputBufferAsList() {
    ArrayList arrayList = new ArrayList();
    byte[] bArr = this.mInputBuffer;
    if (bArr != null && bArr.length > 0) {
      for (byte b : bArr) {
        arrayList.add(Byte.valueOf(b));
      }
    }
    return arrayList;
  }

  public int startWithoutScheduler() {
    if (this.mUseScheduler) {
      Slog.w(
          "FingerprintRequestClient",
          "startWithoutScheduler: It must be started by the scheduler!!");
      throw new UnsupportedOperationException("useScheduler option is set");
    }
    start(
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient.1
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {}

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {}
        });
    return this.mRequestResult;
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void unableToStart() {
    this.mRequestResult = -1;
    this.mCallback.onClientFinished(this, false);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpRequestCommands
  public void onRequestResult(int i) {
    if (getListener() == null) {
      return;
    }
    try {
      getListener().onRequestResult(i);
    } catch (RemoteException e) {
      Slog.w("FingerprintRequestClient", "Failed to invoke sendEvent with onRequestResult", e);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public int getCommand() {
    return this.mCommand;
  }

  public int getParam() {
    return this.mParam;
  }

  public byte[] getInputBuffer() {
    return this.mInputBuffer;
  }

  public int getRequestResult() {
    return this.mRequestResult;
  }

  public void setRequestResultForTest(int i) {
    this.mRequestResult = i;
  }

  public final void fillOutputBuffer(byte[] bArr) {
    byte[] bArr2;
    if (bArr == null
        || bArr.length == 0
        || (bArr2 = this.mOutputBuffer) == null
        || bArr2.length == 0) {
      return;
    }
    int min = Math.min(bArr.length, bArr2.length);
    System.arraycopy(bArr, 0, this.mOutputBuffer, 0, min);
    this.mRequestResult = min;
  }
}
