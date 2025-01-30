package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hardware.face.Face;
import android.os.HidlMemory;
import android.util.Slog;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback;

/* loaded from: classes.dex */
public class TestHal extends ISehBiometricsFace.Stub {
  public ISehBiometricsFaceClientCallback mCallback;
  public final Context mContext;
  public final int mSensorId;
  public int mUserId;

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int revokeChallenge() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehAuthenticate(long j, int i, ArrayList arrayList) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehCloseTaSession() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehConfigurePreview(int i, ArrayList arrayList) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehFinishTaInstallation(ArrayList arrayList) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public String sehGetEngineVersion() {
    return null;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public void sehGetFaceTag(
      int i, ISehBiometricsFace.sehGetFaceTagCallback sehgetfacetagcallback) {}

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public void sehGetFaceTagList(
      ISehBiometricsFace.sehGetFaceTagListCallback sehgetfacetaglistcallback) {}

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehGetServicePid() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public String sehGetTaInfo() {
    return null;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehInstallTaDataChunk(HidlMemory hidlMemory) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public boolean sehIsTaSessionClosed() {
    return false;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehOpenTaSession() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehPauseEnrollment() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehPrepareTaInstallation() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehResumeEnrollment() {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehSetFaceTag(int i, ArrayList arrayList) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehSetRotation(int i) {
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public int sehSetSecurityLevel(int i) {
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int userActivity() {
    return 0;
  }

  public TestHal(Context context, int i) {
    this.mContext = context;
    this.mSensorId = i;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public OptionalUint64 setCallback(IBiometricsFaceClientCallback iBiometricsFaceClientCallback) {
    this.mCallback = (ISehBiometricsFaceClientCallback) iBiometricsFaceClientCallback;
    new OptionalUint64().status = 0;
    return new OptionalUint64();
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int setActiveUser(int i, String str) {
    this.mUserId = i;
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public OptionalUint64 generateChallenge(int i) {
    Slog.w("face.hidl.TestHal", "generateChallenge");
    OptionalUint64 optionalUint64 = new OptionalUint64();
    optionalUint64.status = 0;
    optionalUint64.value = 0L;
    return optionalUint64;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
    Slog.w("face.hidl.TestHal", "enroll");
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public OptionalBool getFeature(int i, int i2) {
    OptionalBool optionalBool = new OptionalBool();
    optionalBool.status = 0;
    optionalBool.value = true;
    return optionalBool;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public OptionalUint64 getAuthenticatorId() {
    OptionalUint64 optionalUint64 = new OptionalUint64();
    optionalUint64.status = 0;
    optionalUint64.value = 0L;
    return optionalUint64;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int cancel() {
    ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
    if (iSehBiometricsFaceClientCallback == null) {
      return 0;
    }
    iSehBiometricsFaceClientCallback.onError(0L, 0, 5, 0);
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int enumerate() {
    Slog.w("face.hidl.TestHal", "enumerate");
    ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
    if (iSehBiometricsFaceClientCallback != null) {
      iSehBiometricsFaceClientCallback.onEnumerate(0L, new ArrayList(), 0);
    }
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int remove(int i) {
    Slog.w("face.hidl.TestHal", "remove");
    ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
    if (iSehBiometricsFaceClientCallback == null) {
      return 0;
    }
    if (i == 0) {
      List biometricsForUser =
          FaceUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, this.mUserId);
      ArrayList arrayList = new ArrayList();
      Iterator it = biometricsForUser.iterator();
      while (it.hasNext()) {
        arrayList.add(Integer.valueOf(((Face) it.next()).getBiometricId()));
      }
      this.mCallback.onRemoved(0L, arrayList, this.mUserId);
      return 0;
    }
    iSehBiometricsFaceClientCallback.onRemoved(
        0L, new ArrayList(Collections.singletonList(Integer.valueOf(i))), this.mUserId);
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int authenticate(long j) {
    Slog.w("face.hidl.TestHal", "authenticate");
    return 0;
  }

  @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
  public int resetLockout(ArrayList arrayList) {
    Slog.w("face.hidl.TestHal", "resetLockout");
    return 0;
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public void sehGetSecurityLevel(
      ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
    sehgetsecuritylevelcallback.onValues(0, 2);
  }

  @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
  public OptionalUint64 sehSetCallback(
      ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
    this.mCallback = iSehBiometricsFaceClientCallback;
    new OptionalUint64().status = 0;
    return new OptionalUint64();
  }
}
