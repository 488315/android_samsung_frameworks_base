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
import android.hardware.camera2.impl.DeviceInjectorSessionImpl;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class DeviceInjectorSessionImpl extends DeviceInjectorSession implements IBinder.DeathRecipient {
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
        private StopDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (DeviceInjectorSessionImpl.this.mInterfaceLock) {
                DeviceInjectorSessionImpl.this.mInjectorSession = null;
                DeviceInjectorSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$StopDrainListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.StopDrainListener.this.lambda$onDrained$0();
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
        private PendingDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (DeviceInjectorSessionImpl.this.mInterfaceLock) {
                DeviceInjectorSessionImpl.this.mStopDrainer.beginDrain();
            }
        }
    }

    public DeviceInjectorSessionImpl(DeviceInjectorSession.StatusCallback statusCallback, Executor executor) {
        this.mStatusCallback = statusCallback;
        this.mExecutor = executor;
        this.mStopDrainer = new TaskSingleDrainer(executor, new StopDrainListener(), "stop");
        this.mPendingDrainer = new TaskSingleDrainer(executor, new PendingDrainListener(), ImsConferenceState.STATUS_PENDING);
    }

    @Override // android.hardware.camera2.DeviceInjectorSession, java.lang.AutoCloseable
    public void close() {
        TaskSingleDrainer taskSingleDrainer;
        synchronized (this.mInterfaceLock) {
            try {
                try {
                    IDeviceInjectorSession iDeviceInjectorSession = this.mInjectorSession;
                    if (iDeviceInjectorSession != null) {
                        this.mClosed = true;
                        iDeviceInjectorSession.stopDeviceInjector();
                        this.mInjectorSession.asBinder().unlinkToDeath(this, 0);
                    }
                    taskSingleDrainer = this.mPendingDrainer;
                } catch (Throwable th) {
                    this.mPendingDrainer.beginDrain();
                    throw th;
                }
            } catch (RemoteException unused) {
                taskSingleDrainer = this.mPendingDrainer;
            }
            taskSingleDrainer.beginDrain();
        }
    }

    @Override // android.hardware.camera2.DeviceInjectorSession
    public void setDeviceInjectorPending(boolean z) throws CameraAccessException, SecurityException {
        synchronized (this.mInterfaceLock) {
            if (this.mClosed) {
                throw new IllegalStateException("DeviceInjectorSession is already closed");
            }
            try {
                IDeviceInjectorSession iDeviceInjectorSession = this.mInjectorSession;
                if (iDeviceInjectorSession != null) {
                    iDeviceInjectorSession.setDeviceInjectorPending(z);
                }
            } catch (RemoteException unused) {
                ExceptionUtils.throwAsPublicException(new ServiceSpecificException(4, "Camera service is currently unavailable"));
            } catch (ServiceSpecificException e) {
                ExceptionUtils.throwAsPublicException(e);
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
            Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceInjectorSessionImpl.this.lambda$binderDied$0();
                }
            };
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(runnable);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        synchronized (this.mInterfaceLock) {
            this.mStatusCallback.onError(1);
            if (this.mInjectionStarted) {
                this.mStopDrainer.taskFinished();
                this.mStatusCallback.onInjectionStopped(this.mLastPackageName, this.mLastTargetId, this.mLastSourceId);
            }
            this.mInjectionStarted = false;
            if (this.mInjectionPending) {
                this.mPendingDrainer.taskFinished();
                this.mStatusCallback.onInjectionPendingStopped(this.mLastPackageName, this.mLastTargetId);
            }
            this.mInjectionPending = false;
        }
    }

    public DeviceInjectorCallback getCallback() {
        return this.mCallback;
    }

    public DeviceInjectorRemoteDevice getRemoteDevice(DeviceInjectorSession.RemoteDevice remoteDevice) {
        return new DeviceInjectorRemoteDevice(remoteDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRemoteInjectorSession(IDeviceInjectorSession iDeviceInjectorSession) {
        synchronized (this.mInterfaceLock) {
            if (iDeviceInjectorSession == null) {
                Log.e(TAG, "The device injector session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            this.mInjectorSession = iDeviceInjectorSession;
            IBinder asBinder = iDeviceInjectorSession.asBinder();
            if (asBinder == null) {
                Log.e(TAG, "The device injector session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    asBinder.linkToDeath(this, 0);
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            DeviceInjectorSessionImpl.this.lambda$setRemoteInjectorSession$1();
                        }
                    });
                } catch (RemoteException unused) {
                    scheduleNotifyError(0);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRemoteInjectorSession$1() {
        this.mStatusCallback.onSessionCreated(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionStarted(final String str, final String str2, final String str3) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            this.mStopDrainer.taskStarted();
            this.mInjectionStarted = true;
            this.mLastPackageName = str;
            this.mLastTargetId = str2;
            this.mLastSourceId = str3;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.this.lambda$onInjectionStarted$2(str, str2, str3);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionStarted$2(String str, String str2, String str3) {
        this.mStatusCallback.onInjectionStarted(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionStopped(final String str, final String str2, final String str3) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            if (this.mInjectionStarted) {
                this.mStopDrainer.taskFinished();
            }
            this.mInjectionStarted = false;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.this.lambda$onInjectionStopped$3(str, str2, str3);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionStopped$3(String str, String str2, String str3) {
        this.mStatusCallback.onInjectionStopped(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionPendingStarted(final String str, final String str2) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            this.mPendingDrainer.taskStarted();
            this.mInjectionPending = true;
            this.mLastPackageName = str;
            this.mLastTargetId = str2;
            this.mLastSourceId = "";
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.this.lambda$onInjectionPendingStarted$4(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionPendingStarted$4(String str, String str2) {
        this.mStatusCallback.onInjectionPendingStarted(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionPendingStopped(final String str, final String str2) {
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            if (this.mInjectionPending) {
                this.mPendingDrainer.taskFinished();
            }
            this.mInjectionPending = false;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.this.lambda$onInjectionPendingStopped$5(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInjectionPendingStopped$5(String str, String str2) {
        this.mStatusCallback.onInjectionPendingStopped(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionError(int i) {
        Log.i(TAG, String.format("injector session error received, code %d", Integer.valueOf(i)));
        synchronized (this.mInterfaceLock) {
            if (this.mInjectorSession == null) {
                return;
            }
            if (i == -1 || i == 0 || i == 1 || i == 2) {
                scheduleNotifyError(i);
            } else {
                Log.e(TAG, "Unknown error from injector session: " + i);
                scheduleNotifyError(1);
            }
        }
    }

    private void scheduleNotifyError(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((DeviceInjectorSessionImpl) obj).notifyError(((Integer) obj2).intValue());
                }
            }, this, Integer.valueOf(i)).recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        boolean z;
        synchronized (this.mInterfaceLock) {
            z = this.mInjectorSession != null;
        }
        if (z) {
            this.mStatusCallback.onError(i);
        }
    }

    public class DeviceInjectorCallback extends IDeviceInjectorCallback.Stub {
        @Override // android.hardware.IDeviceInjectorCallback.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public DeviceInjectorCallback() {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onSessionCreated(IDeviceInjectorSession iDeviceInjectorSession) throws RemoteException {
            DeviceInjectorSessionImpl.this.setRemoteInjectorSession(iDeviceInjectorSession);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStarted(String str, String str2, String str3) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionStarted(str, str2, str3);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStopped(String str, String str2, String str3) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionStopped(str, str2, str3);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStarted(String str, String str2) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionPendingStarted(str, str2);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStopped(String str, String str2) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionPendingStopped(str, str2);
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onError(int i) throws RemoteException {
            DeviceInjectorSessionImpl.this.onInjectionError(i);
        }
    }

    public class DeviceInjectorRemoteDevice extends IRemoteDevice.Stub {
        private static final Executor BINDER_EXECUTOR = Executors.newSingleThreadExecutor();
        private final DeviceInjectorSession.RemoteDevice mRemoteDevice;

        @Override // android.hardware.IRemoteDevice.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public DeviceInjectorRemoteDevice(DeviceInjectorSession.RemoteDevice remoteDevice) {
            this.mRemoteDevice = remoteDevice;
        }

        private <T> T executeWithCleanIdentity(final Callable<T> callable) {
            final CompletableFuture completableFuture = new CompletableFuture();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                BINDER_EXECUTOR.execute(new Runnable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.lambda$executeWithCleanIdentity$0(completableFuture, callable);
                    }
                });
                try {
                    return (T) completableFuture.get();
                } catch (Throwable th) {
                    Log.e(DeviceInjectorSessionImpl.TAG, "error while transaction", th);
                    throw android.util.ExceptionUtils.propagate(th);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        static /* synthetic */ void lambda$executeWithCleanIdentity$0(CompletableFuture completableFuture, Callable callable) {
            try {
                completableFuture.complete(callable.call());
            } catch (Exception e) {
                throw android.util.ExceptionUtils.propagate(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$open$1(String str, int i) throws Exception {
            return this.mRemoteDevice.open(str, i);
        }

        @Override // android.hardware.IRemoteDevice
        public String open(final String str, final int i) throws RemoteException {
            return (String) executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda5
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    String lambda$open$1;
                    lambda$open$1 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$open$1(str, i);
                    return lambda$open$1;
                }
            });
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
            final CharacteristicBuilderImpl characteristicBuilderImpl = new CharacteristicBuilderImpl();
            characteristicBuilderImpl.setLensFacing(2);
            characteristicBuilderImpl.setSensorOrientation(0);
            characteristicBuilderImpl.setAELockAvailable(false);
            characteristicBuilderImpl.setAWBLockAvailable(false);
            characteristicBuilderImpl.setFlashAvailable(false);
            characteristicBuilderImpl.addSupportedAEMode(1);
            characteristicBuilderImpl.addSupportedAWBMode(1);
            characteristicBuilderImpl.addSupportedAFMode(1);
            characteristicBuilderImpl.addSupportedEffectMode(0);
            characteristicBuilderImpl.addSupportedSceneMode(0);
            characteristicBuilderImpl.addSupportedControlMode(1);
            return (CameraMetadataNative) executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    CameraMetadataNative lambda$getCameraCharacteristic$2;
                    lambda$getCameraCharacteristic$2 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$getCameraCharacteristic$2(characteristicBuilderImpl);
                    return lambda$getCameraCharacteristic$2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ CameraMetadataNative lambda$getCameraCharacteristic$2(DeviceInjectorSession.CharacteristicBuilder characteristicBuilder) throws Exception {
            return this.mRemoteDevice.getCameraCharacteristic(characteristicBuilder).getNativeMetadata();
        }

        @Override // android.hardware.IRemoteDevice
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            final Surface surface = outputConfiguration.getSurface();
            return ((Integer) executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Integer lambda$createStream$3;
                    lambda$createStream$3 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$createStream$3(surface);
                    return lambda$createStream$3;
                }
            })).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$createStream$3(Surface surface) throws Exception {
            return Integer.valueOf(this.mRemoteDevice.createStream(surface, SurfaceUtils.getSurfaceSize(surface)));
        }

        @Override // android.hardware.IRemoteDevice
        public void deleteStream(final int i) throws RemoteException {
            executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda4
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object lambda$deleteStream$4;
                    lambda$deleteStream$4 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$deleteStream$4(i);
                    return lambda$deleteStream$4;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$deleteStream$4(int i) throws Exception {
            this.mRemoteDevice.deleteStream(i);
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative createDefaultRequest() throws RemoteException {
            final CaptureRequest.Builder builder = new CaptureRequest.Builder(new CameraMetadataNative(), false, -1, "", null);
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
            return (CameraMetadataNative) executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda7
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    CameraMetadataNative lambda$createDefaultRequest$5;
                    lambda$createDefaultRequest$5 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$createDefaultRequest$5(builder);
                    return lambda$createDefaultRequest$5;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ CameraMetadataNative lambda$createDefaultRequest$5(CaptureRequest.Builder builder) throws Exception {
            return this.mRemoteDevice.createDefaultRequest(builder).getNativeMetadata();
        }

        @Override // android.hardware.IRemoteDevice
        public void submitRequest(CameraMetadataNative cameraMetadataNative, final int[] iArr, final boolean z) throws RemoteException {
            final CaptureRequest.Builder builder = new CaptureRequest.Builder(cameraMetadataNative, false, -1, "", null);
            executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda9
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object lambda$submitRequest$6;
                    lambda$submitRequest$6 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$submitRequest$6(builder, iArr, z);
                    return lambda$submitRequest$6;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$submitRequest$6(CaptureRequest.Builder builder, int[] iArr, boolean z) throws Exception {
            this.mRemoteDevice.submitRequest(builder.build(), iArr, z);
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void clearRequest() throws RemoteException {
            executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object lambda$clearRequest$7;
                    lambda$clearRequest$7 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$clearRequest$7();
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
        public void setCallback(final IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException {
            executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda8
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object lambda$setCallback$8;
                    lambda$setCallback$8 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$setCallback$8(iRemoteDeviceCallback);
                    return lambda$setCallback$8;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$setCallback$8(final IRemoteDeviceCallback iRemoteDeviceCallback) throws Exception {
            this.mRemoteDevice.setCallback(new DeviceInjectorSession.RemoteDeviceCallback(this) { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.1
                @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                public void onCaptureResult(Map<CaptureResult.Key, Object> map) throws RemoteException {
                    Objects.requireNonNull(map);
                    CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
                    for (Map.Entry<CaptureResult.Key, Object> entry : map.entrySet()) {
                        cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key>) entry.getKey(), (CaptureResult.Key) entry.getValue());
                    }
                    iRemoteDeviceCallback.onCaptureResult(cameraMetadataNative);
                }

                @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                public void onError(int i) throws RemoteException {
                    iRemoteDeviceCallback.onError(i);
                }

                @Override // android.hardware.camera2.DeviceInjectorSession.RemoteDeviceCallback
                public void onOrientationChanged(int i) throws RemoteException {
                    if (i != 0 && i != 90 && i != 180 && i != 270) {
                        throw new IllegalArgumentException("orientation must be 0, 90, 180 or 270.");
                    }
                    iRemoteDeviceCallback.onOrientationChanged(i);
                }
            });
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void close() throws RemoteException {
            executeWithCleanIdentity(new Callable() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$DeviceInjectorRemoteDevice$$ExternalSyntheticLambda2
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object lambda$close$9;
                    lambda$close$9 = DeviceInjectorSessionImpl.DeviceInjectorRemoteDevice.this.lambda$close$9();
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

        private CharacteristicBuilderImpl(DeviceInjectorSessionImpl deviceInjectorSessionImpl) {
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
        public DeviceInjectorSession.CharacteristicBuilder setSensorOrientation(int i) {
            this.mSensorOrientation = i;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setLensFacing(int i) {
            this.mLensFacing = i;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setAELockAvailable(boolean z) {
            this.mAELockAvailable = z;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder setAWBLockAvailable(boolean z) {
            this.mAWBLockAvailable = z;
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
        public DeviceInjectorSession.CharacteristicBuilder setFlashAvailable(boolean z) {
            this.mFlashAvailable = z;
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAEMode(int i) {
            this.mAEModes.add(Integer.valueOf(i));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAWBMode(int i) {
            this.mAWBModes.add(Integer.valueOf(i));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedAFMode(int i) {
            this.mAFModes.add(Integer.valueOf(i));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedEffectMode(int i) {
            this.mEffectModes.add(Integer.valueOf(i));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedSceneMode(int i) {
            this.mSceneModes.add(Integer.valueOf(i));
            if (i != 0) {
                this.mSceneModes.remove(0);
            }
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public DeviceInjectorSession.CharacteristicBuilder addSupportedControlMode(int i) {
            this.mControlMode.add(Integer.valueOf(i));
            return this;
        }

        @Override // android.hardware.camera2.DeviceInjectorSession.CharacteristicBuilder
        public CameraCharacteristics build() throws IllegalArgumentException {
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            if (this.mActiveArraySize == null) {
                throw new IllegalArgumentException("active array size is null");
            }
            int i = this.mSensorOrientation;
            if (i < 0 || 360 <= i || i % 90 != 0) {
                throw new IllegalArgumentException("sensor orientation is invalid");
            }
            int i2 = this.mLensFacing;
            if (i2 < 0 || 2 < i2) {
                throw new IllegalArgumentException("lens facing is invalid");
            }
            if (this.mStreamingSizes.isEmpty()) {
                throw new IllegalArgumentException("streaming size is empty");
            }
            Iterator<Size> it = this.mStreamingSizes.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    throw new IllegalArgumentException("streaming size contains null");
                }
            }
            Iterator<Size> it2 = this.mCaptureSizes.iterator();
            while (it2.hasNext()) {
                if (it2.next() == null) {
                    throw new IllegalArgumentException("capture size contains null");
                }
            }
            HashSet hashSet = new HashSet(this.mStreamingSizes);
            hashSet.retainAll(this.mCaptureSizes);
            if (!hashSet.isEmpty()) {
                throw new IllegalArgumentException("streaming size and capture size has common size");
            }
            if (this.mAEModes.isEmpty()) {
                throw new IllegalArgumentException("supported AE modes are empty");
            }
            for (Integer num : this.mAEModes) {
                if (num == null) {
                    throw new IllegalArgumentException("ae mode contains null");
                }
                if (num.intValue() < 0 || 5 < num.intValue()) {
                    throw new IllegalArgumentException("invalid ae mode");
                }
            }
            if (this.mAWBModes.isEmpty()) {
                throw new IllegalArgumentException("supported AWB modes are empty");
            }
            for (Integer num2 : this.mAWBModes) {
                if (num2 == null) {
                    throw new IllegalArgumentException("awb mode contains null");
                }
                if (num2.intValue() < 0 || 8 < num2.intValue()) {
                    throw new IllegalArgumentException("invalid awb mode");
                }
            }
            if (this.mAFModes.isEmpty()) {
                throw new IllegalArgumentException("supported AF modes are empty");
            }
            for (Integer num3 : this.mAFModes) {
                if (num3 == null) {
                    throw new IllegalArgumentException("af mode contains null");
                }
                if (num3.intValue() < 0 || 5 < num3.intValue()) {
                    throw new IllegalArgumentException("invalid af mode");
                }
            }
            if (this.mEffectModes.isEmpty()) {
                throw new IllegalArgumentException("supported effect modes are empty");
            }
            for (Integer num4 : this.mEffectModes) {
                if (num4 == null) {
                    throw new IllegalArgumentException("effect mode contains null");
                }
                if (num4.intValue() < 0 || 8 < num4.intValue()) {
                    throw new IllegalArgumentException("invalid effect mode");
                }
            }
            if (this.mSceneModes.isEmpty()) {
                throw new IllegalArgumentException("supported scene modes are empty");
            }
            for (Integer num5 : this.mSceneModes) {
                if (num5 == null) {
                    throw new IllegalArgumentException("scene mode contains null");
                }
                if (num5.intValue() < 0 || 16 < num5.intValue()) {
                    throw new IllegalArgumentException("invalid scene mode");
                }
            }
            if (this.mControlMode.isEmpty()) {
                throw new IllegalArgumentException("supported control modes are empty");
            }
            for (Integer num6 : this.mControlMode) {
                if (num6 == null) {
                    throw new IllegalArgumentException("scene mode contains null");
                }
                if (num6.intValue() < 0 || 4 < num6.intValue()) {
                    throw new IllegalArgumentException("invalid control mode");
                }
            }
            if (this.mControlMode.contains(2)) {
                if (this.mSceneModes.contains(0)) {
                    throw new IllegalArgumentException("control mode contains USE_SCENE_MODE but no valid scene mode exist");
                }
            } else if (!this.mSceneModes.contains(0)) {
                throw new IllegalArgumentException("control mode does not contains USE_SCENE_MODE but scene mode other than DISABLED exist");
            }
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Rect>>) CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE, (CameraCharacteristics.Key<Rect>) new Rect(0, 0, this.mActiveArraySize.getWidth(), this.mActiveArraySize.getHeight()));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Integer>>) CameraCharacteristics.SENSOR_ORIENTATION, (CameraCharacteristics.Key<Integer>) Integer.valueOf(this.mSensorOrientation));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Integer>>) CameraCharacteristics.LENS_FACING, (CameraCharacteristics.Key<Integer>) Integer.valueOf(this.mLensFacing));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>) CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE, (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mAELockAvailable));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>) CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE, (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mAWBLockAvailable));
            final ArrayList arrayList = new ArrayList();
            this.mStreamingSizes.stream().forEach(new Consumer() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$CharacteristicBuilderImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(new StreamConfiguration(35, r1.getWidth(), ((Size) obj).getHeight(), false));
                }
            });
            this.mCaptureSizes.stream().forEach(new Consumer() { // from class: android.hardware.camera2.impl.DeviceInjectorSessionImpl$CharacteristicBuilderImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(new StreamConfiguration(35, r1.getWidth(), ((Size) obj).getHeight(), true));
                }
            });
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<StreamConfiguration[]>>) CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS, (CameraCharacteristics.Key<StreamConfiguration[]>) arrayList.toArray(new StreamConfiguration[0]));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Boolean>>) CameraCharacteristics.FLASH_INFO_AVAILABLE, (CameraCharacteristics.Key<Boolean>) Boolean.valueOf(this.mFlashAvailable));
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES, (CameraCharacteristics.Key<int[]>) this.mAEModes.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES, (CameraCharacteristics.Key<int[]>) this.mAWBModes.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES, (CameraCharacteristics.Key<int[]>) this.mAFModes.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS, (CameraCharacteristics.Key<int[]>) this.mEffectModes.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES, (CameraCharacteristics.Key<int[]>) this.mSceneModes.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.CONTROL_AVAILABLE_MODES, (CameraCharacteristics.Key<int[]>) this.mControlMode.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            return new CameraCharacteristics(cameraMetadataNative);
        }
    }
}
