package android.hardware.devicestate;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateRequest;
import android.hardware.devicestate.IDeviceStateManager;
import android.hardware.devicestate.IDeviceStateManagerCallback;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class DeviceStateManagerGlobal {
    private static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final int FOLD_STATE_CLOSE = 0;
    public static final int FOLD_STATE_DUAL = 4;
    public static final int FOLD_STATE_HALF_CLOSE = 6;
    public static final int FOLD_STATE_HALF_OPEN = 2;
    public static final int FOLD_STATE_OPEN = 3;
    public static final int FOLD_STATE_REAR_DUAL = 5;
    public static final int FOLD_STATE_TENT = 1;
    public static final int FOLD_STATE_UNKNOWN = -1;
    private static final String TAG = "DeviceStateManagerGlobal";
    private static DeviceStateManagerGlobal sInstance;
    private DeviceStateManagerCallback mCallback;
    private final IDeviceStateManager mDeviceStateManager;
    private DeviceStateInfo mLastReceivedInfo;
    private final Object mLock = new Object();
    private final ArrayList<DeviceStateCallbackWrapper> mCallbacks = new ArrayList<>();
    private final ArrayMap<IBinder, DeviceStateRequestWrapper> mRequests = new ArrayMap<>();
    private final ArrayList<SemFoldStateListener> mFoldStateListeners = new ArrayList<>();

    public static DeviceStateManagerGlobal getInstance() {
        DeviceStateManagerGlobal deviceStateManagerGlobal;
        IBinder service;
        synchronized (DeviceStateManagerGlobal.class) {
            if (sInstance == null && (service = ServiceManager.getService(Context.DEVICE_STATE_SERVICE)) != null) {
                sInstance = new DeviceStateManagerGlobal(IDeviceStateManager.Stub.asInterface(service));
            }
            deviceStateManagerGlobal = sInstance;
        }
        return deviceStateManagerGlobal;
    }

    public DeviceStateManagerGlobal(IDeviceStateManager iDeviceStateManager) {
        this.mDeviceStateManager = iDeviceStateManager;
        registerCallbackLocked();
    }

    public List<DeviceState> getSupportedDeviceStates() {
        List<DeviceState> listCopyOf;
        synchronized (this.mLock) {
            DeviceStateInfo deviceStateInfo = this.mLastReceivedInfo;
            if (deviceStateInfo == null) {
                try {
                    deviceStateInfo = this.mDeviceStateManager.getDeviceStateInfo();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            listCopyOf = List.copyOf(deviceStateInfo.supportedStates);
        }
        return listCopyOf;
    }

    public void requestState(DeviceStateRequest deviceStateRequest, Executor executor, DeviceStateRequest.Callback callback) {
        DeviceStateRequestWrapper deviceStateRequestWrapper = new DeviceStateRequestWrapper(deviceStateRequest, callback, executor);
        synchronized (this.mLock) {
            if (findRequestTokenLocked(deviceStateRequest) != null) {
                return;
            }
            Binder binder = new Binder();
            this.mRequests.put(binder, deviceStateRequestWrapper);
            try {
                this.mDeviceStateManager.requestState(binder, deviceStateRequest.getState(), deviceStateRequest.getFlags());
            } catch (RemoteException e) {
                this.mRequests.remove(binder);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelStateRequest() {
        synchronized (this.mLock) {
            try {
                try {
                    this.mDeviceStateManager.cancelStateRequest();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void requestBaseStateOverride(DeviceStateRequest deviceStateRequest, Executor executor, DeviceStateRequest.Callback callback) {
        DeviceStateRequestWrapper deviceStateRequestWrapper = new DeviceStateRequestWrapper(deviceStateRequest, callback, executor);
        synchronized (this.mLock) {
            if (findRequestTokenLocked(deviceStateRequest) != null) {
                return;
            }
            Binder binder = new Binder();
            this.mRequests.put(binder, deviceStateRequestWrapper);
            try {
                this.mDeviceStateManager.requestBaseStateOverride(binder, deviceStateRequest.getState(), deviceStateRequest.getFlags());
            } catch (RemoteException e) {
                this.mRequests.remove(binder);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelBaseStateOverride() {
        synchronized (this.mLock) {
            try {
                try {
                    this.mDeviceStateManager.cancelBaseStateOverride();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void registerDeviceStateCallback(DeviceStateManager.DeviceStateCallback deviceStateCallback, Executor executor) {
        synchronized (this.mLock) {
            if (findCallbackLocked(deviceStateCallback) != -1) {
                return;
            }
            DeviceStateCallbackWrapper deviceStateCallbackWrapper = new DeviceStateCallbackWrapper(deviceStateCallback, executor);
            this.mCallbacks.add(deviceStateCallbackWrapper);
            DeviceStateInfo deviceStateInfo = this.mLastReceivedInfo;
            if (deviceStateInfo != null) {
                deviceStateCallbackWrapper.notifySupportedDeviceStatesChanged(List.copyOf(deviceStateInfo.supportedStates));
                deviceStateCallbackWrapper.notifyDeviceStateChanged(this.mLastReceivedInfo.currentState);
            }
        }
    }

    public void unregisterDeviceStateCallback(DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        synchronized (this.mLock) {
            int iFindCallbackLocked = findCallbackLocked(deviceStateCallback);
            if (iFindCallbackLocked != -1) {
                this.mCallbacks.remove(iFindCallbackLocked);
            }
        }
    }

    public void onStateRequestOverlayDismissed(boolean z) {
        try {
            this.mDeviceStateManager.onStateRequestOverlayDismissed(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void registerCallbackLocked() {
        this.mCallback = new DeviceStateManagerCallback();
        try {
            if (Flags.wlinfoOncreate()) {
                synchronized (this.mLock) {
                    this.mLastReceivedInfo = this.mDeviceStateManager.registerCallback(this.mCallback);
                }
                return;
            }
            this.mDeviceStateManager.registerCallback(this.mCallback);
        } catch (RemoteException e) {
            this.mCallback = null;
            throw e.rethrowFromSystemServer();
        }
    }

    private int findCallbackLocked(DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).mDeviceStateCallback.equals(deviceStateCallback)) {
                return i;
            }
        }
        return -1;
    }

    private IBinder findRequestTokenLocked(DeviceStateRequest deviceStateRequest) {
        for (int i = 0; i < this.mRequests.size(); i++) {
            if (this.mRequests.valueAt(i).mRequest.equals(deviceStateRequest)) {
                return this.mRequests.keyAt(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceStateInfoChanged(DeviceStateInfo deviceStateInfo) {
        DeviceStateInfo deviceStateInfo2;
        ArrayList arrayList;
        synchronized (this.mLock) {
            deviceStateInfo2 = this.mLastReceivedInfo;
            this.mLastReceivedInfo = deviceStateInfo;
            arrayList = new ArrayList(this.mCallbacks);
        }
        int iDiff = deviceStateInfo2 == null ? -1 : deviceStateInfo.diff(deviceStateInfo2);
        if ((iDiff & 1) > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                ((DeviceStateCallbackWrapper) arrayList.get(i)).notifySupportedDeviceStatesChanged(List.copyOf(deviceStateInfo.supportedStates));
            }
        }
        if ((iDiff & 4) > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ((DeviceStateCallbackWrapper) arrayList.get(i2)).notifyDeviceStateChanged(deviceStateInfo.currentState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestActive(IBinder iBinder) {
        DeviceStateRequestWrapper deviceStateRequestWrapper;
        synchronized (this.mLock) {
            deviceStateRequestWrapper = this.mRequests.get(iBinder);
        }
        if (deviceStateRequestWrapper != null) {
            deviceStateRequestWrapper.notifyRequestActive();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestCanceled(IBinder iBinder) {
        DeviceStateRequestWrapper deviceStateRequestWrapperRemove;
        synchronized (this.mLock) {
            deviceStateRequestWrapperRemove = this.mRequests.remove(iBinder);
        }
        if (deviceStateRequestWrapperRemove != null) {
            deviceStateRequestWrapperRemove.notifyRequestCanceled();
        }
    }

    private final class DeviceStateManagerCallback extends IDeviceStateManagerCallback.Stub {
        private DeviceStateManagerCallback() {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onDeviceStateInfoChanged(DeviceStateInfo deviceStateInfo) {
            DeviceStateManagerGlobal.this.handleDeviceStateInfoChanged(deviceStateInfo);
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestActive(IBinder iBinder) {
            DeviceStateManagerGlobal.this.handleRequestActive(iBinder);
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestCanceled(IBinder iBinder) {
            DeviceStateManagerGlobal.this.handleRequestCanceled(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DeviceStateCallbackWrapper {
        private final DeviceStateManager.DeviceStateCallback mDeviceStateCallback;
        private final Executor mExecutor;

        DeviceStateCallbackWrapper(DeviceStateManager.DeviceStateCallback deviceStateCallback, Executor executor) {
            this.mDeviceStateCallback = deviceStateCallback;
            this.mExecutor = executor;
        }

        void notifySupportedDeviceStatesChanged(final List<DeviceState> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifySupportedDeviceStatesChanged$0(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySupportedDeviceStatesChanged$0(List list) {
            this.mDeviceStateCallback.onSupportedStatesChanged(list);
        }

        void notifyDeviceStateChanged(final DeviceState deviceState) {
            execute("notifyDeviceStateChanged", new Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyDeviceStateChanged$1(deviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyDeviceStateChanged$1(DeviceState deviceState) {
            this.mDeviceStateCallback.onDeviceStateChanged(deviceState);
        }

        private void execute(final String str, final Runnable runnable) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$execute$2(str, runnable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$execute$2(String str, Runnable runnable) {
            if (DeviceStateManagerGlobal.DEBUG) {
                Trace.beginSection(this.mDeviceStateCallback.getClass().getSimpleName() + "#" + str);
            }
            try {
                runnable.run();
            } finally {
                if (DeviceStateManagerGlobal.DEBUG) {
                    Trace.endSection();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DeviceStateRequestWrapper {
        private final DeviceStateRequest.Callback mCallback;
        private final Executor mExecutor;
        private final DeviceStateRequest mRequest;

        DeviceStateRequestWrapper(DeviceStateRequest deviceStateRequest, DeviceStateRequest.Callback callback, Executor executor) {
            validateRequestWrapperParameters(callback, executor);
            this.mRequest = deviceStateRequest;
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        void notifyRequestActive() {
            if (this.mCallback == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyRequestActive$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestActive$0() {
            this.mCallback.onRequestActivated(this.mRequest);
        }

        void notifyRequestCanceled() {
            if (this.mCallback == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyRequestCanceled$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestCanceled$1() {
            this.mCallback.onRequestCanceled(this.mRequest);
        }

        private void validateRequestWrapperParameters(DeviceStateRequest.Callback callback, Executor executor) {
            if (callback == null && executor != null) {
                throw new IllegalArgumentException("Callback must be supplied with executor.");
            }
            if (executor == null && callback != null) {
                throw new IllegalArgumentException("Executor must be supplied with callback.");
            }
        }
    }

    class SemFoldStateListener implements DeviceStateManager.DeviceStateCallback {
        private Boolean mFolded;
        private SemWindowManager.FoldStateListener mListener;
        private Boolean mTableMode;

        SemFoldStateListener(DeviceStateManagerGlobal deviceStateManagerGlobal, SemWindowManager.FoldStateListener foldStateListener) {
            this.mListener = foldStateListener;
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public void onDeviceStateChanged(DeviceState deviceState) {
            int identifier = deviceState.getIdentifier();
            boolean z = identifier == 0 || identifier == 1 || identifier == 5;
            Boolean bool = this.mFolded;
            if (bool == null || z != bool.booleanValue()) {
                this.mFolded = Boolean.valueOf(z);
                this.mListener.onFoldStateChanged(z);
            }
            boolean z2 = identifier == 2;
            Boolean bool2 = this.mTableMode;
            if (bool2 == null || z2 != bool2.booleanValue()) {
                this.mTableMode = Boolean.valueOf(z2);
                this.mListener.onTableModeChanged(z2);
            }
        }
    }

    public void registerFoldStateListener(SemWindowManager.FoldStateListener foldStateListener, Handler handler) {
        Log.d(TAG, "This device does not support FoldStateListener!");
    }

    public void unregisterFoldStateListener(SemWindowManager.FoldStateListener foldStateListener) {
        synchronized (this.mFoldStateListeners) {
            int iFindFoldStateListenersLocked = findFoldStateListenersLocked(foldStateListener);
            if (iFindFoldStateListenersLocked != -1) {
                unregisterDeviceStateCallback(this.mFoldStateListeners.remove(iFindFoldStateListenersLocked));
            }
        }
    }

    private int findFoldStateListenersLocked(SemWindowManager.FoldStateListener foldStateListener) {
        int size = this.mFoldStateListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mFoldStateListeners.get(i).mListener.equals(foldStateListener)) {
                return i;
            }
        }
        return -1;
    }

    private static Looper getLooperForHandler(Handler handler) {
        Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        if (looper != null) {
            return looper;
        }
        throw new RuntimeException("Could not get Looper for the UI thread.");
    }
}
