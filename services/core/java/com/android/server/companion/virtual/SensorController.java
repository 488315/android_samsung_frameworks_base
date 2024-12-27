package com.android.server.companion.virtual;

import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.sensor.IVirtualSensorCallback;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtual.sensor.VirtualSensorConfig;
import android.companion.virtual.sensor.VirtualSensorEvent;
import android.content.AttributionSource;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.modules.expresslog.Counter;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.SensorController;
import com.android.server.sensors.SensorManagerInternal$RuntimeSensorCallback;
import com.android.server.sensors.SensorService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SensorController {
    public static final AtomicInteger sNextDirectChannelHandle = new AtomicInteger(1);
    public final AttributionSource mAttributionSource;
    public final RuntimeSensorCallbackWrapper mRuntimeSensorCallback;
    public final int mVirtualDeviceId;
    public final Object mLock = new Object();
    public final ArrayMap mSensorDescriptors = new ArrayMap();
    public final SparseArray mVirtualSensors = new SparseArray();
    public List mVirtualSensorList = null;
    public final SensorService.LocalService mSensorManagerInternal = (SensorService.LocalService) LocalServices.getService(SensorService.LocalService.class);
    public final VirtualDeviceManagerInternal mVdmInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RuntimeSensorCallbackWrapper implements SensorManagerInternal$RuntimeSensorCallback {
        public final IVirtualSensorCallback mCallback;

        public RuntimeSensorCallbackWrapper(IVirtualSensorCallback iVirtualSensorCallback) {
            this.mCallback = iVirtualSensorCallback;
        }

        @Override // com.android.server.sensors.SensorManagerInternal$RuntimeSensorCallback
        public final int onConfigurationChanged(int i, boolean z, int i2, int i3) {
            if (this.mCallback == null) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "No sensor callback configured for sensor handle ", "SensorController");
                return -22;
            }
            SensorController sensorController = SensorController.this;
            VirtualSensor virtualSensor = sensorController.mVdmInternal.getVirtualSensor(sensorController.mVirtualDeviceId, i);
            if (virtualSensor != null) {
                try {
                    this.mCallback.onConfigurationChanged(virtualSensor, z, i2, i3);
                    return 0;
                } catch (RemoteException e) {
                    Slog.e("SensorController", "Failed to call sensor callback: " + e);
                    return Integer.MIN_VALUE;
                }
            }
            Slog.e("SensorController", "No sensor found for deviceId=" + sensorController.mVirtualDeviceId + " and sensor handle=" + i);
            return -22;
        }

        @Override // com.android.server.sensors.SensorManagerInternal$RuntimeSensorCallback
        public final int onDirectChannelConfigured(int i, int i2, int i3) {
            if (this.mCallback == null) {
                Slog.e("SensorController", "No runtime sensor callback configured.");
                return -22;
            }
            SensorController sensorController = SensorController.this;
            VirtualSensor virtualSensor = sensorController.mVdmInternal.getVirtualSensor(sensorController.mVirtualDeviceId, i2);
            if (virtualSensor == null) {
                Slog.e("SensorController", "No sensor found for deviceId=" + sensorController.mVirtualDeviceId + " and sensor handle=" + i2);
                return -22;
            }
            try {
                this.mCallback.onDirectChannelConfigured(i, virtualSensor, i3, i2);
                if (i3 == 0) {
                    return 0;
                }
                return i2;
            } catch (RemoteException e) {
                Slog.e("SensorController", "Failed to call sensor callback: " + e);
                return Integer.MIN_VALUE;
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal$RuntimeSensorCallback
        public final int onDirectChannelCreated(ParcelFileDescriptor parcelFileDescriptor) {
            if (this.mCallback == null) {
                VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("No sensor callback for virtual deviceId "), SensorController.this.mVirtualDeviceId, "SensorController");
                return -22;
            }
            if (parcelFileDescriptor == null) {
                Slog.e("SensorController", "Received invalid ParcelFileDescriptor");
                return -22;
            }
            int andIncrement = SensorController.sNextDirectChannelHandle.getAndIncrement();
            try {
                this.mCallback.onDirectChannelCreated(andIncrement, SharedMemory.fromFileDescriptor(parcelFileDescriptor));
                return andIncrement;
            } catch (RemoteException e) {
                Slog.e("SensorController", "Failed to call sensor callback: " + e);
                return Integer.MIN_VALUE;
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal$RuntimeSensorCallback
        public final void onDirectChannelDestroyed(int i) {
            IVirtualSensorCallback iVirtualSensorCallback = this.mCallback;
            if (iVirtualSensorCallback == null) {
                VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("No sensor callback for virtual deviceId "), SensorController.this.mVirtualDeviceId, "SensorController");
                return;
            }
            try {
                iVirtualSensorCallback.onDirectChannelDestroyed(i);
            } catch (RemoteException e) {
                Slog.e("SensorController", "Failed to call sensor callback: " + e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class SensorCreationException extends Exception {
        public SensorCreationException(String str) {
            super(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class SensorDescriptor {
        public final int mHandle;
        public final String mName;
        public final int mType;

        public SensorDescriptor(int i, int i2, String str) {
            this.mHandle = i;
            this.mType = i2;
            this.mName = str;
        }
    }

    public SensorController(IVirtualDevice iVirtualDevice, int i, AttributionSource attributionSource, IVirtualSensorCallback iVirtualSensorCallback, List list) {
        this.mVirtualDeviceId = i;
        this.mAttributionSource = attributionSource;
        this.mRuntimeSensorCallback = new RuntimeSensorCallbackWrapper(iVirtualSensorCallback);
        Objects.requireNonNull(iVirtualDevice);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    createSensorInternal(iVirtualDevice, (VirtualSensorConfig) it.next());
                }
            } catch (SensorCreationException e) {
                throw new RuntimeException("Failed to create virtual sensor", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addSensorForTesting(IBinder iBinder, int i, int i2, String str) {
        synchronized (this.mLock) {
            this.mSensorDescriptors.put(iBinder, new SensorDescriptor(i, i2, str));
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            this.mSensorDescriptors.values().forEach(new Consumer() { // from class: com.android.server.companion.virtual.SensorController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SensorService.LocalService localService = SensorController.this.mSensorManagerInternal;
                    int i = ((SensorController.SensorDescriptor) obj).mHandle;
                    synchronized (SensorService.this.mLock) {
                        try {
                            if (SensorService.this.mRuntimeSensorHandles.contains(Integer.valueOf(i))) {
                                SensorService.this.mRuntimeSensorHandles.remove(Integer.valueOf(i));
                                SensorService.unregisterRuntimeSensorNative(SensorService.this.mPtr, i);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
            this.mSensorDescriptors.clear();
            this.mVirtualSensors.clear();
            this.mVirtualSensorList = null;
        }
    }

    public final void createSensorInternal(IVirtualDevice iVirtualDevice, VirtualSensorConfig virtualSensorConfig) {
        int registerRuntimeSensorNative;
        Objects.requireNonNull(virtualSensorConfig);
        if (virtualSensorConfig.getType() <= 0) {
            throw new SensorCreationException("Received an invalid virtual sensor type (config name '" + virtualSensorConfig.getName() + "').");
        }
        SensorService.LocalService localService = this.mSensorManagerInternal;
        int i = this.mVirtualDeviceId;
        int type = virtualSensorConfig.getType();
        String name = virtualSensorConfig.getName();
        String vendor2 = virtualSensorConfig.getVendor() == null ? "" : virtualSensorConfig.getVendor();
        float maximumRange = virtualSensorConfig.getMaximumRange();
        float resolution = virtualSensorConfig.getResolution();
        float power = virtualSensorConfig.getPower();
        int minDelay = virtualSensorConfig.getMinDelay();
        int maxDelay = virtualSensorConfig.getMaxDelay();
        int flags = virtualSensorConfig.getFlags();
        RuntimeSensorCallbackWrapper runtimeSensorCallbackWrapper = this.mRuntimeSensorCallback;
        synchronized (SensorService.this.mLock) {
            registerRuntimeSensorNative = SensorService.registerRuntimeSensorNative(SensorService.this.mPtr, i, type, name, vendor2, maximumRange, resolution, power, minDelay, maxDelay, flags, runtimeSensorCallbackWrapper);
            SensorService.this.mRuntimeSensorHandles.add(Integer.valueOf(registerRuntimeSensorNative));
        }
        if (registerRuntimeSensorNative <= 0) {
            throw new SensorCreationException("Received an invalid virtual sensor handle '" + virtualSensorConfig.getName() + "'.");
        }
        SensorDescriptor sensorDescriptor = new SensorDescriptor(registerRuntimeSensorNative, virtualSensorConfig.getType(), virtualSensorConfig.getName());
        Binder binder = new Binder("android.hardware.sensor.VirtualSensor:" + virtualSensorConfig.getName());
        VirtualSensor virtualSensor = new VirtualSensor(registerRuntimeSensorNative, virtualSensorConfig.getType(), virtualSensorConfig.getName(), iVirtualDevice, binder);
        synchronized (this.mLock) {
            this.mSensorDescriptors.put(binder, sensorDescriptor);
            this.mVirtualSensors.put(registerRuntimeSensorNative, virtualSensor);
        }
        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
            Counter.logIncrementWithUid("virtual_devices.value_virtual_sensors_created_count", this.mAttributionSource.getUid());
        }
    }

    public Map getSensorDescriptors() {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mSensorDescriptors);
        }
        return arrayMap;
    }

    public final boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) {
        boolean sendRuntimeSensorEventNative;
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(virtualSensorEvent);
        synchronized (this.mLock) {
            SensorDescriptor sensorDescriptor = (SensorDescriptor) this.mSensorDescriptors.get(iBinder);
            if (sensorDescriptor == null) {
                throw new IllegalArgumentException("Could not send sensor event for given token");
            }
            SensorService.LocalService localService = this.mSensorManagerInternal;
            int i = sensorDescriptor.mHandle;
            int i2 = sensorDescriptor.mType;
            long timestampNanos = virtualSensorEvent.getTimestampNanos();
            float[] values = virtualSensorEvent.getValues();
            synchronized (SensorService.this.mLock) {
                try {
                    sendRuntimeSensorEventNative = !SensorService.this.mRuntimeSensorHandles.contains(Integer.valueOf(i)) ? false : SensorService.sendRuntimeSensorEventNative(SensorService.this.mPtr, i, i2, timestampNanos, values);
                } finally {
                }
            }
        }
        return sendRuntimeSensorEventNative;
    }
}
