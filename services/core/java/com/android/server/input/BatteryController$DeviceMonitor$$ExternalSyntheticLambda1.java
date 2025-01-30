package com.android.server.input;

import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryController$DeviceMonitor$$ExternalSyntheticLambda1
    implements Consumer {
  public final /* synthetic */ BatteryController.DeviceMonitor f$0;

  @Override // java.util.function.Consumer
  public final void accept(Object obj) {
    this.f$0.updateBatteryStateFromNative(((Long) obj).longValue());
  }
}
