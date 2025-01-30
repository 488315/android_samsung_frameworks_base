package com.android.server.location;

import android.R;
import android.content.Context;
import android.hardware.location.ActivityRecognitionHardware;
import android.hardware.location.IActivityRecognitionHardwareClient;
import android.hardware.location.IActivityRecognitionHardwareWatcher;
import android.os.IBinder;
import android.util.Log;
import com.android.server.location.injector.Injector;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher;

/* loaded from: classes2.dex */
public class HardwareActivityRecognitionProxy implements ServiceWatcher.ServiceListener {
  public final ActivityRecognitionHardware mInstance;
  public final boolean mIsSupported;
  public final ServiceWatcher mServiceWatcher;

  @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
  public void onUnbind() {}

  public static HardwareActivityRecognitionProxy createAndRegister(
      Context context, Injector injector) {
    HardwareActivityRecognitionProxy hardwareActivityRecognitionProxy =
        new HardwareActivityRecognitionProxy(context, injector);
    if (hardwareActivityRecognitionProxy.register()) {
      return hardwareActivityRecognitionProxy;
    }
    return null;
  }

  public HardwareActivityRecognitionProxy(Context context, Injector injector) {
    boolean isSupported = ActivityRecognitionHardware.isSupported();
    this.mIsSupported = isSupported;
    if (isSupported) {
      this.mInstance = ActivityRecognitionHardware.getInstance(context);
    } else {
      this.mInstance = null;
    }
    this.mServiceWatcher =
        ServiceWatcher.create(
            context,
            "HardwareActivityRecognitionProxy",
            CurrentUserServiceSupplier.createFromConfig(
                context,
                "com.android.location.service.ActivityRecognitionProvider",
                R.bool.config_dockedStackDividerFreeSnapMode,
                R.string.config_wearableAmbientContextEventArrayExtraKey),
            this,
            injector);
  }

  public final boolean register() {
    boolean checkServiceResolves = this.mServiceWatcher.checkServiceResolves();
    if (checkServiceResolves) {
      this.mServiceWatcher.register();
    }
    return checkServiceResolves;
  }

  @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
  public void onBind(
      IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
    if (IActivityRecognitionHardwareWatcher.class.getCanonicalName().equals(interfaceDescriptor)) {
      IActivityRecognitionHardwareWatcher asInterface =
          IActivityRecognitionHardwareWatcher.Stub.asInterface(iBinder);
      ActivityRecognitionHardware activityRecognitionHardware = this.mInstance;
      if (activityRecognitionHardware != null) {
        asInterface.onInstanceChanged(activityRecognitionHardware);
        return;
      }
      return;
    }
    if (IActivityRecognitionHardwareClient.class.getCanonicalName().equals(interfaceDescriptor)) {
      IActivityRecognitionHardwareClient.Stub.asInterface(iBinder)
          .onAvailabilityChanged(this.mIsSupported, this.mInstance);
      return;
    }
    Log.e("ARProxy", "Unknown descriptor: " + interfaceDescriptor);
  }
}
