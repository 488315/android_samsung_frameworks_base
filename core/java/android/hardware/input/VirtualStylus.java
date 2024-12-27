package android.hardware.input;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class VirtualStylus extends VirtualInputDevice {
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

    public VirtualStylus(VirtualStylusConfig config, IVirtualDevice virtualDevice, IBinder token) {
        super(config, virtualDevice, token);
    }

    public void sendMotionEvent(VirtualStylusMotionEvent event) {
        try {
            if (!this.mVirtualDevice.sendStylusMotionEvent(this.mToken, event)) {
                Log.w(
                        "VirtualInputDevice",
                        "Failed to send motion event from virtual stylus "
                                + this.mConfig.getInputDeviceName());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendButtonEvent(VirtualStylusButtonEvent event) {
        try {
            if (!this.mVirtualDevice.sendStylusButtonEvent(this.mToken, event)) {
                Log.w(
                        "VirtualInputDevice",
                        "Failed to send button event from virtual stylus "
                                + this.mConfig.getInputDeviceName());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
