package com.android.server.health;

import android.hardware.health.HealthInfo;
import android.hardware.health.IHealth;
import android.os.BatteryProperty;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IServiceCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.Trace;
import android.util.Slog;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import vendor.samsung.hardware.health.ISehHealth;

/* loaded from: classes2.dex */
public class HealthServiceWrapperAidl extends HealthServiceWrapper {
  static final String SERVICE_NAME = IHealth.DESCRIPTOR + "/default";
  public final HandlerThread mHandlerThread = new HandlerThread("HealthServiceBinder");
  public final AtomicReference mLastSehService;
  public final AtomicReference mLastService;
  public final HealthRegCallbackAidl mRegCallback;
  public final IServiceCallback mServiceCallback;

  public interface ServiceManagerStub {
    default IHealth waitForDeclaredService(String str) {
      return IHealth.Stub.asInterface(ServiceManager.waitForDeclaredService(str));
    }

    default void registerForNotifications(String str, IServiceCallback iServiceCallback) {
      ServiceManager.registerForNotifications(str, iServiceCallback);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x007c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public HealthServiceWrapperAidl(
      HealthRegCallbackAidl healthRegCallbackAidl, ServiceManagerStub serviceManagerStub) {
    ISehHealth asInterface;
    AtomicReference atomicReference = new AtomicReference();
    this.mLastService = atomicReference;
    this.mServiceCallback = new ServiceCallback();
    this.mLastSehService = new AtomicReference();
    traceBegin("HealthInitGetServiceAidl");
    try {
      String str = SERVICE_NAME;
      IHealth waitForDeclaredService = serviceManagerStub.waitForDeclaredService(str);
      Slog.d(
          "HealthServiceWrapperAidl",
          "HealthServiceWrapperAidl: newService: " + waitForDeclaredService);
      if (waitForDeclaredService == null) {
        throw new NoSuchElementException(
            "IHealth service instance isn't available. Perhaps no permission?");
      }
      atomicReference.set(waitForDeclaredService);
      IBinder allowBlocking = Binder.allowBlocking(ServiceManager.waitForDeclaredService(str));
      try {
        if (allowBlocking != null) {
          try {
            asInterface = ISehHealth.Stub.asInterface(allowBlocking.getExtension());
          } catch (RemoteException unused) {
            Slog.e(
                "HealthServiceWrapperAidl",
                "Unable to register DeathRecipient for " + ((Object) null));
          }
          this.mLastSehService.set(asInterface);
          this.mRegCallback = healthRegCallbackAidl;
          if (healthRegCallbackAidl != null) {
            healthRegCallbackAidl.onRegistration(null, asInterface);
          }
          traceBegin("HealthInitRegisterNotificationAidl");
          this.mHandlerThread.start();
          serviceManagerStub.registerForNotifications(SERVICE_NAME, this.mServiceCallback);
          traceEnd();
          Slog.i("HealthServiceWrapperAidl", "health: HealthServiceWrapper listening to AIDL HAL");
          return;
        }
        serviceManagerStub.registerForNotifications(SERVICE_NAME, this.mServiceCallback);
        traceEnd();
        Slog.i("HealthServiceWrapperAidl", "health: HealthServiceWrapper listening to AIDL HAL");
        return;
      } finally {
      }
      asInterface = null;
      this.mLastSehService.set(asInterface);
      this.mRegCallback = healthRegCallbackAidl;
      if (healthRegCallbackAidl != null) {}
      traceBegin("HealthInitRegisterNotificationAidl");
      this.mHandlerThread.start();
    } finally {
    }
  }

  @Override // com.android.server.health.HealthServiceWrapper
  public HandlerThread getHandlerThread() {
    return this.mHandlerThread;
  }

  @Override // com.android.server.health.HealthServiceWrapper
  public int getProperty(int i, BatteryProperty batteryProperty) {
    traceBegin("HealthGetPropertyAidl");
    try {
      return getPropertyInternal(i, batteryProperty);
    } finally {
      traceEnd();
    }
  }

  public final int getPropertyInternal(int i, BatteryProperty batteryProperty) {
    IHealth iHealth = (IHealth) this.mLastService.get();
    if (iHealth == null) {
      throw new RemoteException("no health service");
    }
    try {
      switch (i) {
        case 1:
          batteryProperty.setLong(iHealth.getChargeCounterUah());
          break;
        case 2:
          batteryProperty.setLong(iHealth.getCurrentNowMicroamps());
          break;
        case 3:
          batteryProperty.setLong(iHealth.getCurrentAverageMicroamps());
          break;
        case 4:
          batteryProperty.setLong(iHealth.getCapacity());
          break;
        case 5:
          batteryProperty.setLong(iHealth.getEnergyCounterNwh());
          break;
        case 6:
          batteryProperty.setLong(iHealth.getChargeStatus());
          break;
        case 7:
          batteryProperty.setLong(iHealth.getBatteryHealthData().batteryManufacturingDateSeconds);
          break;
        case 8:
          batteryProperty.setLong(iHealth.getBatteryHealthData().batteryFirstUsageSeconds);
          break;
        case 9:
          batteryProperty.setLong(iHealth.getChargingPolicy());
          break;
        case 10:
          batteryProperty.setLong(iHealth.getBatteryHealthData().batteryStateOfHealth);
          break;
        default:
          return 0;
      }
      return 0;
    } catch (UnsupportedOperationException unused) {
      return -1;
    } catch (ServiceSpecificException unused2) {
      return -2;
    }
  }

  @Override // com.android.server.health.HealthServiceWrapper
  public void scheduleUpdate() {
    getHandlerThread()
        .getThreadHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.health.HealthServiceWrapperAidl$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                HealthServiceWrapperAidl.this.lambda$scheduleUpdate$0();
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdate$0() {
    IHealth iHealth;
    traceBegin("HealthScheduleUpdate");
    try {
      try {
        iHealth = (IHealth) this.mLastService.get();
      } catch (RemoteException | ServiceSpecificException e) {
        Slog.e("HealthServiceWrapperAidl", "Cannot call update on health AIDL HAL", e);
      }
      if (iHealth == null) {
        Slog.e("HealthServiceWrapperAidl", "no health service");
      } else {
        iHealth.update();
      }
    } finally {
      traceEnd();
    }
  }

  @Override // com.android.server.health.HealthServiceWrapper
  public HealthInfo getHealthInfo() {
    IHealth iHealth = (IHealth) this.mLastService.get();
    if (iHealth == null) {
      return null;
    }
    try {
      return iHealth.getHealthInfo();
    } catch (UnsupportedOperationException | ServiceSpecificException unused) {
      return null;
    }
  }

  @Override // com.android.server.health.HealthServiceWrapper
  public void sehWriteEnableToParam(int i, boolean z, String str) {
    try {
      ISehHealth iSehHealth = (ISehHealth) this.mLastSehService.get();
      if (iSehHealth == null) {
        throw new RemoteException("no seh health service");
      }
      iSehHealth.sehWriteEnableToParam(i, z);
      Slog.d(
          "HealthServiceWrapperAidl",
          "sehWriteEnableToParam[" + str + "]: , offset: " + i + ", enable: " + z);
    } catch (Exception e) {
      Slog.e(
          "HealthServiceWrapperAidl",
          "Exception in sehWriteEnableToParam[" + str + "]: , offset: " + i + ", enable: " + z);
      e.printStackTrace();
    }
  }

  public static void traceBegin(String str) {
    Trace.traceBegin(524288L, str);
  }

  public static void traceEnd() {
    Trace.traceEnd(524288L);
  }

  public class ServiceCallback extends IServiceCallback.Stub {
    public ServiceCallback() {}

    public void onRegistration(String str, final IBinder iBinder) {
      if (HealthServiceWrapperAidl.SERVICE_NAME.equals(str)) {
        HealthServiceWrapperAidl.this
            .getHandlerThread()
            .getThreadHandler()
            .post(
                new Runnable() { // from class:
                                 // com.android.server.health.HealthServiceWrapperAidl$ServiceCallback$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    HealthServiceWrapperAidl.ServiceCallback.this.lambda$onRegistration$0(iBinder);
                  }
                });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRegistration$0(IBinder iBinder) {
      IHealth iHealth =
          (IHealth)
              HealthServiceWrapperAidl.this.mLastService.getAndSet(
                  IHealth.Stub.asInterface(Binder.allowBlocking(iBinder)));
      ISehHealth iSehHealth = null;
      if (Objects.equals(iBinder, iHealth != null ? iHealth.asBinder() : null)) {
        return;
      }
      try {
        iSehHealth = ISehHealth.Stub.asInterface(Binder.allowBlocking(iBinder).getExtension());
      } catch (Exception e) {
        Slog.e("HealthServiceWrapperAidl", "Unable to getExtension for health ", e);
      }
      if (iSehHealth == null) {
        Slog.e(
            "HealthServiceWrapperAidl",
            "HealthServiceWrapperAidl: ServiceCallback: newSehService: " + iSehHealth);
        throw new NoSuchElementException(
            "ServiceCallback: ISehHealth service instance isn't available. Perhaps no permission?");
      }
      ISehHealth iSehHealth2 =
          (ISehHealth) HealthServiceWrapperAidl.this.mLastSehService.getAndSet(iSehHealth);
      Slog.i("HealthServiceWrapperAidl", "New health AIDL HAL service registered");
      if (HealthServiceWrapperAidl.this.mRegCallback != null) {
        HealthServiceWrapperAidl.this.mRegCallback.onRegistration(iSehHealth2, iSehHealth);
      }
    }
  }
}
