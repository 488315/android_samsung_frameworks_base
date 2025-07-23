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

    int addCallback(Executor executor, Consumer<Integer> consumer) {
        int i;
        synchronized (sLock) {
            if (this.mCallbackNotifier == null) {
                this.mCallbackNotifier = new IScreenRecordingCallback.Stub() { // from class: android.view.ScreenRecordingCallbacks.1
                    @Override // android.window.IScreenRecordingCallback
                    public void onScreenRecordingStateChanged(boolean z) {
                        ScreenRecordingCallbacks.this.notifyCallbacks(z ? 1 : 0);
                    }
                };
                try {
                    this.mState = getWindowManagerService().registerScreenRecordingCallback(this.mCallbackNotifier) ? 1 : 0;
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            if (this.mKnoxCallbackNotifier == null) {
                this.mKnoxCallbackNotifier = new IScreenRecordingCallback.Stub() { // from class: android.view.ScreenRecordingCallbacks.2
                    @Override // android.window.IScreenRecordingCallback
                    public void onScreenRecordingStateChanged(boolean z) {
                        ScreenRecordingCallbacks.this.notifyCallbacks(z ? 1 : 0, true);
                    }
                };
                try {
                    this.mKnoxState = getWindowManagerService().registerKnoxRemoteScreenCallback(this.mKnoxCallbackNotifier) ? 1 : 0;
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
            }
            this.mCallbacks.put(consumer, executor);
            i = this.mKnoxState | this.mState;
        }
        return i;
    }

    void removeCallback(Consumer<Integer> consumer) {
        synchronized (sLock) {
            this.mCallbacks.remove(consumer);
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
    public void notifyCallbacks(int i) {
        notifyCallbacks(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallbacks(final int i, boolean z) {
        synchronized (sLock) {
            boolean needToNotifyClient = needToNotifyClient(i);
            updateRecordingState(i, z);
            if (this.mCallbacks.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                final Consumer<Integer> keyAt = this.mCallbacks.keyAt(i2);
                final Executor valueAt = this.mCallbacks.valueAt(i2);
                arrayList.add(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        valueAt.execute(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                r1.accept(Integer.valueOf(r2));
                            }
                        });
                    }
                });
            }
            checkAndNotifyCallbacks(arrayList, needToNotifyClient);
        }
    }

    private boolean needToNotifyClient(int i) {
        return isRecordingStopped() && i == 1;
    }

    private void updateRecordingState(int i, boolean z) {
        if (z) {
            this.mKnoxState = i;
        } else {
            this.mState = i;
        }
    }

    private boolean isRecordingStopped() {
        return this.mKnoxState == 0 && this.mState == 0;
    }

    private void checkAndNotifyCallbacks(List<Runnable> list, boolean z) {
        if (z || isRecordingStopped()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            for (int i = 0; i < list.size(); i++) {
                try {
                    list.get(i).run();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }
}
