package com.android.server.biometrics.sensors.fingerprint.hidl;

import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Fingerprint21$$ExternalSyntheticLambda18 implements Supplier {
  public final /* synthetic */ Fingerprint21 f$0;

  public /* synthetic */ Fingerprint21$$ExternalSyntheticLambda18(Fingerprint21 fingerprint21) {
    this.f$0 = fingerprint21;
  }

  @Override // java.util.function.Supplier
  public final Object get() {
    ISehBiometricsFingerprint sehDaemon;
    sehDaemon = this.f$0.getSehDaemon();
    return sehDaemon;
  }
}
