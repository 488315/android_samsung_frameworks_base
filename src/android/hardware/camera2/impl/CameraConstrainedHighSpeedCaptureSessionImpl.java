package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.ConditionVariable;
import android.os.Handler;
import android.util.Log;
import android.util.Range;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CameraConstrainedHighSpeedCaptureSessionImpl extends CameraConstrainedHighSpeedCaptureSession implements CameraCaptureSessionCore {
    private final String TAG;
    private final CameraCharacteristics mCharacteristics;
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

    CameraConstrainedHighSpeedCaptureSessionImpl(int i, CameraCaptureSession.StateCallback stateCallback, Executor executor, CameraDeviceImpl cameraDeviceImpl, Executor executor2, boolean z, CameraCharacteristics cameraCharacteristics) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.mInitialized = conditionVariable;
        this.TAG = "CameraConstrainedHighSpeedCaptureSessionImpl";
        this.mCharacteristics = cameraCharacteristics;
        this.mSessionImpl = new CameraCaptureSessionImpl(i, null, new WrapperCallback(stateCallback), executor, cameraDeviceImpl, executor2, z);
        conditionVariable.open();
    }

    @Override // android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession
    public List<CaptureRequest> createHighSpeedRequestList(CaptureRequest captureRequest) throws CameraAccessException {
        CaptureRequest.Builder builder;
        if (captureRequest == null) {
            throw new IllegalArgumentException("Input capture request must not be null");
        }
        CameraCharacteristics.Key<StreamConfigurationMap> key = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP;
        Integer num = (Integer) captureRequest.get(CaptureRequest.SENSOR_PIXEL_MODE);
        if (num != null && num.intValue() == 1) {
            key = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;
        }
        Collection<Surface> targets = captureRequest.getTargets();
        Range range = (Range) captureRequest.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristics.get(key);
        SurfaceUtils.checkConstrainedHighSpeedSurfaces(targets, range, streamConfigurationMap);
        Range<Integer>[] highSpeedVideoFpsRangesFor = streamConfigurationMap.getHighSpeedVideoFpsRangesFor(SurfaceUtils.getSurfaceSize(targets.iterator().next()));
        Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "High speed fps ranges: " + Arrays.toString(highSpeedVideoFpsRangesFor));
        int i = Integer.MAX_VALUE;
        for (Range<Integer> range2 : highSpeedVideoFpsRangesFor) {
            int intValue = range2.getLower().intValue();
            if (i > intValue) {
                i = intValue;
            }
        }
        if (i != 60 && i != 30) {
            Log.w("CameraConstrainedHighSpeedCaptureSessionImpl", "previewFps is neither 60 nor 30.");
            i = 30;
        }
        Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "previewFps: " + i);
        int intValue2 = ((Integer) range.getUpper()).intValue() / i;
        if (((Integer) range.getUpper()).intValue() > ((Integer) range.getLower()).intValue()) {
            intValue2 = 1;
        }
        Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "Request list size is: " + intValue2);
        ArrayList arrayList = new ArrayList();
        CaptureRequest.Builder builder2 = new CaptureRequest.Builder(new CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest.getLogicalCameraId(), null);
        builder2.setTag(captureRequest.getTag());
        Iterator<Surface> it = targets.iterator();
        Surface next = it.next();
        if (targets.size() == 1 && SurfaceUtils.isSurfaceForHwVideoEncoder(next)) {
            builder2.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
        } else {
            builder2.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
        }
        builder2.setPartOfCHSRequestList(true);
        if (targets.size() == 2) {
            builder = new CaptureRequest.Builder(new CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest.getLogicalCameraId(), null);
            builder.setTag(captureRequest.getTag());
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
            builder.addTarget(next);
            Surface next2 = it.next();
            builder.addTarget(next2);
            builder.setPartOfCHSRequestList(true);
            if (!SurfaceUtils.isSurfaceForHwVideoEncoder(next)) {
                next = next2;
            }
            builder2.addTarget(next);
        } else {
            builder2.addTarget(next);
            builder = null;
        }
        for (int i2 = 0; i2 < intValue2; i2++) {
            if (i2 == 0 && builder != null) {
                arrayList.add(builder.build());
            } else {
                arrayList.add(builder2.build());
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private boolean isConstrainedHighSpeedRequestList(List<CaptureRequest> list) {
        Preconditions.checkCollectionNotEmpty(list, "High speed request list");
        Iterator<CaptureRequest> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().isPartOfCRequestList()) {
                return false;
            }
        }
        return true;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraDevice getDevice() {
        return this.mSessionImpl.getDevice();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(Surface surface) throws CameraAccessException {
        this.mSessionImpl.prepare(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, Surface surface) throws CameraAccessException {
        this.mSessionImpl.prepare(i, surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(Surface surface) throws CameraAccessException {
        this.mSessionImpl.tearDown(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.captureBurst(list, captureCallback, handler);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.captureBurstRequests(list, executor, captureCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.setRepeatingBurst(list, captureCallback, handler);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.setRepeatingBurstRequests(list, executor, captureCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws CameraAccessException {
        this.mSessionImpl.stopRepeating();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws CameraAccessException {
        this.mSessionImpl.abortCaptures();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraOfflineSession switchToOffline(Collection<Surface> collection, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(Surface surface) {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support offline mode");
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void closeWithoutDraining() {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        this.mSessionImpl.close();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void replaceSessionClose() {
        this.mSessionImpl.replaceSessionClose();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        return this.mSessionImpl.getDeviceStateCallback();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public boolean isAborting() {
        return this.mSessionImpl.isAborting();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException {
        this.mSessionImpl.finalizeOutputConfigurations(list);
    }

    private class WrapperCallback extends CameraCaptureSession.StateCallback {
        private final CameraCaptureSession.StateCallback mCallback;

        public WrapperCallback(CameraCaptureSession.StateCallback stateCallback) {
            this.mCallback = stateCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigured(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigureFailed(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onReady(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onActive(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
            this.mCallback.onSurfacePrepared(CameraConstrainedHighSpeedCaptureSessionImpl.this, surface);
        }
    }
}
