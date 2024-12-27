package com.android.server.location;

import android.R;
import android.content.Context;
import android.hardware.location.ActivityRecognitionHardware;
import android.hardware.location.IActivityRecognitionHardwareClient;
import android.hardware.location.IActivityRecognitionHardwareWatcher;
import android.os.IBinder;

import com.android.server.FgThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.injector.Injector;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$ServiceListener;
import com.android.server.servicewatcher.ServiceWatcherImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HardwareActivityRecognitionProxy implements ServiceWatcher$ServiceListener {
    public final ActivityRecognitionHardware mInstance;
    public final boolean mIsSupported;
    public final ServiceWatcherImpl mServiceWatcher;

    public HardwareActivityRecognitionProxy(Context context, Injector injector) {
        boolean isSupported = ActivityRecognitionHardware.isSupported();
        this.mIsSupported = isSupported;
        if (isSupported) {
            this.mInstance = ActivityRecognitionHardware.getInstance(context);
        } else {
            this.mInstance = null;
        }
        this.mServiceWatcher =
                new ServiceWatcherImpl(
                        context,
                        FgThread.getHandler(),
                        "HardwareActivityRecognitionProxy",
                        CurrentUserServiceSupplier.createFromConfig(
                                context,
                                "com.android.location.service.ActivityRecognitionProvider",
                                R.bool.config_enableCarDockHomeLaunch,
                                R.string.config_wwan_data_service_package),
                        this,
                        injector);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onBind(
            IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if (!IActivityRecognitionHardwareWatcher.class
                .getCanonicalName()
                .equals(interfaceDescriptor)) {
            if (IActivityRecognitionHardwareClient.class
                    .getCanonicalName()
                    .equals(interfaceDescriptor)) {
                IActivityRecognitionHardwareClient.Stub.asInterface(iBinder)
                        .onAvailabilityChanged(this.mIsSupported, this.mInstance);
                return;
            } else {
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        "Unknown descriptor: ", interfaceDescriptor, "ARProxy");
                return;
            }
        }
        IActivityRecognitionHardwareWatcher asInterface =
                IActivityRecognitionHardwareWatcher.Stub.asInterface(iBinder);
        ActivityRecognitionHardware activityRecognitionHardware = this.mInstance;
        if (activityRecognitionHardware != null) {
            asInterface.onInstanceChanged(activityRecognitionHardware);
        }
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onUnbind() {}
}
