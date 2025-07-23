package android.hardware.camera2;

import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Handler;
import android.view.Surface;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class CameraCaptureSession implements AutoCloseable {
    public static final int SESSION_ID_NONE = -1;

    public static abstract class CaptureCallback {
        public static final int NO_FRAMES_CAPTURED = -1;

        public void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long j) {
        }

        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        }

        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
        }

        public void onCapturePartial(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
        }

        public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
        }

        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
        }

        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
        }

        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
        }

        public void onReadoutStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
        }
    }

    public static abstract class StateCallback {
        public void onActive(CameraCaptureSession cameraCaptureSession) {
        }

        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
        }

        public void onClosed(CameraCaptureSession cameraCaptureSession) {
        }

        public abstract void onConfigureFailed(CameraCaptureSession cameraCaptureSession);

        public abstract void onConfigured(CameraCaptureSession cameraCaptureSession);

        public void onReady(CameraCaptureSession cameraCaptureSession) {
        }

        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
        }
    }

    public abstract void abortCaptures() throws CameraAccessException;

    public abstract int capture(CaptureRequest captureRequest, CaptureCallback captureCallback, Handler handler) throws CameraAccessException;

    public abstract int captureBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Handler handler) throws CameraAccessException;

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException;

    public abstract CameraDevice getDevice();

    public abstract Surface getInputSurface();

    public abstract boolean isReprocessable();

    public abstract void prepare(int i, Surface surface) throws CameraAccessException;

    public abstract void prepare(Surface surface) throws CameraAccessException;

    public abstract int setRepeatingBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Handler handler) throws CameraAccessException;

    public abstract int setRepeatingRequest(CaptureRequest captureRequest, CaptureCallback captureCallback, Handler handler) throws CameraAccessException;

    public abstract void stopRepeating() throws CameraAccessException;

    public abstract void tearDown(Surface surface) throws CameraAccessException;

    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public CameraOfflineSession switchToOffline(Collection<Surface> collection, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public boolean supportsOfflineProcessing(Surface surface) {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }
}
