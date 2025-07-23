package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.CameraSharedCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.ConditionVariable;
import android.os.Handler;
import android.view.Surface;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CameraSharedCaptureSessionImpl extends CameraSharedCaptureSession implements CameraCaptureSessionCore {
    private static final String TAG = "CameraSharedCaptureSessionImpl";
    private final CameraDeviceImpl mCameraDevice;
    private final Executor mDeviceExecutor;
    private final ConditionVariable mInitialized;
    private final CameraCaptureSessionImpl mSessionImpl;

    @Override // android.hardware.camera2.CameraCaptureSession
    public Surface getInputSurface() {
        return null;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean isReprocessable() {
        return false;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(Surface surface) {
        return false;
    }

    CameraSharedCaptureSessionImpl(int i, CameraCaptureSession.StateCallback stateCallback, Executor executor, CameraDeviceImpl cameraDeviceImpl, Executor executor2, boolean z) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.mInitialized = conditionVariable;
        this.mSessionImpl = new CameraCaptureSessionImpl(i, null, new WrapperCallback(stateCallback), executor, cameraDeviceImpl, executor2, z);
        this.mCameraDevice = cameraDeviceImpl;
        this.mDeviceExecutor = executor2;
        conditionVariable.open();
    }

    @Override // android.hardware.camera2.CameraSharedCaptureSession
    public int startStreaming(List<Surface> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("No surfaces provided for streaming");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        return this.mSessionImpl.startStreaming(list, executor, captureCallback);
    }

    @Override // android.hardware.camera2.CameraSharedCaptureSession
    public void stopStreaming() throws CameraAccessException {
        this.mSessionImpl.stopRepeating();
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        this.mSessionImpl.close();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            this.mSessionImpl.abortCaptures();
            return;
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            return this.mSessionImpl.setRepeatingRequest(captureRequest, captureCallback, handler);
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            return this.mSessionImpl.setSingleRepeatingRequest(captureRequest, executor, captureCallback);
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            this.mSessionImpl.stopRepeating();
            return;
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            return this.mSessionImpl.capture(captureRequest, captureCallback, handler);
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (this.mCameraDevice.isPrimaryClient()) {
            return this.mSessionImpl.captureSingleRequest(captureRequest, executor, captureCallback);
        }
        throw new UnsupportedOperationException("Shared capture session only supports this method for primary clients");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(Surface surface) throws CameraAccessException {
        this.mSessionImpl.tearDown(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraDevice getDevice() {
        return this.mSessionImpl.getDevice();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public boolean isAborting() {
        return this.mSessionImpl.isAborting();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        return this.mSessionImpl.getDeviceStateCallback();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void replaceSessionClose() {
        this.mSessionImpl.replaceSessionClose();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraOfflineSession switchToOffline(Collection<Surface> collection, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared Capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared Capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared Capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared Capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(Surface surface) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, Surface surface) throws CameraAccessException {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void closeWithoutDraining() {
        throw new UnsupportedOperationException("Shared capture session do not support this method");
    }

    private class WrapperCallback extends CameraCaptureSession.StateCallback {
        private final CameraCaptureSession.StateCallback mCallback;

        WrapperCallback(CameraCaptureSession.StateCallback stateCallback) {
            this.mCallback = stateCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            CameraSharedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigured(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraSharedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigureFailed(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onReady(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onActive(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(CameraSharedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
            this.mCallback.onSurfacePrepared(CameraSharedCaptureSessionImpl.this, surface);
        }
    }
}
