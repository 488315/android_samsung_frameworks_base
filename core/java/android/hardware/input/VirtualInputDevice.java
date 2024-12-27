package android.hardware.input;

import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.Closeable;

/* loaded from: classes2.dex */
abstract class VirtualInputDevice implements Closeable {
    protected static final String TAG = "VirtualInputDevice";
    protected final VirtualInputDeviceConfig mConfig;
    protected final IBinder mToken;
    protected final IVirtualDevice mVirtualDevice;

    VirtualInputDevice(
            VirtualInputDeviceConfig config, IVirtualDevice virtualDevice, IBinder token) {
        this.mConfig = config;
        this.mVirtualDevice = virtualDevice;
        this.mToken = token;
    }

    public int getInputDeviceId() {
        try {
            return this.mVirtualDevice.getInputDeviceId(this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Log.d(TAG, "Closing virtual input device " + this.mConfig.getInputDeviceName());
        try {
            this.mVirtualDevice.unregisterInputDevice(this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String toString() {
        return this.mConfig.toString();
    }
}
