package com.android.server.devicestate;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda3
    implements Runnable {
  public final /* synthetic */ DeviceStateManagerService f$0;

  public /* synthetic */ DeviceStateManagerService$$ExternalSyntheticLambda3(
      DeviceStateManagerService deviceStateManagerService) {
    this.f$0 = deviceStateManagerService;
  }

  @Override // java.lang.Runnable
  public final void run() {
    this.f$0.notifyPolicyIfNeeded();
  }
}
