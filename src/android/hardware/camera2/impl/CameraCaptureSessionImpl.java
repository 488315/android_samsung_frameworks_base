package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.CallbackProxies;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.TaskDrainer;
import android.hardware.camera2.utils.TaskSingleDrainer;
import android.os.Binder;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CameraCaptureSessionImpl extends CameraCaptureSession implements CameraCaptureSessionCore {
    private static final boolean DEBUG = false;
    private static final String TAG = "CameraCaptureSession";
    private final TaskSingleDrainer mAbortDrainer;
    private volatile boolean mAborting;
    private boolean mClosed;
    private final boolean mConfigureSuccess;
    private final Executor mDeviceExecutor;
    private final CameraDeviceImpl mDeviceImpl;
    private final int mId;
    private final String mIdString;
    private final TaskSingleDrainer mIdleDrainer;
    private final Surface mInput;
    private final TaskDrainer<Integer> mSequenceDrainer;
    private boolean mSkipUnconfigure = false;
    private final CameraCaptureSession.StateCallback mStateCallback;
    private final Executor mStateExecutor;

    CameraCaptureSessionImpl(int i, Surface surface, CameraCaptureSession.StateCallback stateCallback, Executor executor, CameraDeviceImpl cameraDeviceImpl, Executor executor2, boolean z) {
        this.mClosed = false;
        if (stateCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        this.mId = i;
        String str = String.format("Session %d: ", Integer.valueOf(i));
        this.mIdString = str;
        this.mInput = surface;
        Executor executor3 = (Executor) Preconditions.checkNotNull(executor, "stateExecutor must not be null");
        this.mStateExecutor = executor3;
        CameraCaptureSession.StateCallback stateCallbackCreateUserStateCallbackProxy = createUserStateCallbackProxy(executor3, stateCallback);
        this.mStateCallback = stateCallbackCreateUserStateCallbackProxy;
        Executor executor4 = (Executor) Preconditions.checkNotNull(executor2, "deviceStateExecutor must not be null");
        this.mDeviceExecutor = executor4;
        this.mDeviceImpl = (CameraDeviceImpl) Preconditions.checkNotNull(cameraDeviceImpl, "deviceImpl must not be null");
        this.mSequenceDrainer = new TaskDrainer<>(executor4, new SequenceDrainListener(), "seq");
        this.mIdleDrainer = new TaskSingleDrainer(executor4, new IdleDrainListener(), "idle");
        this.mAbortDrainer = new TaskSingleDrainer(executor4, new AbortDrainListener(), "abort");
        if (z) {
            stateCallbackCreateUserStateCallbackProxy.onConfigured(this);
            this.mConfigureSuccess = true;
            return;
        }
        stateCallbackCreateUserStateCallbackProxy.onConfigureFailed(this);
        this.mClosed = true;
        Log.e(TAG, str + "Failed to create capture session; configuration failed");
        this.mConfigureSuccess = false;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraDevice getDevice() {
        return this.mDeviceImpl;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(Surface surface) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.prepare(surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, Surface surface) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.prepare(i, surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(Surface surface) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.tearDown(surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.finalizeOutputConfigs(list);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        int iAddPendingSequence;
        checkCaptureRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.capture(captureRequest, createCaptureCallbackProxy(CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        int iAddPendingSequence;
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCaptureRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.capture(captureRequest, createCaptureCallbackProxyWithExecutor(CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    private void checkCaptureRequest(CaptureRequest captureRequest) {
        if (captureRequest == null) {
            throw new IllegalArgumentException("request must not be null");
        }
        if (captureRequest.isReprocess() && !isReprocessable()) {
            throw new IllegalArgumentException("this capture session cannot handle reprocess requests");
        }
        if (captureRequest.isReprocess() && captureRequest.getReprocessableSessionId() != this.mId) {
            throw new IllegalArgumentException("capture request was created for another session");
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        int iAddPendingSequence;
        checkCaptureRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.captureBurst(list, createCaptureCallbackProxy(CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        int iAddPendingSequence;
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCaptureRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.captureBurst(list, createCaptureCallbackProxyWithExecutor(CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    private void checkCaptureRequests(List<CaptureRequest> list) {
        if (list == null) {
            throw new IllegalArgumentException("Requests must not be null");
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Requests must have at least one element");
        }
        for (CaptureRequest captureRequest : list) {
            if (captureRequest.isReprocess()) {
                if (!isReprocessable()) {
                    throw new IllegalArgumentException("This capture session cannot handle reprocess requests");
                }
                if (captureRequest.getReprocessableSessionId() != this.mId) {
                    throw new IllegalArgumentException("Capture request was created for another session");
                }
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        int iAddPendingSequence;
        checkRepeatingRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, createCaptureCallbackProxy(CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        int iAddPendingSequence;
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkRepeatingRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, createCaptureCallbackProxyWithExecutor(CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    public int startStreaming(List<Surface> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        int iAddPendingSequence;
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.startStreaming(list, createCaptureCallbackProxyWithExecutor(CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    private void checkRepeatingRequest(CaptureRequest captureRequest) {
        if (captureRequest == null) {
            throw new IllegalArgumentException("request must not be null");
        }
        if (captureRequest.isReprocess()) {
            throw new IllegalArgumentException("repeating reprocess requests are not supported");
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        int iAddPendingSequence;
        checkRepeatingRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, createCaptureCallbackProxy(CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        int iAddPendingSequence;
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkRepeatingRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            iAddPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, createCaptureCallbackProxyWithExecutor(CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return iAddPendingSequence;
    }

    private void checkRepeatingRequests(List<CaptureRequest> list) {
        if (list == null) {
            throw new IllegalArgumentException("requests must not be null");
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("requests must have at least one element");
        }
        Iterator<CaptureRequest> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isReprocess()) {
                throw new IllegalArgumentException("repeating reprocess burst requests are not supported");
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.stopRepeating();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            if (this.mAborting) {
                Log.w(TAG, this.mIdString + "abortCaptures - Session is already aborting; doing nothing");
                return;
            }
            this.mAborting = true;
            this.mAbortDrainer.taskStarted();
            this.mDeviceImpl.flush();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.updateOutputConfiguration(outputConfiguration);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public CameraOfflineSession switchToOffline(Collection<Surface> collection, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
        }
        return this.mDeviceImpl.switchToOffline(collection, executor, cameraOfflineSessionCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(Surface surface) {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
        }
        return this.mDeviceImpl.supportsOfflineProcessing(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean isReprocessable() {
        return this.mInput != null;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public Surface getInputSurface() {
        return this.mInput;
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void replaceSessionClose() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            this.mSkipUnconfigure = true;
            close();
        }
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void closeWithoutDraining() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            this.mStateCallback.onClosed(this);
            Surface surface = this.mInput;
            if (surface != null) {
                surface.release();
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            try {
                try {
                    this.mDeviceImpl.stopRepeating();
                } catch (CameraAccessException e) {
                    Log.e(TAG, this.mIdString + "Exception while stopping repeating: ", e);
                }
                this.mSequenceDrainer.beginDrain();
                Surface surface = this.mInput;
                if (surface != null) {
                    surface.release();
                }
            } catch (IllegalStateException unused) {
                this.mStateCallback.onClosed(this);
            }
        }
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public boolean isAborting() {
        return this.mAborting;
    }

    private CameraCaptureSession.StateCallback createUserStateCallbackProxy(Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        return new CallbackProxies.SessionStateCallbackProxy(executor, stateCallback);
    }

    private CaptureCallback createCaptureCallbackProxy(Handler handler, CameraCaptureSession.CaptureCallback captureCallback) {
        return createCaptureCallbackProxyWithExecutor(captureCallback != null ? CameraDeviceImpl.checkAndWrapHandler(handler) : null, captureCallback);
    }

    /* renamed from: android.hardware.camera2.impl.CameraCaptureSessionImpl$1, reason: invalid class name */
    class AnonymousClass1 extends CaptureCallback {
        final /* synthetic */ CameraCaptureSession.CaptureCallback val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Executor executor, CameraCaptureSession.CaptureCallback captureCallback, CameraCaptureSession.CaptureCallback captureCallback2, Executor executor2) {
            super(executor, captureCallback);
            this.val$callback = captureCallback2;
            this.val$executor = executor2;
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureStarted(CameraDevice cameraDevice, final CaptureRequest captureRequest, final long j, final long j2) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureStarted$0(captureCallback, captureRequest, j, j2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, long j, long j2) {
            captureCallback.onCaptureStarted(CameraCaptureSessionImpl.this, captureRequest, j, j2);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onReadoutStarted(CameraDevice cameraDevice, final CaptureRequest captureRequest, final long j, final long j2) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReadoutStarted$1(captureCallback, captureRequest, j, j2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReadoutStarted$1(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, long j, long j2) {
            captureCallback.onReadoutStarted(CameraCaptureSessionImpl.this, captureRequest, j, j2);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCapturePartial(CameraDevice cameraDevice, final CaptureRequest captureRequest, final CaptureResult captureResult) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCapturePartial$2(captureCallback, captureRequest, captureResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturePartial$2(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, CaptureResult captureResult) {
            captureCallback.onCapturePartial(CameraCaptureSessionImpl.this, captureRequest, captureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureProgressed(CameraDevice cameraDevice, final CaptureRequest captureRequest, final CaptureResult captureResult) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureProgressed$3(captureCallback, captureRequest, captureResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProgressed$3(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, CaptureResult captureResult) {
            captureCallback.onCaptureProgressed(CameraCaptureSessionImpl.this, captureRequest, captureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureCompleted(CameraDevice cameraDevice, final CaptureRequest captureRequest, final TotalCaptureResult totalCaptureResult) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureCompleted$4(captureCallback, captureRequest, totalCaptureResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            captureCallback.onCaptureCompleted(CameraCaptureSessionImpl.this, captureRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureFailed(CameraDevice cameraDevice, final CaptureRequest captureRequest, final CaptureFailure captureFailure) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureFailed$5(captureCallback, captureRequest, captureFailure);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$5(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            captureCallback.onCaptureFailed(CameraCaptureSessionImpl.this, captureRequest, captureFailure);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureSequenceCompleted(CameraDevice cameraDevice, int i, final long j) {
            final AnonymousClass1 anonymousClass1;
            final int i2;
            if (this.val$callback == null || this.val$executor == null) {
                anonymousClass1 = this;
                i2 = i;
            } else {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Executor executor = this.val$executor;
                    final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    anonymousClass1 = this;
                    i2 = i;
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureSequenceCompleted$6(captureCallback, i2, j);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            CameraCaptureSessionImpl.this.finishPendingSequence(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$6(CameraCaptureSession.CaptureCallback captureCallback, int i, long j) {
            captureCallback.onCaptureSequenceCompleted(CameraCaptureSessionImpl.this, i, j);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureSequenceAborted(CameraDevice cameraDevice, final int i) {
            if (this.val$callback != null && this.val$executor != null) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Executor executor = this.val$executor;
                    final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureSequenceAborted$7(captureCallback, i);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            CameraCaptureSessionImpl.this.finishPendingSequence(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$7(CameraCaptureSession.CaptureCallback captureCallback, int i) {
            captureCallback.onCaptureSequenceAborted(CameraCaptureSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureBufferLost(CameraDevice cameraDevice, final CaptureRequest captureRequest, final Surface surface, final long j) {
            if (this.val$callback == null || this.val$executor == null) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureBufferLost$8(captureCallback, captureRequest, surface, j);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureBufferLost$8(CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, Surface surface, long j) {
            captureCallback.onCaptureBufferLost(CameraCaptureSessionImpl.this, captureRequest, surface, j);
        }
    }

    private CaptureCallback createCaptureCallbackProxyWithExecutor(Executor executor, CameraCaptureSession.CaptureCallback captureCallback) {
        return new AnonymousClass1(executor, captureCallback, captureCallback, executor);
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        final Object obj = this.mDeviceImpl.mInterfaceLock;
        return new CameraDeviceImpl.StateCallbackKK() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl.2
            private boolean mBusy = false;
            private boolean mActive = false;

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onUnconfigured(CameraDevice cameraDevice) {
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onOpened(CameraDevice cameraDevice) {
                throw new AssertionError("Camera must already be open before creating a session");
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onDisconnected(CameraDevice cameraDevice) {
                CameraCaptureSessionImpl.this.close();
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onError(CameraDevice cameraDevice, int i) {
                Log.wtf(CameraCaptureSessionImpl.TAG, CameraCaptureSessionImpl.this.mIdString + "Got device error " + i);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onActive(CameraDevice cameraDevice) {
                CameraCaptureSessionImpl.this.mIdleDrainer.taskStarted();
                this.mActive = true;
                CameraCaptureSessionImpl.this.mStateCallback.onActive(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onIdle(CameraDevice cameraDevice) {
                boolean z;
                synchronized (obj) {
                    z = CameraCaptureSessionImpl.this.mAborting;
                }
                if (this.mBusy && z) {
                    CameraCaptureSessionImpl.this.mAbortDrainer.taskFinished();
                    synchronized (obj) {
                        CameraCaptureSessionImpl.this.mAborting = false;
                    }
                }
                if (this.mActive) {
                    CameraCaptureSessionImpl.this.mIdleDrainer.taskFinished();
                }
                this.mBusy = false;
                this.mActive = false;
                CameraCaptureSessionImpl.this.mStateCallback.onReady(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onBusy(CameraDevice cameraDevice) {
                this.mBusy = true;
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onRequestQueueEmpty() {
                CameraCaptureSessionImpl.this.mStateCallback.onCaptureQueueEmpty(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onSurfacePrepared(Surface surface) {
                CameraCaptureSessionImpl.this.mStateCallback.onSurfacePrepared(this, surface);
            }
        };
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void checkNotClosed() {
        if (this.mClosed) {
            throw new IllegalStateException("Session has been closed; further changes are illegal.");
        }
    }

    private int addPendingSequence(int i) {
        this.mSequenceDrainer.taskStarted(Integer.valueOf(i));
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPendingSequence(int i) {
        try {
            this.mSequenceDrainer.taskFinished(Integer.valueOf(i));
        } catch (IllegalStateException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    private class SequenceDrainListener implements TaskDrainer.DrainListener {
        private SequenceDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            CameraCaptureSessionImpl.this.mStateCallback.onClosed(CameraCaptureSessionImpl.this);
            if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                return;
            }
            CameraCaptureSessionImpl.this.mAbortDrainer.beginDrain();
        }
    }

    private class AbortDrainListener implements TaskDrainer.DrainListener {
        private AbortDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (CameraCaptureSessionImpl.this.mDeviceImpl.mInterfaceLock) {
                if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                CameraCaptureSessionImpl.this.mIdleDrainer.beginDrain();
            }
        }
    }

    private class IdleDrainListener implements TaskDrainer.DrainListener {
        private IdleDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (CameraCaptureSessionImpl.this.mDeviceImpl.mInterfaceLock) {
                if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                try {
                    try {
                        CameraCaptureSessionImpl.this.mDeviceImpl.configureStreamsChecked(null, null, 0, null, SystemClock.uptimeMillis());
                    } catch (SecurityException e) {
                        Log.e(CameraCaptureSessionImpl.TAG, CameraCaptureSessionImpl.this.mIdString + "Exception while unconfiguring outputs: ", e);
                    }
                } catch (CameraAccessException e2) {
                    Log.e(CameraCaptureSessionImpl.TAG, CameraCaptureSessionImpl.this.mIdString + "Exception while unconfiguring outputs: ", e2);
                } catch (IllegalStateException unused) {
                }
            }
        }
    }
}
