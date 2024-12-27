package android.hardware.camera2.impl;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.graphics.Rect;
import android.hardware.IDeviceInjectorCallback;
import android.hardware.IDeviceInjectorSession;
import android.hardware.IRemoteDevice;
import android.hardware.IRemoteDeviceCallback;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DeviceInjectorSession;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.ExceptionUtils;
import android.hardware.camera2.utils.SurfaceUtils;
import android.hardware.camera2.utils.TaskDrainer;
import android.hardware.camera2.utils.TaskSingleDrainer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.telephony.ims.ImsConferenceState;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import com.android.internal.util.function.pooled.PooledLambda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DeviceInjectorSessionImpl extends DeviceInjectorSession
        implements IBinder.DeathRecipient {
    private static final boolean DEBUG = false;
    private static final String TAG = "ijt/DeviceIjtSessionImpl";
    private final Executor mExecutor;
    private IDeviceInjectorSession mInjectorSession;
    private final TaskSingleDrainer mPendingDrainer;
    private final DeviceInjectorSession.StatusCallback mStatusCallback;
    private final TaskSingleDrainer mStopDrainer;
    private final DeviceInjectorCallback mCallback = new DeviceInjectorCallback();
    private final Object mInterfaceLock = new Object();
    private boolean mClosed = false;
    private boolean mInjectionStarted = false;
    private boolean mInjectionPending = false;
    private String mLastPackageName = "";
    private String mLastTargetId = "";
    private String mLastSourceId = "";

    /* JADX INFO: Access modifiers changed from: private */
    class StopDrainListener implements TaskDrainer.DrainListener {
        private StopDrainListener() {}

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (DeviceInjectorSessionImpl.this.mInterfaceLock) {
                DeviceInjectorSessionImpl.this.mInjectorSession = null;
                DeviceInjectorSessionImpl.this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$StopDrainListener$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.StopDrainListener.this
                                        .lambda$onDrained$0();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDrained$0() {
            DeviceInjectorSessionImpl.this.mStatusCallback.onClose();
        }
    }

    private class PendingDrainListener implements TaskDrainer.DrainListener {
        private PendingDrainListener() {}

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (DeviceInjectorSessionImpl.this.mInterfaceLock) {
                DeviceInjectorSessionImpl.this.mStopDrainer.beginDrain();
            }
        }
    }

    public DeviceInjectorSessionImpl(
            DeviceInjectorSession.StatusCallback statusCallback, Executor executor) {
        this.mStatusCallback = statusCallback;
        this.mExecutor = executor;
        this.mStopDrainer = new TaskSingleDrainer(this.mExecutor, new StopDrainListener(), "stop");
        this.mPendingDrainer =
                new TaskSingleDrainer(
                        this.mExecutor,
                        new PendingDrainListener(),
                        ImsConferenceState.STATUS_PENDING);
    }

    @Override // android.hardware.camera2.DeviceInjectorSession, java.lang.AutoCloseable
    public void close() {
        TaskSingleDrainer taskSingleDrainer;
        synchronized (this.mInterfaceLock) {
            try {
                if (this.mInjectorSession != null) {
                    this.mClosed = true;
                    this.mInjectorSession.stopDeviceInjector();
                    this.mInjectorSession.asBinder().unlinkToDeath(this, 0);
                }
                taskSingleDrainer = this.mPendingDrainer;
            } catch (RemoteException e) {
                taskSingleDrainer = this.mPendingDrainer;
            } catch (Throwable th) {
                this.mPendingDrainer.beginDrain();
                throw th;
            }
            taskSingleDrainer.beginDrain();
        }
    }

    @Override // android.hardware.camera2.DeviceInjectorSession
    public void setDeviceInjectorPending(boolean pending)
            throws CameraAccessException, SecurityException {
        synchronized (this.mInterfaceLock) {
            if (this.mClosed) {
                throw new IllegalStateException("DeviceInjectorSession is already closed");
            }
            try {
                if (this.mInjectorSession != null) {
                    this.mInjectorSession.setDeviceInjectorPending(pending);
                }
            } catch (RemoteException e) {
                ServiceSpecificException sse =
                        new ServiceSpecificException(4, "Camera service is currently unavailable");
                ExceptionUtils.throwAsPublicException(sse);
            } catch (ServiceSpecificException e2) {
                ExceptionUtils.throwAsPublicException(e2);
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mInterfaceLock) {
            Log.w(TAG, "CameraService died unexpectedly");
            if (this.mInjectorSession == null) {
                return;
            }
            Runnable r = new Runnable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DeviceInjectorSessionImpl.this.lambda$binderDied$0();
                        }
                    };
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(r);
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        synchronized (this.mInterfaceLock) {
            this.mStatusCallback.onError(1);
            if (this.mInjectionStarted) {
                this.mStopDrainer.taskFinished();
                this.mStatusCallback.onInjectionStopped(
                        this.mLastPackageName, this.mLastTargetId, this.mLastSourceId);
            }
            this.mInjectionStarted = false;
            if (this.mInjectionPending) {
                this.mPendingDrainer.taskFinished();
                this.mStatusCallback.onInjectionPendingStopped(
                        this.mLastPackageName, this.mLastTargetId);
            }
            this.mInjectionPending = false;
        }
    }

    public DeviceInjectorCallback getCallback() {
        return this.mCallback;
    }

    public DeviceInjectorRemoteDevice getRemoteDevice(DeviceInjectorSession.RemoteDevice device) {
        return new DeviceInjectorRemoteDevice(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRemoteInjectorSession(IDeviceInjectorSession injectorSession) {
        synchronized (this.mInterfaceLock) {
            if (injectorSession == null) {
                Log.e(TAG, "The device injector session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            this.mInjectorSession = injectorSession;
            IBinder remoteSessionBinder = injectorSession.asBinder();
            if (remoteSessionBinder == null) {
                Log.e(TAG, "The device injector session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            long identity = Binder.clearCallingIdentity();
            try {
                remoteSessionBinder.linkToDeath(this, 0);
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.this.lambda$setRemoteInjectorSession$1();
                            }
                        });
            } catch (RemoteException e) {
                scheduleNotifyError(0);
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRemoteInjectorSession$1() {
        this.mStatusCallback.onSessionCreated(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionStarted(
            final String packageName, final String targetId, final String sourceId) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            this.mStopDrainer.taskStarted();
            this.mInjectionStarted = true;
            this.mLastPackageName = packageName;
            this.mLastTargetId = targetId;
            this.mLastSourceId = sourceId;
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.this.lambda$onInjectionStarted$2(
                                        packageName, targetId, sourceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionStarted$2(
            String packageName, String targetId, String sourceId) {
        this.mStatusCallback.onInjectionStarted(packageName, targetId, sourceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionStopped(
            final String packageName, final String targetId, final String sourceId) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            if (this.mInjectionStarted) {
                this.mStopDrainer.taskFinished();
            }
            this.mInjectionStarted = false;
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.this.lambda$onInjectionStopped$3(
                                        packageName, targetId, sourceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionStopped$3(
            String packageName, String targetId, String sourceId) {
        this.mStatusCallback.onInjectionStopped(packageName, targetId, sourceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionPendingStarted(final String packageName, final String targetId) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            this.mPendingDrainer.taskStarted();
            this.mInjectionPending = true;
            this.mLastPackageName = packageName;
            this.mLastTargetId = targetId;
            this.mLastSourceId = "";
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.this.lambda$onInjectionPendingStarted$4(
                                        packageName, targetId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionPendingStarted$4(
            String packageName, String targetId) {
        this.mStatusCallback.onInjectionPendingStarted(packageName, targetId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionPendingStopped(final String packageName, final String targetId) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            if (this.mInjectionPending) {
                this.mPendingDrainer.taskFinished();
            }
            this.mInjectionPending = false;
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.this.lambda$onInjectionPendingStopped$5(
                                        packageName, targetId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionPendingStopped$5(
            String packageName, String targetId) {
        this.mStatusCallback.onInjectionPendingStopped(packageName, targetId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionError(int errorCode) {
        Log.i(
                TAG,
                String.format(
                        "injector session error received, code %d", Integer.valueOf(errorCode)));
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            switch (errorCode) {
                case -1:
                case 0:
                case 1:
                case 2:
                    scheduleNotifyError(errorCode);
                    break;
                default:
                    Log.e(TAG, "Unknown error from injector session: " + errorCode);
                    scheduleNotifyError(1);
                    break;
            }
        }
    }

    private void scheduleNotifyError(int errorCode) {
        long identity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new BiConsumer() { // from class:
                                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda2
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            ((DeviceInjectorSessionImpl) obj)
                                                    .notifyError(((Integer) obj2).intValue());
                                        }
                                    },
                                    this,
                                    Integer.valueOf(errorCode))
                            .recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int errorCode) {
        boolean isInjectorSessionExists;
        synchronized (this.mInterfaceLock) {
            isInjectorSessionExists = this.mInjectorSession != null;
        }
        if (isInjectorSessionExists) {
            this.mStatusCallback.onError(errorCode);
        }
    }

    public class DeviceInjectorCallback extends IDeviceInjectorCallback.Stub {
        public DeviceInjectorCallback() {}

        @Override // android.hardware.IDeviceInjectorCallback.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onSessionCreated(IDeviceInjectorSession deviceInjectorSession)
                throws RemoteException {
            DeviceInjectorSessionImpl.this.setRemoteInjectorSession(deviceInjectorSession);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStarted(String packageName, String targetId, String sourceId)
                throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionStarted(packageName, targetId, sourceId);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStopped(String packageName, String targetId, String sourceId)
                throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionStopped(packageName, targetId, sourceId);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStarted(String packageName, String targetId)
                throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionPendingStarted(packageName, targetId);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStopped(String packageName, String targetId)
                throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionPendingStopped(packageName, targetId);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onError(int errorCode) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionError(errorCode);
        }
    }

    public class DeviceInjectorRemoteDevice extends IRemoteDevice.Stub {
        private static final Executor BINDER_EXECUTOR = Executors.newSingleThreadExecutor();
        private final DeviceInjectorSession.RemoteDevice mRemoteDevice;

        public DeviceInjectorRemoteDevice(DeviceInjectorSession.RemoteDevice remoteDevice) {
            this.mRemoteDevice = remoteDevice;
        }

        private <T> T executeWithCleanIdentity(final Callable<T> callable) {
            final CompletableFuture<T> task = new CompletableFuture<>();
            long identity = Binder.clearCallingIdentity();
            try {
                BINDER_EXECUTOR.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice
                                        .lambda$executeWithCleanIdentity$0(task, callable);
                            }
                        });
                try {
                    return task.get();
                } catch (Throwable e) {
                    Log.e(DeviceInjectorSessionImpl.TAG, "error while transaction", e);
                    throw android.util.ExceptionUtils.propagate(e);
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        static /* synthetic */ void lambda$executeWithCleanIdentity$0(
                CompletableFuture task, Callable callable) {
            try {
                task.complete(callable.call());
            } catch (Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }

        @Override // android.hardware.IRemoteDevice.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$open$1(String targetId, int targetLensFacing)
                throws Exception {
            return this.mRemoteDevice.open(targetId, targetLensFacing);
        }

        @Override // android.hardware.IRemoteDevice
        public String open(final String targetId, final int targetLensFacing)
                throws RemoteException {
            return (String)
                    executeWithCleanIdentity(
                            new Callable() { // from class:
                                // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda5
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    String lambda$open$1;
                                    lambda$open$1 =
                                            DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice
                                                    .this
                                                    .lambda$open$1(targetId, targetLensFacing);
                                    return lambda$open$1;
                                }
                            });
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
            final DeviceInjectorSession.CharacteristicBuilder builder =
                    new CharacteristicBuilderImpl();
            builder.setLensFacing(2);
            builder.setSensorOrientation(0);
            builder.setAELockAvailable(false);
            builder.setAWBLockAvailable(false);
            builder.setFlashAvailable(false);
            builder.addSupportedAEMode(1);
            builder.addSupportedAWBMode(1);
            builder.addSupportedAFMode(1);
            builder.addSupportedEffectMode(0);
            builder.addSupportedSceneMode(0);
            builder.addSupportedControlMode(1);
            return (CameraMetadataNative)
                    executeWithCleanIdentity(
                            new Callable() { // from class:
                                // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda3
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    CameraMetadataNative lambda$getCameraCharacteristic$2;
                                    lambda$getCameraCharacteristic$2 =
                                            DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice
                                                    .this
                                                    .lambda$getCameraCharacteristic$2(builder);
                                    return lambda$getCameraCharacteristic$2;
                                }
                            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ CameraMetadataNative lambda$getCameraCharacteristic$2(
                DeviceInjectorSession.CharacteristicBuilder builder) throws Exception {
            return this.mRemoteDevice.getCameraCharacteristic(builder).getNativeMetadata();
        }

        @Override // android.hardware.IRemoteDevice
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            final Surface surface = outputConfiguration.getSurface();
            return ((Integer)
                            executeWithCleanIdentity(
                                    new Callable() { // from class:
                                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda0
                                        @Override // java.util.concurrent.Callable
                                        public final Object call() {
                                            Integer lambda$createStream$3;
                                            lambda$createStream$3 =
                                                    DeviceInjectorSessionImpl
                                                            .DeviceInjectorRemoteDevice.this
                                                            .lambda$createStream$3(surface);
                                            return lambda$createStream$3;
                                        }
                                    }))
                    .intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$createStream$3(Surface surface) throws Exception {
            return Integer.valueOf(
                    this.mRemoteDevice.createStream(surface, SurfaceUtils.getSurfaceSize(surface)));
        }

        @Override // android.hardware.IRemoteDevice
        public void deleteStream(final int streamId) throws RemoteException {
            executeWithCleanIdentity(
                    new Callable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda4
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$deleteStream$4;
                            lambda$deleteStream$4 =
                                    DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this
                                            .lambda$deleteStream$4(streamId);
                            return lambda$deleteStream$4;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$deleteStream$4(int streamId) throws Exception {
            this.mRemoteDevice.deleteStream(streamId);
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative createDefaultRequest() throws RemoteException {
            final CaptureRequest.Builder builder =
                    new CaptureRequest.Builder(new CameraMetadataNative(), false, -1, "", null);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
            builder.set(CaptureRequest.CONTROL_AF_MODE, 1);
            builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
            builder.set(CaptureRequest.CONTROL_AWB_MODE, 1);
            builder.set(CaptureRequest.CONTROL_EFFECT_MODE, 0);
            builder.set(CaptureRequest.CONTROL_SCENE_MODE, 0);
            builder.set(CaptureRequest.CONTROL_MODE, 1);
            builder.set(CaptureRequest.CONTROL_AE_LOCK, false);
            builder.set(CaptureRequest.CONTROL_AWB_LOCK, false);
            builder.set(CaptureRequest.FLASH_MODE, 0);
            return (CameraMetadataNative)
                    executeWithCleanIdentity(
                            new Callable() { // from class:
                                // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda7
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    CameraMetadataNative lambda$createDefaultRequest$5;
                                    lambda$createDefaultRequest$5 =
                                            DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice
                                                    .this
                                                    .lambda$createDefaultRequest$5(builder);
                                    return lambda$createDefaultRequest$5;
                                }
                            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ CameraMetadataNative lambda$createDefaultRequest$5(
                CaptureRequest.Builder builder) throws Exception {
            return this.mRemoteDevice.createDefaultRequest(builder).getNativeMetadata();
        }

        @Override // android.hardware.IRemoteDevice
        public void submitRequest(
                CameraMetadataNative request, final int[] outputStreams, final boolean streaming)
                throws RemoteException {
            final CaptureRequest.Builder builder =
                    new CaptureRequest.Builder(request, false, -1, "", null);
            executeWithCleanIdentity(
                    new Callable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda9
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$submitRequest$6;
                            lambda$submitRequest$6 =
                                    DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this
                                            .lambda$submitRequest$6(
                                                    builder, outputStreams, streaming);
                            return lambda$submitRequest$6;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$submitRequest$6(
                CaptureRequest.Builder builder, int[] outputStreams, boolean streaming)
                throws Exception {
            this.mRemoteDevice.submitRequest(builder.build(), outputStreams, streaming);
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void clearRequest() throws RemoteException {
            executeWithCleanIdentity(
                    new Callable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda1
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$clearRequest$7;
                            lambda$clearRequest$7 =
                                    DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this
                                            .lambda$clearRequest$7();
                            return lambda$clearRequest$7;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$clearRequest$7() throws Exception {
            this.mRemoteDevice.clearRequest();
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void setCallback(final IRemoteDeviceCallback callback) throws RemoteException {
            executeWithCleanIdentity(
                    new Callable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda8
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$setCallback$8;
                            lambda$setCallback$8 =
                                    DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this
                                            .lambda$setCallback$8(callback);
                            return lambda$setCallback$8;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$setCallback$8(final IRemoteDeviceCallback callback)
                throws Exception {
            this.mRemoteDevice.setCallback(
                    new DeviceInjectorSession.RemoteDeviceCallback() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.1
                        @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                        public void onCaptureResult(Map<CaptureResult.Key, Object> result)
                                throws RemoteException {
                            Objects.requireNonNull(result);
                            CameraMetadataNative metadata = new CameraMetadataNative();
                            for (Map.Entry<CaptureResult.Key, Object> entry : result.entrySet()) {
                                metadata.set(
                                        (CaptureResult.Key<CaptureResult.Key>) entry.getKey(),
                                        (CaptureResult.Key) entry.getValue());
                            }
                            callback.onCaptureResult(metadata);
                        }

                        @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                        public void onError(int errorCode) throws RemoteException {
                            callback.onError(errorCode);
                        }

                        @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                        public void onOrientationChanged(int orientation) throws RemoteException {
                            switch (orientation) {
                                case 0:
                                case 90:
                                case 180:
                                case 270:
                                    callback.onOrientationChanged(orientation);
                                    return;
                                default:
                                    throw new IllegalArgumentException(
                                            "orientation must be 0, 90, 180 or 270.");
                            }
                        }
                    });
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void close() throws RemoteException {
            executeWithCleanIdentity(
                    new Callable() { // from class:
                        // android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda2
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$close$9;
                            lambda$close$9 =
                                    DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this
                                            .lambda$close$9();
                            return lambda$close$9;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$close$9() throws Exception {
            this.mRemoteDevice.close();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CharacteristicBuilderImpl extends DeviceInjectorSession.CharacteristicBuilder {
        private boolean mAELockAvailable;
        private Set<Integer> mAEModes;
        private Set<Integer> mAFModes;
        private boolean mAWBLockAvailable;
        private Set<Integer> mAWBModes;
        private Size mActiveArraySize;
        private Set<Size> mCaptureSizes;
        private Set<Integer> mControlMode;
        private Set<Integer> mEffectModes;
        private boolean mFlashAvailable;
        private int mLensFacing;
        private Set<Integer> mSceneModes;
        private int mSensorOrientation;
        private Set<Size> mStreamingSizes;

        private CharacteristicBuilderImpl() {
            this.mSensorOrientation = 0;
            this.mLensFacing = 2;
            this.mAELockAvailable = false;
            this.mAWBLockAvailable = false;
            this.mStreamingSizes = new HashSet();
            this.mCaptureSizes = new HashSet();
            this.mFlashAvailable = false;
            this.mAEModes = new HashSet();
            this.mAWBModes = new HashSet();
            this.mAFModes = new HashSet();
            this.mEffectModes = new HashSet();
            this.mSceneModes = new HashSet();
            this.mControlMode = new HashSet();
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setActiveArraySize(Size size) {
            this.mActiveArraySize = size;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setSensorOrientation(int orientation) {
            this.mSensorOrientation = orientation;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setLensFacing(int facing) {
            this.mLensFacing = facing;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setAELockAvailable(boolean available) {
            this.mAELockAvailable = available;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setAWBLockAvailable(boolean available) {
            this.mAWBLockAvailable = available;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedStreamingSize(Size size) {
            this.mStreamingSizes.add(size);
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedCaptureSize(Size size) {
            this.mCaptureSizes.add(size);
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setFlashAvailable(boolean available) {
            this.mFlashAvailable = available;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAEMode(int aeMode) {
            this.mAEModes.add(Integer.valueOf(aeMode));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAWBMode(int awbMode) {
            this.mAWBModes.add(Integer.valueOf(awbMode));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAFMode(int afMode) {
            this.mAFModes.add(Integer.valueOf(afMode));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedEffectMode(int effectMode) {
            this.mEffectModes.add(Integer.valueOf(effectMode));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedSceneMode(int sceneMode) {
            this.mSceneModes.add(Integer.valueOf(sceneMode));
            if (sceneMode != 0) {
                this.mSceneModes.remove(0);
            }
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedControlMode(
                int controlMode) {
            this.mControlMode.add(Integer.valueOf(controlMode));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public CameraCharacteristics build() throws IllegalArgumentException {
            CameraMetadataNative metadataNative = new CameraMetadataNative();
            if (this.mActiveArraySize == null) {
                throw new IllegalArgumentException("active array size is null");
            }
            if (this.mSensorOrientation < 0
                    || 360 <= this.mSensorOrientation
                    || this.mSensorOrientation % 90 != 0) {
                throw new IllegalArgumentException("sensor orientation is invalid");
            }
            if (this.mLensFacing < 0 || 2 < this.mLensFacing) {
                throw new IllegalArgumentException("lens facing is invalid");
            }
            if (this.mStreamingSizes.isEmpty()) {
                throw new IllegalArgumentException("streaming size is empty");
            }
            for (Size size : this.mStreamingSizes) {
                if (size == null) {
                    throw new IllegalArgumentException("streaming size contains null");
                }
            }
            for (Size size2 : this.mCaptureSizes) {
                if (size2 == null) {
                    throw new IllegalArgumentException("capture size contains null");
                }
            }
            Set<Size> intersection = new HashSet<>(this.mStreamingSizes);
            intersection.retainAll(this.mCaptureSizes);
            if (!intersection.isEmpty()) {
                throw new IllegalArgumentException(
                        "streaming size and capture size has common size");
            }
            if (this.mAEModes.isEmpty()) {
                throw new IllegalArgumentException("supported AE modes are empty");
            }
            for (Integer mode : this.mAEModes) {
                if (mode == null) {
                    throw new IllegalArgumentException("ae mode contains null");
                }
                if (mode.intValue() < 0 || 5 < mode.intValue()) {
                    throw new IllegalArgumentException("invalid ae mode");
                }
            }
            if (this.mAWBModes.isEmpty()) {
                throw new IllegalArgumentException("supported AWB modes are empty");
            }
            for (Integer mode2 : this.mAWBModes) {
                if (mode2 == null) {
                    throw new IllegalArgumentException("awb mode contains null");
                }
                if (mode2.intValue() < 0 || 8 < mode2.intValue()) {
                    throw new IllegalArgumentException("invalid awb mode");
                }
            }
            if (this.mAFModes.isEmpty()) {
                throw new IllegalArgumentException("supported AF modes are empty");
            }
            for (Integer mode3 : this.mAFModes) {
                if (mode3 == null) {
                    throw new IllegalArgumentException("af mode contains null");
                }
                if (mode3.intValue() < 0 || 5 < mode3.intValue()) {
                    throw new IllegalArgumentException("invalid af mode");
                }
            }
            if (this.mEffectModes.isEmpty()) {
                throw new IllegalArgumentException("supported effect modes are empty");
            }
            for (Integer mode4 : this.mEffectModes) {
                if (mode4 == null) {
                    throw new IllegalArgumentException("effect mode contains null");
                }
                if (mode4.intValue() < 0 || 8 < mode4.intValue()) {
                    throw new IllegalArgumentException("invalid effect mode");
                }
            }
            if (this.mSceneModes.isEmpty()) {
                throw new IllegalArgumentException("supported scene modes are empty");
            }
            for (Integer mode5 : this.mSceneModes) {
                if (mode5 == null) {
                    throw new IllegalArgumentException("scene mode contains null");
                }
                if (mode5.intValue() < 0 || 16 < mode5.intValue()) {
                    throw new IllegalArgumentException("invalid scene mode");
                }
            }
            if (this.mControlMode.isEmpty()) {
                throw new IllegalArgumentException("supported control modes are empty");
            }
            for (Integer mode6 : this.mControlMode) {
                if (mode6 == null) {
                    throw new IllegalArgumentException("scene mode contains null");
                }
                if (mode6.intValue() < 0 || 4 < mode6.intValue()) {
                    throw new IllegalArgumentException("invalid control mode");
                }
            }
            if (this.mControlMode.contains(2)) {
                if (this.mSceneModes.contains(0)) {
                    throw new IllegalArgumentException(
                            "control mode contains USE_SCENE_MODE but no valid scene mode exist");
                }
            } else if (!this.mSceneModes.contains(0)) {
                throw new IllegalArgumentException(
                        "control mode does not contains USE_SCENE_MODE but scene mode other than"
                                + " DISABLED exist");
            }
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Rect>>)
                            CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE,
                    (CameraCharacteristics.Key<Rect>)
                            new Rect(
                                    0,
                                    0,
                                    this.mActiveArraySize.getWidth(),
                                    this.mActiveArraySize.getHeight()));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Integer>>)
                            CameraCharacteristics.SENSOR_ORIENTATION,
                    (CameraCharacteristics.Key<Integer>) Integer.valueOf(this.mSensorOrientation));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Integer>>)
                            CameraCharacteristics.LENS_FACING,
                    (CameraCharacteristics.Key<Integer>) Integer.valueOf(this.mLensFacing));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>)
                            CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE,
                    (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mAELockAvailable));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>)
                            CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE,
                    (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mAWBLockAvailable));
            final ArrayList<StreamConfiguration> streamConfigurations = new ArrayList<>();
            this.mStreamingSizes.stream()
                    .forEach(
                            new Consumer() { // from class:
                                // android.hardware.camera2.impl.DeviceInjectorSessionImpl$CharacteristicBuilderImpl$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    streamConfigurations.add(
                                            new StreamConfiguration(
                                                    35,
                                                    r2.getWidth(),
                                                    ((Size) obj).getHeight(),
                                                    false));
                                }
                            });
            this.mCaptureSizes.stream()
                    .forEach(
                            new Consumer() { // from class:
                                // android.hardware.camera2.impl.DeviceInjectorSessionImpl$CharacteristicBuilderImpl$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    streamConfigurations.add(
                                            new StreamConfiguration(
                                                    35,
                                                    r2.getWidth(),
                                                    ((Size) obj).getHeight(),
                                                    true));
                                }
                            });
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<StreamConfiguration[]>>)
                            CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS,
                    (CameraCharacteristics.Key<StreamConfiguration[]>)
                            streamConfigurations.toArray(new StreamConfiguration[0]));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>)
                            CameraCharacteristics.FLASH_INFO_AVAILABLE,
                    (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mFlashAvailable));
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES,
                    (CameraCharacteristics.Key<int[]>)
                            this.mAEModes.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES,
                    (CameraCharacteristics.Key<int[]>)
                            this.mAWBModes.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES,
                    (CameraCharacteristics.Key<int[]>)
                            this.mAFModes.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS,
                    (CameraCharacteristics.Key<int[]>)
                            this.mEffectModes.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES,
                    (CameraCharacteristics.Key<int[]>)
                            this.mSceneModes.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            metadataNative.set(
                    (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                            CameraCharacteristics.CONTROL_AVAILABLE_MODES,
                    (CameraCharacteristics.Key<int[]>)
                            this.mControlMode.stream()
                                    .mapToInt(
                                            new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                                    .toArray());
            return new CameraCharacteristics(metadataNative);
        }
    }
}
