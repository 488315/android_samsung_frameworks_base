package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.camera.flags.Flags;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public abstract class SessionProcessor {
    private static final String TAG = "SessionProcessor";
    private CameraUsageTracker mCameraUsageTracker;

    @SystemApi
    public interface CaptureCallback {
        void onCaptureCompleted(long j, int i, Map<CaptureResult.Key, Object> map);

        void onCaptureFailed(int i, int i2);

        void onCaptureProcessStarted(int i);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i);

        void onCaptureStarted(int i, long j);
    }

    public abstract void deInitSession(IBinder iBinder);

    public abstract ExtensionConfiguration initSession(
            IBinder iBinder,
            String str,
            CharacteristicsMap characteristicsMap,
            CameraOutputSurface cameraOutputSurface,
            CameraOutputSurface cameraOutputSurface2);

    public abstract void onCaptureSessionEnd();

    public abstract void onCaptureSessionStart(RequestProcessor requestProcessor, String str);

    public abstract void setParameters(CaptureRequest captureRequest);

    public abstract int startMultiFrameCapture(Executor executor, CaptureCallback captureCallback);

    public abstract int startRepeating(Executor executor, CaptureCallback captureCallback);

    public abstract int startTrigger(
            CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback);

    public abstract void stopRepeating();

    void setCameraUsageTracker(CameraUsageTracker tracker) {
        this.mCameraUsageTracker = tracker;
    }

    private final class SessionProcessorImpl extends ISessionProcessorImpl.Stub {
        OutputSurface mImageCaptureSurface;
        OutputSurface mPostviewSurface;
        OutputSurface mPreviewSurface;
        private long mVendorId;

        private SessionProcessorImpl() {
            this.mVendorId = -1L;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public CameraSessionConfig initSession(
                IBinder token,
                String cameraId,
                Map<String, CameraMetadataNative> charsMap,
                OutputSurface previewSurface,
                OutputSurface imageCaptureSurface,
                OutputSurface postviewSurface)
                throws RemoteException {
            if (Flags.surfaceLeakFix()) {
                this.mPreviewSurface = previewSurface;
                this.mPostviewSurface = postviewSurface;
                this.mImageCaptureSurface = imageCaptureSurface;
            }
            ExtensionConfiguration config =
                    SessionProcessor.this.initSession(
                            token,
                            cameraId,
                            new CharacteristicsMap(charsMap),
                            new CameraOutputSurface(previewSurface),
                            new CameraOutputSurface(imageCaptureSurface));
            if (config == null) {
                throw new IllegalArgumentException("Invalid extension configuration");
            }
            ArrayList<CameraCharacteristics.Key<?>> vendorKeys =
                    charsMap.get(cameraId).getAllVendorKeys(keyClass);
            if (vendorKeys != null && !vendorKeys.isEmpty()) {
                this.mVendorId = vendorKeys.get(0).getVendorId();
            }
            return config.getCameraSessionConfig();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void deInitSession(IBinder token) throws RemoteException {
            SessionProcessor.this.deInitSession(token);
            if (Flags.surfaceLeakFix()) {
                if (this.mPreviewSurface != null && this.mPreviewSurface.surface != null) {
                    this.mPreviewSurface.surface.release();
                }
                if (this.mImageCaptureSurface != null
                        && this.mImageCaptureSurface.surface != null) {
                    this.mImageCaptureSurface.surface.release();
                }
                if (this.mPostviewSurface != null && this.mPostviewSurface.surface != null) {
                    this.mPostviewSurface.surface.release();
                }
            }
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionStart(IRequestProcessorImpl requestProcessor, String statsKey)
                throws RemoteException {
            if (SessionProcessor.this.mCameraUsageTracker != null) {
                SessionProcessor.this.mCameraUsageTracker.startCameraOperation();
            }
            SessionProcessor.this.onCaptureSessionStart(
                    new RequestProcessor(requestProcessor, this.mVendorId), statsKey);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionEnd() throws RemoteException {
            if (SessionProcessor.this.mCameraUsageTracker != null) {
                SessionProcessor.this.mCameraUsageTracker.finishCameraOperation();
            }
            SessionProcessor.this.onCaptureSessionEnd();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startRepeating(ICaptureCallback callback) throws RemoteException {
            return SessionProcessor.this.startRepeating(
                    new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())),
                    new CaptureCallbackImpl(callback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void stopRepeating() throws RemoteException {
            SessionProcessor.this.stopRepeating();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startCapture(ICaptureCallback callback, boolean isPostviewRequested)
                throws RemoteException {
            return SessionProcessor.this.startMultiFrameCapture(
                    new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())),
                    new CaptureCallbackImpl(callback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void setParameters(CaptureRequest captureRequest) throws RemoteException {
            SessionProcessor.this.setParameters(captureRequest);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startTrigger(CaptureRequest captureRequest, ICaptureCallback callback)
                throws RemoteException {
            return SessionProcessor.this.startTrigger(
                    captureRequest,
                    new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())),
                    new CaptureCallbackImpl(callback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
            return null;
        }
    }

    private static final class CaptureCallbackImpl implements CaptureCallback {
        private final ICaptureCallback mCaptureCallback;
        private long mVendorId;

        CaptureCallbackImpl(ICaptureCallback cb, long vendorId) {
            this.mVendorId = -1L;
            this.mCaptureCallback = cb;
            this.mVendorId = vendorId;
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int captureSequenceId, long timestamp) {
            try {
                this.mCaptureCallback.onCaptureStarted(captureSequenceId, timestamp);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify capture start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int captureSequenceId) {
            try {
                this.mCaptureCallback.onCaptureProcessStarted(captureSequenceId);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify process start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int captureSequenceId, int failure) {
            try {
                this.mCaptureCallback.onCaptureProcessFailed(captureSequenceId, failure);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify capture failure start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int captureSequenceId) {
            try {
                this.mCaptureCallback.onCaptureSequenceCompleted(captureSequenceId);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify capture sequence done due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int captureSequenceId) {
            try {
                this.mCaptureCallback.onCaptureSequenceAborted(captureSequenceId);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify capture sequence abort due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(
                long shutterTimestamp, int requestId, Map<CaptureResult.Key, Object> results) {
            CameraMetadataNative captureResults = new CameraMetadataNative();
            captureResults.setVendorId(this.mVendorId);
            for (Map.Entry<CaptureResult.Key, Object> entry : results.entrySet()) {
                captureResults.set(
                        (CaptureResult.Key<CaptureResult.Key>) entry.getKey(),
                        (CaptureResult.Key) entry.getValue());
            }
            try {
                this.mCaptureCallback.onCaptureCompleted(
                        shutterTimestamp, requestId, captureResults);
            } catch (RemoteException e) {
                Log.e(
                        SessionProcessor.TAG,
                        "Failed to notify capture complete due to remote exception!");
            }
        }
    }

    ISessionProcessorImpl getSessionProcessorBinder() {
        return new SessionProcessorImpl();
    }
}
