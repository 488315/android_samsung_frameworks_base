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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023 A[Catch: all -> 0x0047, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:7:0x000e, B:10:0x001c, B:11:0x001f, B:13:0x0023, B:14:0x002a, B:17:0x0038, B:18:0x003b, B:19:0x0045), top: B:24:0x0003, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003b A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:7:0x000e, B:10:0x001c, B:11:0x001f, B:13:0x0023, B:14:0x002a, B:17:0x0038, B:18:0x003b, B:19:0x0045), top: B:24:0x0003, inners: #1, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                if (this.mKnoxCallbackNotifier != null) {
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
                    this.mCallbacks.put(consumer, executor);
                    i = this.mKnoxState | this.mState;
                } else {
                    this.mCallbacks.put(consumer, executor);
                    i = this.mKnoxState | this.mState;
                }
            } else if (this.mKnoxCallbackNotifier != null) {
            }
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
            boolean zNeedToNotifyClient = needToNotifyClient(i);
            updateRecordingState(i, z);
            if (this.mCallbacks.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                final Consumer<Integer> consumerKeyAt = this.mCallbacks.keyAt(i2);
                final Executor executorValueAt = this.mCallbacks.valueAt(i2);
                arrayList.add(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        executorValueAt.execute(new Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Integer.valueOf(i));
                            }
                        });
                    }
                });
            }
            checkAndNotifyCallbacks(arrayList, zNeedToNotifyClient);
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
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            for (int i = 0; i < list.size(); i++) {
                try {
                    list.get(i).run();
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }
    }
}
