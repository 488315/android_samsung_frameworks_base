package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
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

    public abstract ExtensionConfiguration initSession(IBinder iBinder, String str, CharacteristicsMap characteristicsMap, CameraOutputSurface cameraOutputSurface, CameraOutputSurface cameraOutputSurface2);

    public abstract void onCaptureSessionEnd();

    public abstract void onCaptureSessionStart(RequestProcessor requestProcessor, String str);

    public abstract void setParameters(CaptureRequest captureRequest);

    public abstract int startMultiFrameCapture(Executor executor, CaptureCallback captureCallback);

    public abstract int startRepeating(Executor executor, CaptureCallback captureCallback);

    public abstract int startTrigger(CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback);

    public abstract void stopRepeating();

    void setCameraUsageTracker(CameraUsageTracker cameraUsageTracker) {
        this.mCameraUsageTracker = cameraUsageTracker;
    }

    private final class SessionProcessorImpl extends ISessionProcessorImpl.Stub {
        OutputSurface mImageCaptureSurface;
        OutputSurface mPostviewSurface;
        OutputSurface mPreviewSurface;
        private long mVendorId;

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
            return null;
        }

        private SessionProcessorImpl() {
            this.mVendorId = -1L;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public CameraSessionConfig initSession(IBinder iBinder, String str, Map<String, CameraMetadataNative> map, OutputSurface outputSurface, OutputSurface outputSurface2, OutputSurface outputSurface3) throws RemoteException {
            this.mPreviewSurface = outputSurface;
            this.mPostviewSurface = outputSurface3;
            this.mImageCaptureSurface = outputSurface2;
            ExtensionConfiguration initSession = SessionProcessor.this.initSession(iBinder, str, new CharacteristicsMap(map), new CameraOutputSurface(outputSurface), new CameraOutputSurface(outputSurface2));
            if (initSession == null) {
                throw new IllegalArgumentException("Invalid extension configuration");
            }
            ArrayList allVendorKeys = map.get(str).getAllVendorKeys(CameraCharacteristics.Key.class);
            if (allVendorKeys != null && !allVendorKeys.isEmpty()) {
                this.mVendorId = ((CameraCharacteristics.Key) allVendorKeys.get(0)).getVendorId();
            }
            return initSession.getCameraSessionConfig();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void deInitSession(IBinder iBinder) throws RemoteException {
            SessionProcessor.this.deInitSession(iBinder);
            OutputSurface outputSurface = this.mPreviewSurface;
            if (outputSurface != null && outputSurface.surface != null) {
                this.mPreviewSurface.surface.release();
            }
            OutputSurface outputSurface2 = this.mImageCaptureSurface;
            if (outputSurface2 != null && outputSurface2.surface != null) {
                this.mImageCaptureSurface.surface.release();
            }
            OutputSurface outputSurface3 = this.mPostviewSurface;
            if (outputSurface3 == null || outputSurface3.surface == null) {
                return;
            }
            this.mPostviewSurface.surface.release();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl, String str) throws RemoteException {
            if (SessionProcessor.this.mCameraUsageTracker != null) {
                SessionProcessor.this.mCameraUsageTracker.startCameraOperation();
            }
            SessionProcessor.this.onCaptureSessionStart(new RequestProcessor(iRequestProcessorImpl, this.mVendorId), str);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionEnd() throws RemoteException {
            if (SessionProcessor.this.mCameraUsageTracker != null) {
                SessionProcessor.this.mCameraUsageTracker.finishCameraOperation();
            }
            SessionProcessor.this.onCaptureSessionEnd();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException {
            return SessionProcessor.this.startRepeating(new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())), new CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void stopRepeating() throws RemoteException {
            SessionProcessor.this.stopRepeating();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException {
            return SessionProcessor.this.startMultiFrameCapture(new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())), new CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void setParameters(CaptureRequest captureRequest) throws RemoteException {
            SessionProcessor.this.setParameters(captureRequest);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback) throws RemoteException {
            return SessionProcessor.this.startTrigger(captureRequest, new CameraExtensionUtils.HandlerExecutor(new Handler(Looper.getMainLooper())), new CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }
    }

    private static final class CaptureCallbackImpl implements CaptureCallback {
        private final ICaptureCallback mCaptureCallback;
        private long mVendorId;

        CaptureCallbackImpl(ICaptureCallback iCaptureCallback, long j) {
            this.mCaptureCallback = iCaptureCallback;
            this.mVendorId = j;
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i, long j) {
            try {
                this.mCaptureCallback.onCaptureStarted(i, j);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify capture start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int i) {
            try {
                this.mCaptureCallback.onCaptureProcessStarted(i);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify process start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i, int i2) {
            try {
                this.mCaptureCallback.onCaptureProcessFailed(i, i2);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify capture failure start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i) {
            try {
                this.mCaptureCallback.onCaptureSequenceCompleted(i);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify capture sequence done due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int i) {
            try {
                this.mCaptureCallback.onCaptureSequenceAborted(i);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify capture sequence abort due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j, int i, Map<CaptureResult.Key, Object> map) {
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            cameraMetadataNative.setVendorId(this.mVendorId);
            for (Map.Entry<CaptureResult.Key, Object> entry : map.entrySet()) {
                cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key>) entry.getKey(), (CaptureResult.Key) entry.getValue());
            }
            try {
                this.mCaptureCallback.onCaptureCompleted(j, i, cameraMetadataNative);
            } catch (RemoteException unused) {
                Log.e(SessionProcessor.TAG, "Failed to notify capture complete due to remote exception!");
            }
        }
    }

    ISessionProcessorImpl getSessionProcessorBinder() {
        return new SessionProcessorImpl();
    }
}
