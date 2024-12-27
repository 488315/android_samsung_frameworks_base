package com.android.server.companion.virtual.camera;

import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtualcamera.IVirtualCameraService;
import android.companion.virtualcamera.SupportedStreamConfiguration;
import android.companion.virtualcamera.VirtualCameraConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;

import java.util.Iterator;
import java.util.Map;

public final class VirtualCameraController implements IBinder.DeathRecipient {
    public final int mCameraPolicy;
    public final int mDeviceId;
    public IVirtualCameraService mVirtualCameraService;
    public final Object mServiceLock = new Object();
    public final Map mCameras = new ArrayMap();

    public final class CameraDescriptor implements IBinder.DeathRecipient {
        public final VirtualCameraConfig mConfig;

        public CameraDescriptor(VirtualCameraConfig virtualCameraConfig) {
            this.mConfig = virtualCameraConfig;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.d("VirtualCameraController", "Virtual camera binder died");
            VirtualCameraController.this.unregisterCamera(this.mConfig);
        }
    }

    public VirtualCameraController(IVirtualCameraService iVirtualCameraService, int i, int i2) {
        this.mVirtualCameraService = iVirtualCameraService;
        this.mCameraPolicy = i;
        this.mDeviceId = i2;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.d("VirtualCameraController", "Virtual camera service died.");
        synchronized (this.mServiceLock) {
            this.mVirtualCameraService = null;
        }
        synchronized (this.mCameras) {
            ((ArrayMap) this.mCameras).clear();
        }
    }

    public final void close() {
        synchronized (this.mCameras) {
            if (!((ArrayMap) this.mCameras).isEmpty()) {
                connectVirtualCameraServiceIfNeeded();
                synchronized (this.mServiceLock) {
                    Iterator it = ((ArrayMap) this.mCameras).keySet().iterator();
                    while (it.hasNext()) {
                        try {
                            ((IVirtualCameraService.Stub.Proxy) this.mVirtualCameraService)
                                    .unregisterCamera((IBinder) it.next());
                        } catch (RemoteException e) {
                            Slog.w(
                                    "VirtualCameraController",
                                    "close(): Camera failed to be removed on camera service.",
                                    e);
                        }
                    }
                }
                ((ArrayMap) this.mCameras).clear();
            }
        }
        synchronized (this.mServiceLock) {
            this.mVirtualCameraService = null;
        }
    }

    public final void connectVirtualCameraService() {
        IBinder waitForService;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                waitForService = ServiceManager.waitForService("virtual_camera");
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (waitForService == null) {
                Slog.e(
                        "VirtualCameraController",
                        "connectVirtualCameraService: Failed to connect to the virtual camera"
                            + " service");
            } else {
                waitForService.linkToDeath(this, 0);
                this.mVirtualCameraService = IVirtualCameraService.Stub.asInterface(waitForService);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void connectVirtualCameraServiceIfNeeded() {
        synchronized (this.mServiceLock) {
            try {
                if (this.mVirtualCameraService == null) {
                    connectVirtualCameraService();
                }
                if (this.mVirtualCameraService == null) {
                    throw new IllegalStateException("Virtual camera service is not connected.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean registerCameraWithService(VirtualCameraConfig virtualCameraConfig) {
        boolean registerCamera;
        VirtualCameraConfiguration virtualCameraConfiguration = new VirtualCameraConfiguration();
        virtualCameraConfiguration.supportedStreamConfigs =
                (SupportedStreamConfiguration[])
                        virtualCameraConfig.getStreamConfigs().stream()
                                .map(new VirtualCameraConversionUtil$$ExternalSyntheticLambda0())
                                .toArray(
                                        new VirtualCameraConversionUtil$$ExternalSyntheticLambda1());
        virtualCameraConfiguration.sensorOrientation = virtualCameraConfig.getSensorOrientation();
        virtualCameraConfiguration.lensFacing = virtualCameraConfig.getLensFacing();
        virtualCameraConfiguration.virtualCameraCallback =
                new VirtualCameraConversionUtil$1(virtualCameraConfig.getCallback());
        synchronized (this.mServiceLock) {
            registerCamera =
                    ((IVirtualCameraService.Stub.Proxy) this.mVirtualCameraService)
                            .registerCamera(
                                    virtualCameraConfig.getCallback().asBinder(),
                                    virtualCameraConfiguration,
                                    this.mDeviceId);
        }
        return registerCamera;
    }

    public final void unregisterCamera(VirtualCameraConfig virtualCameraConfig) {
        synchronized (this.mCameras) {
            try {
                IBinder asBinder = virtualCameraConfig.getCallback().asBinder();
                if (((ArrayMap) this.mCameras).containsKey(asBinder)) {
                    connectVirtualCameraServiceIfNeeded();
                    try {
                        synchronized (this.mServiceLock) {
                            ((IVirtualCameraService.Stub.Proxy) this.mVirtualCameraService)
                                    .unregisterCamera(asBinder);
                        }
                        ((ArrayMap) this.mCameras).remove(asBinder);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                } else {
                    Slog.w("VirtualCameraController", "Virtual camera was not registered.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
