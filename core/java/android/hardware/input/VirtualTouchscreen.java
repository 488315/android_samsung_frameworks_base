package android.hardware.input;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class VirtualTouchscreen extends VirtualInputDevice {
    @Override // android.hardware.input.VirtualInputDevice, java.io.Closeable,
    // java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ int getInputDeviceId() {
        return super.getInputDeviceId();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public VirtualTouchscreen(
            VirtualTouchscreenConfig config, IVirtualDevice virtualDevice, IBinder token) {
        super(config, virtualDevice, token);
    }

    public void sendTouchEvent(VirtualTouchEvent event) {
        try {
            if (!this.mVirtualDevice.sendTouchEvent(this.mToken, event)) {
                Log.w(
                        "VirtualInputDevice",
                        "Failed to send touch event to virtual touchscreen "
                                + this.mConfig.getInputDeviceName());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
