package android.hardware.input;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class VirtualRotaryEncoder extends VirtualInputDevice {
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

    public VirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig, IVirtualDevice iVirtualDevice, IBinder iBinder) {
        super(virtualRotaryEncoderConfig, iVirtualDevice, iBinder);
    }

    public void sendScrollEvent(VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent) {
        try {
            if (this.mVirtualDevice.sendRotaryEncoderScrollEvent(this.mToken, virtualRotaryEncoderScrollEvent)) {
                return;
            }
            Log.w("VirtualInputDevice", "Failed to send scroll event from virtual rotary " + this.mConfig.getInputDeviceName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
