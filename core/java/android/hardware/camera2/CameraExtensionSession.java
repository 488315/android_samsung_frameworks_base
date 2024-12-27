package android.hardware.camera2;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class CameraExtensionSession implements AutoCloseable {

    public static abstract class ExtensionCaptureCallback {
        public void onCaptureStarted(CameraExtensionSession session, CaptureRequest request, long timestamp) {
        }

        public void onCaptureProcessStarted(CameraExtensionSession session, CaptureRequest request) {
        }

        public void onCaptureFailed(CameraExtensionSession session, CaptureRequest request) {
        }

        public void onCaptureFailed(CameraExtensionSession session, CaptureRequest request, int failure) {
        }

        public void onCaptureSequenceCompleted(CameraExtensionSession session, int sequenceId) {
        }

        public void onCaptureSequenceAborted(CameraExtensionSession session, int sequenceId) {
        }

        public void onCaptureResultAvailable(CameraExtensionSession session, CaptureRequest request, TotalCaptureResult result) {
        }

        public void onCaptureProcessProgressed(CameraExtensionSession session, CaptureRequest request, int progress) {
        }
    }

    public static abstract class StateCallback {
        public abstract void onConfigureFailed(CameraExtensionSession cameraExtensionSession);

        public abstract void onConfigured(CameraExtensionSession cameraExtensionSession);

        public void onClosed(CameraExtensionSession session) {
        }
    }

    public CameraDevice getDevice() {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int capture(CaptureRequest request, Executor executor, ExtensionCaptureCallback listener) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int setRepeatingRequest(CaptureRequest request, Executor executor, ExtensionCaptureCallback listener) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public void stopRepeating() throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public static final class StillCaptureLatency {
        private final long mCaptureLatency;
        private final long mProcessingLatency;

        public StillCaptureLatency(long captureLatency, long processingLatency) {
            this.mCaptureLatency = captureLatency;
            this.mProcessingLatency = processingLatency;
        }

        public long getCaptureLatency() {
            return this.mCaptureLatency;
        }

        public long getProcessingLatency() {
            return this.mProcessingLatency;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            StillCaptureLatency latency = (StillCaptureLatency) o;
            if (this.mCaptureLatency == latency.mCaptureLatency && this.mProcessingLatency == latency.mProcessingLatency) {
                return true;
            }
            return false;
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
