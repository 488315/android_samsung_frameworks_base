package android.hardware.devicestate;

import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class DeviceStateManagerGlobal {
  public static final int FOLD_STATE_CLOSE = 0;
  public static final int FOLD_STATE_DUAL = 4;
  public static final int FOLD_STATE_HALF_OPEN = 2;
  public static final int FOLD_STATE_OPEN = 3;
  public static final int FOLD_STATE_REAR_DUAL = 5;
  public static final int FOLD_STATE_TENT = 1;
  public static final int FOLD_STATE_UNKNOWN = -1;
  private static final String TAG = "DeviceStateManager";
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
    IBinder b;
    synchronized (DeviceStateManagerGlobal.class) {
      if (sInstance == null
          && (b = ServiceManager.getService(Context.DEVICE_STATE_SERVICE)) != null) {
        sInstance = new DeviceStateManagerGlobal(IDeviceStateManager.Stub.asInterface(b));
      }
      deviceStateManagerGlobal = sInstance;
    }
    return deviceStateManagerGlobal;
  }

  public DeviceStateManagerGlobal(IDeviceStateManager deviceStateManager) {
    this.mDeviceStateManager = deviceStateManager;
    registerCallbackIfNeededLocked();
  }

  public int[] getSupportedStates() {
    int[] copyOf;
    synchronized (this.mLock) {
      DeviceStateInfo currentInfo = this.mLastReceivedInfo;
      if (currentInfo == null) {
        try {
          currentInfo = this.mDeviceStateManager.getDeviceStateInfo();
        } catch (RemoteException ex) {
          throw ex.rethrowFromSystemServer();
        }
      }
      copyOf = Arrays.copyOf(currentInfo.supportedStates, currentInfo.supportedStates.length);
    }
    return copyOf;
  }

  public void requestState(
      DeviceStateRequest request, Executor executor, DeviceStateRequest.Callback callback) {
    DeviceStateRequestWrapper requestWrapper =
        new DeviceStateRequestWrapper(request, callback, executor);
    synchronized (this.mLock) {
      if (findRequestTokenLocked(request) != null) {
        return;
      }
      IBinder token = new Binder();
      this.mRequests.put(token, requestWrapper);
      try {
        this.mDeviceStateManager.requestState(token, request.getState(), request.getFlags());
      } catch (RemoteException ex) {
        this.mRequests.remove(token);
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  public void cancelStateRequest() {
    synchronized (this.mLock) {
      try {
        try {
          this.mDeviceStateManager.cancelStateRequest();
        } catch (RemoteException ex) {
          throw ex.rethrowFromSystemServer();
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public void requestBaseStateOverride(
      DeviceStateRequest request, Executor executor, DeviceStateRequest.Callback callback) {
    DeviceStateRequestWrapper requestWrapper =
        new DeviceStateRequestWrapper(request, callback, executor);
    synchronized (this.mLock) {
      if (findRequestTokenLocked(request) != null) {
        return;
      }
      IBinder token = new Binder();
      this.mRequests.put(token, requestWrapper);
      try {
        this.mDeviceStateManager.requestBaseStateOverride(
            token, request.getState(), request.getFlags());
      } catch (RemoteException ex) {
        this.mRequests.remove(token);
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  public void cancelBaseStateOverride() {
    synchronized (this.mLock) {
      try {
        try {
          this.mDeviceStateManager.cancelBaseStateOverride();
        } catch (RemoteException ex) {
          throw ex.rethrowFromSystemServer();
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public void registerDeviceStateCallback(
      DeviceStateManager.DeviceStateCallback callback, Executor executor) {
    synchronized (this.mLock) {
      int index = findCallbackLocked(callback);
      if (index != -1) {
        return;
      }
      DeviceStateCallbackWrapper wrapper = new DeviceStateCallbackWrapper(callback, executor);
      this.mCallbacks.add(wrapper);
      DeviceStateInfo deviceStateInfo = this.mLastReceivedInfo;
      if (deviceStateInfo != null) {
        int[] supportedStates =
            Arrays.copyOf(
                deviceStateInfo.supportedStates, this.mLastReceivedInfo.supportedStates.length);
        wrapper.notifySupportedStatesChanged(supportedStates);
        wrapper.notifyBaseStateChanged(this.mLastReceivedInfo.baseState);
        wrapper.notifyStateChanged(this.mLastReceivedInfo.currentState);
      }
    }
  }

  public void unregisterDeviceStateCallback(DeviceStateManager.DeviceStateCallback callback) {
    synchronized (this.mLock) {
      int indexToRemove = findCallbackLocked(callback);
      if (indexToRemove != -1) {
        this.mCallbacks.remove(indexToRemove);
      }
    }
  }

  public void onStateRequestOverlayDismissed(boolean shouldCancelRequest) {
    try {
      this.mDeviceStateManager.onStateRequestOverlayDismissed(shouldCancelRequest);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  private void registerCallbackIfNeededLocked() {
    if (this.mCallback == null) {
      DeviceStateManagerCallback deviceStateManagerCallback = new DeviceStateManagerCallback();
      this.mCallback = deviceStateManagerCallback;
      try {
        this.mDeviceStateManager.registerCallback(deviceStateManagerCallback);
      } catch (RemoteException ex) {
        this.mCallback = null;
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  private int findCallbackLocked(DeviceStateManager.DeviceStateCallback callback) {
    for (int i = 0; i < this.mCallbacks.size(); i++) {
      if (this.mCallbacks.get(i).mDeviceStateCallback.equals(callback)) {
        return i;
      }
    }
    return -1;
  }

  private IBinder findRequestTokenLocked(DeviceStateRequest request) {
    for (int i = 0; i < this.mRequests.size(); i++) {
      if (this.mRequests.valueAt(i).mRequest.equals(request)) {
        return this.mRequests.keyAt(i);
      }
    }
    return null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleDeviceStateInfoChanged(DeviceStateInfo info) {
    DeviceStateInfo oldInfo;
    ArrayList<DeviceStateCallbackWrapper> callbacks;
    synchronized (this.mLock) {
      oldInfo = this.mLastReceivedInfo;
      this.mLastReceivedInfo = info;
      callbacks = new ArrayList<>(this.mCallbacks);
    }
    int diff = oldInfo == null ? -1 : info.diff(oldInfo);
    if ((diff & 1) > 0) {
      for (int i = 0; i < callbacks.size(); i++) {
        int[] supportedStates = Arrays.copyOf(info.supportedStates, info.supportedStates.length);
        callbacks.get(i).notifySupportedStatesChanged(supportedStates);
      }
    }
    int i2 = diff & 2;
    if (i2 > 0) {
      for (int i3 = 0; i3 < callbacks.size(); i3++) {
        callbacks.get(i3).notifyBaseStateChanged(info.baseState);
      }
    }
    int i4 = diff & 4;
    if (i4 > 0) {
      for (int i5 = 0; i5 < callbacks.size(); i5++) {
        callbacks.get(i5).notifyStateChanged(info.currentState);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleRequestActive(IBinder token) {
    DeviceStateRequestWrapper request;
    synchronized (this.mLock) {
      request = this.mRequests.get(token);
    }
    if (request != null) {
      request.notifyRequestActive();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleRequestCanceled(IBinder token) {
    DeviceStateRequestWrapper request;
    synchronized (this.mLock) {
      request = this.mRequests.remove(token);
    }
    if (request != null) {
      request.notifyRequestCanceled();
    }
  }

  private final class DeviceStateManagerCallback extends IDeviceStateManagerCallback.Stub {
    private DeviceStateManagerCallback() {}

    @Override // android.hardware.devicestate.IDeviceStateManagerCallback
    public void onDeviceStateInfoChanged(DeviceStateInfo info) {
      DeviceStateManagerGlobal.this.handleDeviceStateInfoChanged(info);
    }

    @Override // android.hardware.devicestate.IDeviceStateManagerCallback
    public void onRequestActive(IBinder token) {
      DeviceStateManagerGlobal.this.handleRequestActive(token);
    }

    @Override // android.hardware.devicestate.IDeviceStateManagerCallback
    public void onRequestCanceled(IBinder token) {
      DeviceStateManagerGlobal.this.handleRequestCanceled(token);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class DeviceStateCallbackWrapper {
    private final DeviceStateManager.DeviceStateCallback mDeviceStateCallback;
    private final Executor mExecutor;

    DeviceStateCallbackWrapper(DeviceStateManager.DeviceStateCallback callback, Executor executor) {
      this.mDeviceStateCallback = callback;
      this.mExecutor = executor;
    }

    void notifySupportedStatesChanged(final int[] newSupportedStates) {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this
                  .lambda$notifySupportedStatesChanged$0(newSupportedStates);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySupportedStatesChanged$0(int[] newSupportedStates) {
      this.mDeviceStateCallback.onSupportedStatesChanged(newSupportedStates);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyBaseStateChanged$1(int newBaseState) {
      this.mDeviceStateCallback.onBaseStateChanged(newBaseState);
    }

    void notifyBaseStateChanged(final int newBaseState) {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this
                  .lambda$notifyBaseStateChanged$1(newBaseState);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyStateChanged$2(int newDeviceState) {
      this.mDeviceStateCallback.onStateChanged(newDeviceState);
    }

    void notifyStateChanged(final int newDeviceState) {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifyStateChanged$2(
                  newDeviceState);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class DeviceStateRequestWrapper {
    private final DeviceStateRequest.Callback mCallback;
    private final Executor mExecutor;
    private final DeviceStateRequest mRequest;

    DeviceStateRequestWrapper(
        DeviceStateRequest request, DeviceStateRequest.Callback callback, Executor executor) {
      validateRequestWrapperParameters(callback, executor);
      this.mRequest = request;
      this.mCallback = callback;
      this.mExecutor = executor;
    }

    void notifyRequestActive() {
      if (this.mCallback == null) {
        return;
      }
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DeviceStateManagerGlobal.DeviceStateRequestWrapper.this
                  .lambda$notifyRequestActive$0();
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
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              DeviceStateManagerGlobal.DeviceStateRequestWrapper.this
                  .lambda$notifyRequestCanceled$1();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRequestCanceled$1() {
      this.mCallback.onRequestCanceled(this.mRequest);
    }

    private void validateRequestWrapperParameters(
        DeviceStateRequest.Callback callback, Executor executor) {
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

    SemFoldStateListener(SemWindowManager.FoldStateListener listener) {
      this.mListener = listener;
    }

    @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
    public void onStateChanged(int state) {
      boolean folded = state == 0 || state == 1 || state == 5;
      Boolean bool = this.mFolded;
      if (bool == null || folded != bool.booleanValue()) {
        this.mFolded = Boolean.valueOf(folded);
        this.mListener.onFoldStateChanged(folded);
      }
      boolean tableMode = state == 2;
      Boolean bool2 = this.mTableMode;
      if (bool2 == null || tableMode != bool2.booleanValue()) {
        this.mTableMode = Boolean.valueOf(tableMode);
        this.mListener.onTableModeChanged(tableMode);
      }
    }
  }

  public void registerFoldStateListener(
      SemWindowManager.FoldStateListener listener, Handler handler) {
    Log.m94d(TAG, "This device does not support FoldStateListener!");
  }

  public void unregisterFoldStateListener(SemWindowManager.FoldStateListener listener) {
    synchronized (this.mFoldStateListeners) {
      int index = findFoldStateListenersLocked(listener);
      if (index != -1) {
        unregisterDeviceStateCallback(this.mFoldStateListeners.remove(index));
      }
    }
  }

  private int findFoldStateListenersLocked(SemWindowManager.FoldStateListener listener) {
    int numListeners = this.mFoldStateListeners.size();
    for (int i = 0; i < numListeners; i++) {
      if (this.mFoldStateListeners.get(i).mListener.equals(listener)) {
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
    if (looper == null) {
      throw new RuntimeException("Could not get Looper for the UI thread.");
    }
    return looper;
  }
}
