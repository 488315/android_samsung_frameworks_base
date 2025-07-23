package android.hardware.input;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.graphics.PointF;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class VirtualMouse extends VirtualInputDevice {
    @Override // android.hardware.input.VirtualInputDevice, java.io.Closeable, java.lang.AutoCloseable
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

    public VirtualMouse(VirtualMouseConfig virtualMouseConfig, IVirtualDevice iVirtualDevice, IBinder iBinder) {
        super(virtualMouseConfig, iVirtualDevice, iBinder);
    }

    public void sendButtonEvent(VirtualMouseButtonEvent virtualMouseButtonEvent) {
        try {
            if (this.mVirtualDevice.sendButtonEvent(this.mToken, virtualMouseButtonEvent)) {
                return;
            }
            Log.w("VirtualInputDevice", "Failed to send button event to virtual mouse " + this.mConfig.getInputDeviceName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendScrollEvent(VirtualMouseScrollEvent virtualMouseScrollEvent) {
        try {
            if (this.mVirtualDevice.sendScrollEvent(this.mToken, virtualMouseScrollEvent)) {
                return;
            }
            Log.w("VirtualInputDevice", "Failed to send scroll event to virtual mouse " + this.mConfig.getInputDeviceName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendRelativeEvent(VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        try {
            if (this.mVirtualDevice.sendRelativeEvent(this.mToken, virtualMouseRelativeEvent)) {
                return;
            }
            Log.w("VirtualInputDevice", "Failed to send relative event to virtual mouse " + this.mConfig.getInputDeviceName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PointF getCursorPosition() {
        try {
            return this.mVirtualDevice.getCursorPosition(this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
