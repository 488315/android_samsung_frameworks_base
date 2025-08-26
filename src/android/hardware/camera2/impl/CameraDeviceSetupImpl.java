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
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CameraDeviceSetupImpl extends CameraDevice.CameraDeviceSetup {
    private final String mCameraId;
    private final CameraManager mCameraManager;
    private final Context mContext;
    private final Object mInterfaceLock = new Object();
    private final int mTargetSdkVersion;

    public CameraDeviceSetupImpl(String str, CameraManager cameraManager, Context context) {
        this.mCameraId = str;
        this.mCameraManager = cameraManager;
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public CaptureRequest.Builder createCaptureRequest(int i) throws CameraAccessException {
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
                    CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = cameraService.createDefaultRequest(this.mCameraId, i, this.mCameraManager.getClientAttribution(), this.mCameraManager.getDevicePolicyFromContext(this.mContext));
                    CameraDeviceImpl.disableZslIfNeeded(cameraMetadataNativeCreateDefaultRequest, this.mTargetSdkVersion, i);
                    builder = new CaptureRequest.Builder(cameraMetadataNativeCreateDefaultRequest, false, -1, this.mCameraId, null);
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
    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        boolean zIsSessionConfigurationWithParametersSupported;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new IllegalArgumentException("No cameras available on device");
            }
            ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable.");
            }
            try {
                zIsSessionConfigurationWithParametersSupported = cameraService.isSessionConfigurationWithParametersSupported(this.mCameraId, this.mTargetSdkVersion, sessionConfiguration, this.mCameraManager.getClientAttribution(), this.mCameraManager.getDevicePolicyFromContext(this.mContext));
            } catch (RemoteException e) {
                throw ExceptionUtils.throwAsPublicException(e);
            } catch (ServiceSpecificException e2) {
                throw ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return zIsSessionConfigurationWithParametersSupported;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public CameraCharacteristics getSessionCharacteristics(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristicsPrepareCameraCharacteristics;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new CameraAccessException(2, "Camera service is currently disabled");
            }
            ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                try {
                    cameraCharacteristicsPrepareCameraCharacteristics = this.mCameraManager.prepareCameraCharacteristics(this.mCameraId, cameraService.getSessionCharacteristics(this.mCameraId, this.mTargetSdkVersion, CameraManager.getRotationOverride(this.mContext), sessionConfiguration, this.mCameraManager.getClientAttribution(), this.mCameraManager.getDevicePolicyFromContext(this.mContext)), cameraService);
                } catch (ServiceSpecificException e) {
                    int i = e.errorCode;
                    if (i == 3) {
                        throw new IllegalArgumentException("Invalid Session Configuration");
                    }
                    if (i == 10) {
                        throw new UnsupportedOperationException("Session Characteristics Query not supported by device.");
                    }
                    throw ExceptionUtils.throwAsPublicException(e);
                }
            } catch (RemoteException e2) {
                throw ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return cameraCharacteristicsPrepareCameraCharacteristics;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public void openCamera(Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        this.mCameraManager.openCamera(this.mCameraId, executor, stateCallback);
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
            return this.mCameraId.equals(((CameraDeviceSetupImpl) obj).mCameraId);
        }
        return false;
    }

    public String toString() {
        return "CameraDeviceSetup(cameraId='" + this.mCameraId + "')";
    }

    public static boolean isCameraDeviceSetupSupported(CameraCharacteristics cameraCharacteristics) {
        Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SESSION_CONFIGURATION_QUERY_VERSION);
        return num != null && num.intValue() > 34;
    }
}
