package com.android.server.sensorprivacy;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class CameraPrivacyLightController$$ExternalSyntheticLambda0
    implements Runnable {
  public final /* synthetic */ CameraPrivacyLightController f$0;

  public /* synthetic */ CameraPrivacyLightController$$ExternalSyntheticLambda0(
      CameraPrivacyLightController cameraPrivacyLightController) {
    this.f$0 = cameraPrivacyLightController;
  }

  @Override // java.lang.Runnable
  public final void run() {
    this.f$0.updateLightSession();
  }
}
