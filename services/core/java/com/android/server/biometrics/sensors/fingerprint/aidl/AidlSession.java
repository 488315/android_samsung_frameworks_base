package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.keymaster.HardwareAuthToken;
import android.util.Slog;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;

/* loaded from: classes.dex */
public class AidlSession {
  public final int mHalInterfaceVersion;
  public final Sensor.HalSessionCallback mHalSessionCallback;
  public Supplier mLazySehFingerprint;
  public final ISession mSession;
  public final int mUserId;

  public AidlSession(
      int i, ISession iSession, int i2, Sensor.HalSessionCallback halSessionCallback) {
    this.mHalInterfaceVersion = i;
    this.mSession = iSession;
    this.mUserId = i2;
    this.mHalSessionCallback = halSessionCallback;
    Slog.i("FingerprintService", "AidlSession: " + i);
  }

  public ISession getSession() {
    return this.mSession;
  }

  public int getUserId() {
    return this.mUserId;
  }

  public Sensor.HalSessionCallback getHalSessionCallback() {
    return this.mHalSessionCallback;
  }

  public boolean hasContextMethods() {
    return this.mHalInterfaceVersion >= 2;
  }

  public void resetLockout(HardwareAuthToken hardwareAuthToken) {
    Sensor.HalSessionCallback halSessionCallback = this.mHalSessionCallback;
    if (halSessionCallback != null) {
      halSessionCallback.onLockoutCleared();
    }
  }

  public void setLazySehFingerprint(Supplier supplier) {
    this.mLazySehFingerprint = supplier;
  }

  public ISehFingerprint getSehFingerprint() {
    Supplier supplier = this.mLazySehFingerprint;
    if (supplier != null) {
      return (ISehFingerprint) supplier.get();
    }
    return null;
  }
}
