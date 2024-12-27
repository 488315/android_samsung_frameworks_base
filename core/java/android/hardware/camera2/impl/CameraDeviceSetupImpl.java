package android.hardware.camera2.impl;

import android.content.Context;
import android.hardware.ICameraService;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.ExceptionUtils;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

import com.android.internal.camera.flags.Flags;

import java.util.concurrent.Executor;

public class CameraDeviceSetupImpl extends CameraDevice.CameraDeviceSetup {
    private final String mCameraId;
    private final CameraManager mCameraManager;
    private final Context mContext;
    private final Object mInterfaceLock = new Object();
    private final int mTargetSdkVersion;

    public CameraDeviceSetupImpl(String cameraId, CameraManager cameraManager, Context context) {
        this.mCameraId = cameraId;
        this.mCameraManager = cameraManager;
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public CaptureRequest.Builder createCaptureRequest(int templateType)
            throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new IllegalArgumentException("No cameras available on device");
            }
            ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable.");
            }
            try {
                try {
                    CameraMetadataNative defaultRequest =
                            cameraService.createDefaultRequest(
                                    this.mCameraId,
                                    templateType,
                                    this.mContext.getDeviceId(),
                                    this.mCameraManager.getDevicePolicyFromContext(this.mContext));
                    CameraDeviceImpl.disableZslIfNeeded(
                            defaultRequest, this.mTargetSdkVersion, templateType);
                    builder =
                            new CaptureRequest.Builder(
                                    defaultRequest, false, -1, this.mCameraId, null);
                } catch (RemoteException e) {
                    throw ExceptionUtils.throwAsPublicException(e);
                }
            } catch (ServiceSpecificException e2) {
                throw ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public boolean isSessionConfigurationSupported(SessionConfiguration config)
            throws CameraAccessException {
        boolean isSessionConfigurationWithParametersSupported;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new IllegalArgumentException("No cameras available on device");
            }
            ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable.");
            }
            try {
                isSessionConfigurationWithParametersSupported =
                        cameraService.isSessionConfigurationWithParametersSupported(
                                this.mCameraId,
                                this.mTargetSdkVersion,
                                config,
                                this.mContext.getDeviceId(),
                                this.mCameraManager.getDevicePolicyFromContext(this.mContext));
            } catch (RemoteException e) {
                throw ExceptionUtils.throwAsPublicException(e);
            } catch (ServiceSpecificException e2) {
                throw ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return isSessionConfigurationWithParametersSupported;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public CameraCharacteristics getSessionCharacteristics(SessionConfiguration sessionConfig)
            throws CameraAccessException {
        CameraCharacteristics prepareCameraCharacteristics;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new CameraAccessException(2, "Camera service is currently disabled");
            }
            ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                CameraMetadataNative metadata =
                        cameraService.getSessionCharacteristics(
                                this.mCameraId,
                                this.mTargetSdkVersion,
                                CameraManager.getRotationOverride(this.mContext),
                                sessionConfig,
                                this.mContext.getDeviceId(),
                                this.mCameraManager.getDevicePolicyFromContext(this.mContext));
                prepareCameraCharacteristics =
                        this.mCameraManager.prepareCameraCharacteristics(
                                this.mCameraId, metadata, cameraService);
            } catch (RemoteException e) {
                throw ExceptionUtils.throwAsPublicException(e);
            } catch (ServiceSpecificException e2) {
                switch (e2.errorCode) {
                    case 3:
                        throw new IllegalArgumentException("Invalid Session Configuration");
                    case 10:
                        throw new UnsupportedOperationException(
                                "Session Characteristics Query not supported by device.");
                    default:
                        throw ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }
        return prepareCameraCharacteristics;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public void openCamera(Executor executor, CameraDevice.StateCallback callback)
            throws CameraAccessException {
        this.mCameraManager.openCamera(this.mCameraId, executor, callback);
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public String getId() {
        return this.mCameraId;
    }

    public int hashCode() {
        return this.mCameraId.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof CameraDeviceSetupImpl) {
            CameraDeviceSetupImpl other = (CameraDeviceSetupImpl) obj;
            return this.mCameraId.equals(other.mCameraId);
        }
        return false;
    }

    public String toString() {
        return "CameraDeviceSetup(cameraId='" + this.mCameraId + "')";
    }

    public static boolean isCameraDeviceSetupSupported(CameraCharacteristics chars) {
        Integer queryVersion;
        return Flags.featureCombinationQuery()
                && (queryVersion =
                                (Integer)
                                        chars.get(
                                                CameraCharacteristics
                                                        .INFO_SESSION_CONFIGURATION_QUERY_VERSION))
                        != null
                && queryVersion.intValue() > 34;
    }
}
