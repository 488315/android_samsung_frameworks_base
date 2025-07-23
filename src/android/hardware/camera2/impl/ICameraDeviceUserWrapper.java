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
import android.hardware.common.fmq.MQDescriptor;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.view.Surface;

/* loaded from: classes2.dex */
public class ICameraDeviceUserWrapper {
    private final ICameraDeviceUser mRemoteDevice;

    public ICameraDeviceUserWrapper(ICameraDeviceUser iCameraDeviceUser) {
        if (iCameraDeviceUser == null) {
            throw new NullPointerException("Remote device may not be null");
        }
        this.mRemoteDevice = iCameraDeviceUser;
    }

    public void unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        if (this.mRemoteDevice.asBinder() != null) {
            this.mRemoteDevice.asBinder().unlinkToDeath(deathRecipient, i);
        }
    }

    public void disconnect() {
        try {
            this.mRemoteDevice.disconnect();
        } catch (RemoteException unused) {
        }
    }

    public SubmitInfo startStreaming(int[] iArr, int[] iArr2) throws CameraAccessException {
        try {
            return this.mRemoteDevice.startStreaming(iArr, iArr2);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequest(captureRequest, z);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequestList(captureRequestArr, z);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public long cancelRequest(int i) throws CameraAccessException {
        try {
            return this.mRemoteDevice.cancelRequest(i);
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

    public int[] endConfigure(int i, CameraMetadataNative cameraMetadataNative, long j) throws CameraAccessException {
        try {
            ICameraDeviceUser iCameraDeviceUser = this.mRemoteDevice;
            if (cameraMetadataNative == null) {
                cameraMetadataNative = new CameraMetadataNative();
            }
            return iCameraDeviceUser.endConfigure(i, cameraMetadataNative, j);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void deleteStream(int i) throws CameraAccessException {
        try {
            this.mRemoteDevice.deleteStream(i);
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

    public int createInputStream(int i, int i2, int i3, boolean z) throws CameraAccessException {
        try {
            return this.mRemoteDevice.createInputStream(i, i2, i3, z);
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

    public CameraMetadataNative createDefaultRequest(int i) throws CameraAccessException {
        try {
            return this.mRemoteDevice.createDefaultRequest(i);
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

    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        try {
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 10) {
                throw new UnsupportedOperationException("Session configuration query not supported");
            }
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException("Invalid session configuration");
            }
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void setParameters(String str) throws CameraAccessException {
        try {
            this.mRemoteDevice.setParameters(str);
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

    public void prepare(int i) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare(i);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void tearDown(int i) throws CameraAccessException {
        try {
            this.mRemoteDevice.tearDown(i);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void prepare2(int i, int i2) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare2(i, i2);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            this.mRemoteDevice.updateOutputConfiguration(i, outputConfiguration);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws CameraAccessException {
        try {
            return this.mRemoteDevice.switchToOffline(iCameraDeviceCallbacks, iArr);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            this.mRemoteDevice.finalizeOutputConfigurations(i, outputConfiguration);
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void setCameraAudioRestriction(int i) throws CameraAccessException {
        try {
            this.mRemoteDevice.setCameraAudioRestriction(i);
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

    public boolean isPrimaryClient() throws CameraAccessException {
        try {
            return this.mRemoteDevice.isPrimaryClient();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public MQDescriptor<Byte, Byte> getCaptureResultMetadataQueue() throws CameraAccessException {
        try {
            return this.mRemoteDevice.getCaptureResultMetadataQueue();
        } catch (RemoteException e) {
            throw ExceptionUtils.throwAsPublicException(e);
        } catch (ServiceSpecificException e2) {
            throw ExceptionUtils.throwAsPublicException(e2);
        }
    }
}
