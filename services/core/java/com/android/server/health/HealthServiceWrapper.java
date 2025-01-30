package com.android.server.health;

import android.hardware.health.HealthInfo;
import android.os.BatteryProperty;
import android.os.HandlerThread;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public abstract class HealthServiceWrapper {
  public abstract HandlerThread getHandlerThread();

  public abstract HealthInfo getHealthInfo();

  public abstract int getProperty(int i, BatteryProperty batteryProperty);

  public abstract void scheduleUpdate();

  public abstract void sehWriteEnableToParam(int i, boolean z, String str);

  public static HealthServiceWrapper create(HealthInfoCallback healthInfoCallback) {
    return create(
        healthInfoCallback == null ? null : new HealthRegCallbackAidl(healthInfoCallback),
        new HealthServiceWrapperAidl
            .ServiceManagerStub() { // from class: com.android.server.health.HealthServiceWrapper.1
        },
        healthInfoCallback != null ? new HealthHalCallbackHidl(healthInfoCallback) : null,
        new HealthServiceWrapperHidl
            .IServiceManagerSupplier() { // from class:
                                         // com.android.server.health.HealthServiceWrapper.2
        },
        new HealthServiceWrapperHidl
            .IHealthSupplier() { // from class: com.android.server.health.HealthServiceWrapper.3
        });
  }

  public static HealthServiceWrapper create(
      HealthRegCallbackAidl healthRegCallbackAidl,
      HealthServiceWrapperAidl.ServiceManagerStub serviceManagerStub,
      HealthServiceWrapperHidl.Callback callback,
      HealthServiceWrapperHidl.IServiceManagerSupplier iServiceManagerSupplier,
      HealthServiceWrapperHidl.IHealthSupplier iHealthSupplier) {
    try {
      return new HealthServiceWrapperAidl(healthRegCallbackAidl, serviceManagerStub);
    } catch (NoSuchElementException unused) {
      return new HealthServiceWrapperHidl(callback, iServiceManagerSupplier, iHealthSupplier);
    }
  }
}
