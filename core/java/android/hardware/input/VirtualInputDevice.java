package android.hardware.input;

import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.RemoteException;
import java.io.Closeable;

/* loaded from: classes2.dex */
abstract class VirtualInputDevice implements Closeable {
  protected final IBinder mToken;
  protected final IVirtualDevice mVirtualDevice;

  VirtualInputDevice(IVirtualDevice virtualDevice, IBinder token) {
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
    try {
      this.mVirtualDevice.unregisterInputDevice(this.mToken);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }
}
