package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.RemoteException;
import java.io.Closeable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualCamera implements Closeable {
    private final String mCameraId;
    private final VirtualCameraConfig mConfig;
    private final IVirtualDevice mVirtualDevice;

    public VirtualCamera(IVirtualDevice iVirtualDevice, String str, VirtualCameraConfig virtualCameraConfig) {
        this.mVirtualDevice = (IVirtualDevice) Objects.requireNonNull(iVirtualDevice);
        this.mCameraId = (String) Objects.requireNonNull(str);
        this.mConfig = (VirtualCameraConfig) Objects.requireNonNull(virtualCameraConfig);
    }

    public VirtualCameraConfig getConfig() {
        return this.mConfig;
    }

    public String getId() {
        return this.mCameraId;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.mVirtualDevice.unregisterVirtualCamera(this.mConfig);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
