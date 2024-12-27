package android.view;

import android.os.Binder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.window.IScreenRecordingCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class ScreenRecordingCallbacks {
    private static ScreenRecordingCallbacks sInstance;
    private static final Object sLock = new Object();
    private IScreenRecordingCallback mCallbackNotifier;
    private IScreenRecordingCallback mKnoxCallbackNotifier;
    private final ArrayMap<Consumer<Integer>, Executor> mCallbacks = new ArrayMap<>();
    private int mState = 0;
    private int mKnoxState = 0;

    private ScreenRecordingCallbacks() {
    }

    private static IWindowManager getWindowManagerService() {
        return (IWindowManager) Objects.requireNonNull(WindowManagerGlobal.getWindowManagerService());
    }

    static ScreenRecordingCallbacks getInstance() {
        ScreenRecordingCallbacks screenRecordingCallbacks;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ScreenRecordingCallbacks();
            }
            screenRecordingCallbacks = sInstance;
        }
        return screenRecordingCallbacks;
    }

    int addCallback(Executor executor, Consumer<Integer> callback) {
        int i;
        int i2;
        synchronized (sLock) {
            int i3 = 1;
            if (this.mCallbackNotifier == null) {
                this.mCallbackNotifier = new IScreenRecordingCallback.Stub() { // from class: android.view.ScreenRecordingCallbacks.1
                    @Override // android.window.IScreenRecordingCallback
                    public void onScreenRecordingStateChanged(boolean visibleInScreenRecording) {
                        int state;
                        if (visibleInScreenRecording) {
                            state = 1;
                        } else {
                            state = 0;
                        }
                        ScreenRecordingCallbacks.this.notifyCallbacks(state);
                    }
                };
                try {
                    boolean visibleInScreenRecording = getWindowManagerService().registerScreenRecordingCallback(this.mCallbackNotifier);
                    if (visibleInScreenRecording) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    this.mState = i2;
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            if (this.mKnoxCallbackNotifier == null) {
                this.mKnoxCallbackNotifier = new IScreenRecordingCallback.Stub() { // from class: android.view.ScreenRecordingCallbacks.2
                    @Override // android.window.IScreenRecordingCallback
                    public void onScreenRecordingStateChanged(boolean visibleInKnoxRemoteScreen) {
                        int knoxState;
                        if (visibleInKnoxRemoteScreen) {
                            knoxState = 1;
                        } else {
                            knoxState = 0;
                        }
                        ScreenRecordingCallbacks.this.notifyCallbacks(knoxState, true);
                    }
                };
                try {
                    boolean visibleInKnoxRemoteScreen = getWindowManagerService().registerKnoxRemoteScreenCallback(this.mKnoxCallbackNotifier);
                    if (!visibleInKnoxRemoteScreen) {
                        i3 = 0;
                    }
                    this.mKnoxState = i3;
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
            }
            this.mCallbacks.put(callback, executor);
            i = this.mState | this.mKnoxState;
        }
        return i;
    }

    void removeCallback(Consumer<Integer> callback) {
        synchronized (sLock) {
            this.mCallbacks.remove(callback);
            if (this.mCallbacks.isEmpty()) {
                try {
                    getWindowManagerService().unregisterScreenRecordingCallback(this.mCallbackNotifier);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                this.mCallbackNotifier = null;
                try {
                    getWindowManagerService().unregisterKnoxRemoteScreenCallback(this.mKnoxCallbackNotifier);
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
                this.mKnoxCallbackNotifier = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallbacks(int state) {
        notifyCallbacks(state, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallbacks(final int state, boolean isKnox) {
        synchronized (sLock) {
            boolean notifyClient = needToNotifyClient(state);
            updateRecordingState(state, isKnox);
            if (this.mCallbacks.isEmpty()) {
                return;
            }
            List<Runnable> callbacks = new ArrayList<>();
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                final Consumer<Integer> callback = this.mCallbacks.keyAt(i);
                final Executor executor = this.mCallbacks.valueAt(i);
                callbacks.add(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        executor.execute(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                r1.accept(Integer.valueOf(r2));
                            }
                        });
                    }
                });
            }
            checkAndNotifyCallbacks(callbacks, notifyClient);
        }
    }

    private boolean needToNotifyClient(int state) {
        if (!isRecordingStopped()) {
            return false;
        }
        boolean notifyClient = state == 1;
        return notifyClient;
    }

    private void updateRecordingState(int state, boolean isKnox) {
        if (isKnox) {
            this.mKnoxState = state;
        } else {
            this.mState = state;
        }
    }

    private boolean isRecordingStopped() {
        return this.mKnoxState == 0 && this.mState == 0;
    }

    private void checkAndNotifyCallbacks(List<Runnable> callbacks, boolean notifyClient) {
        if (notifyClient || isRecordingStopped()) {
            long token = Binder.clearCallingIdentity();
            for (int i = 0; i < callbacks.size(); i++) {
                try {
                    callbacks.get(i).run();
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }
    }
}
