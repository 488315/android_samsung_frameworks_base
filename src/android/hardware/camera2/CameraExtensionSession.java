package android.hardware.camera2;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class CameraExtensionSession implements AutoCloseable {

    public static abstract class ExtensionCaptureCallback {
        public void onCaptureFailed(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest) {
        }

        public void onCaptureFailed(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest, int i) {
        }

        public void onCaptureProcessProgressed(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest, int i) {
        }

        public void onCaptureProcessStarted(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest) {
        }

        public void onCaptureResultAvailable(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        }

        public void onCaptureSequenceAborted(CameraExtensionSession cameraExtensionSession, int i) {
        }

        public void onCaptureSequenceCompleted(CameraExtensionSession cameraExtensionSession, int i) {
        }

        public void onCaptureStarted(CameraExtensionSession cameraExtensionSession, CaptureRequest captureRequest, long j) {
        }
    }

    public static abstract class StateCallback {
        public void onClosed(CameraExtensionSession cameraExtensionSession) {
        }

        public abstract void onConfigureFailed(CameraExtensionSession cameraExtensionSession);

        public abstract void onConfigured(CameraExtensionSession cameraExtensionSession);
    }

    public CameraDevice getDevice() {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int capture(CaptureRequest captureRequest, Executor executor, ExtensionCaptureCallback extensionCaptureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int setRepeatingRequest(CaptureRequest captureRequest, Executor executor, ExtensionCaptureCallback extensionCaptureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public void stopRepeating() throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public static final class StillCaptureLatency {
        private final long mCaptureLatency;
        private final long mProcessingLatency;

        public StillCaptureLatency(long j, long j2) {
            this.mCaptureLatency = j;
            this.mProcessingLatency = j2;
        }

        public long getCaptureLatency() {
            return this.mCaptureLatency;
        }

        public long getProcessingLatency() {
            return this.mProcessingLatency;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            StillCaptureLatency stillCaptureLatency = (StillCaptureLatency) obj;
            return this.mCaptureLatency == stillCaptureLatency.mCaptureLatency && this.mProcessingLatency == stillCaptureLatency.mProcessingLatency;
        }

        public int hashCode() {
            return HashCodeHelpers.hashCode(this.mCaptureLatency, this.mProcessingLatency);
        }

        public String toString() {
            return "StillCaptureLatency(processingLatency:" + this.mProcessingLatency + ", captureLatency: " + this.mCaptureLatency + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public StillCaptureLatency getRealtimeStillCaptureLatency() throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    @Override // java.lang.AutoCloseable
    public void close() throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }
}
