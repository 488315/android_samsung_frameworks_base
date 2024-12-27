package android.hardware.input;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class VirtualKeyboard extends VirtualInputDevice {
    private final int mUnsupportedKeyCode;

    @Override // android.hardware.input.VirtualInputDevice, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public VirtualKeyboard(VirtualKeyboardConfig config, IVirtualDevice virtualDevice, IBinder token) {
        super(config, virtualDevice, token);
        this.mUnsupportedKeyCode = 23;
    }

    public void sendKeyEvent(VirtualKeyEvent event) {
        try {
            if (23 == event.getKeyCode()) {
                throw new IllegalArgumentException("Unsupported key code " + event.getKeyCode() + " sent to a VirtualKeyboard input device.");
            }
            if (!this.mVirtualDevice.sendKeyEvent(this.mToken, event)) {
                Log.w("VirtualInputDevice", "Failed to send key event to virtual keyboard " + this.mConfig.getInputDeviceName());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.input.VirtualInputDevice
    public int getInputDeviceId() {
        return super.getInputDeviceId();
    }
}
