package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.ICameraOfflineSession;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.ExceptionUtils;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.view.Surface;

/* loaded from: classes2.dex */
public class ICameraDeviceUserWrapper {
    private final ICameraDeviceUser mRemoteDevice;

    public ICameraDeviceUserWrapper(ICameraDeviceUser remoteDevice) {
        if (remoteDevice == null) {
            throw new NullPointerException("Remote device may not be null");
        }
        this.mRemoteDevice = remoteDevice;
    }

    public void unlinkToDeath(IBinder.DeathRecipient recipient, int flags) {
        if (this.mRemoteDevice.asBinder() != null) {
            this.mRemoteDevice.asBinder().unlinkToDeath(recipient, flags);
        }
    }

    public void disconnect() {
        try {
            this.mRemoteDevice.disconnect();
        } catch (RemoteException e) {
        }
    }

    public SubmitInfo submitRequest(CaptureRequest request, boolean streaming)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequest(request, streaming);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public SubmitInfo submitRequestList(CaptureRequest[] requestList, boolean streaming)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequestList(requestList, streaming);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public long cancelRequest(int requestId) throws CameraAccessException {
        try {
            return this.mRemoteDevice.cancelRequest(requestId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void beginConfigure() throws CameraAccessException {
        try {
            this.mRemoteDevice.beginConfigure();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int[] endConfigure(
            int operatingMode, CameraMetadataNative sessionParams, long startTimeMs)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.endConfigure(
                    operatingMode,
                    sessionParams == null ? new CameraMetadataNative() : sessionParams,
                    startTimeMs);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void deleteStream(int streamId) throws CameraAccessException {
        try {
            this.mRemoteDevice.deleteStream(streamId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int createStream(OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            return this.mRemoteDevice.createStream(outputConfiguration);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int createInputStream(int width, int height, int format, boolean isMultiResolution)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.createInputStream(width, height, format, isMultiResolution);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public Surface getInputSurface() throws CameraAccessException {
        try {
            return this.mRemoteDevice.getInputSurface();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public CameraMetadataNative createDefaultRequest(int templateId) throws CameraAccessException {
        try {
            return this.mRemoteDevice.createDefaultRequest(templateId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public CameraMetadataNative getCameraInfo() throws CameraAccessException {
        try {
            return this.mRemoteDevice.getCameraInfo();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void waitUntilIdle() throws CameraAccessException {
        try {
            this.mRemoteDevice.waitUntilIdle();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfig)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfig);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 10) {
                throw new UnsupportedOperationException(
                        "Session configuration query not supported");
            }
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException("Invalid session configuration");
            }
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void setParameters(String params) throws CameraAccessException {
        try {
            this.mRemoteDevice.setParameters(params);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public long flush() throws CameraAccessException {
        try {
            return this.mRemoteDevice.flush();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void prepare(int streamId) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare(streamId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void tearDown(int streamId) throws CameraAccessException {
        try {
            this.mRemoteDevice.tearDown(streamId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void prepare2(int maxCount, int streamId) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare2(maxCount, streamId);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void updateOutputConfiguration(int streamId, OutputConfiguration config)
            throws CameraAccessException {
        try {
            this.mRemoteDevice.updateOutputConfiguration(streamId, config);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks cbs, int[] offlineOutputIds)
            throws CameraAccessException {
        try {
            return this.mRemoteDevice.switchToOffline(cbs, offlineOutputIds);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void finalizeOutputConfigurations(int streamId, OutputConfiguration deferredConfig)
            throws CameraAccessException {
        try {
            this.mRemoteDevice.finalizeOutputConfigurations(streamId, deferredConfig);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void setCameraAudioRestriction(int mode) throws CameraAccessException {
        try {
            this.mRemoteDevice.setCameraAudioRestriction(mode);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int getGlobalAudioRestriction() throws CameraAccessException {
        try {
            return this.mRemoteDevice.getGlobalAudioRestriction();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }
}
