package android.accessibilityservice;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AccessibilityButtonController {
    private static final String LOG_TAG = "A11yButtonController";
    private ArrayMap<AccessibilityButtonCallback, Handler> mCallbacks;
    private final Object mLock = new Object();
    private final IAccessibilityServiceConnection mServiceConnection;

    public static abstract class AccessibilityButtonCallback {
        public void onAvailabilityChanged(AccessibilityButtonController accessibilityButtonController, boolean z) {
        }

        public void onClicked(AccessibilityButtonController accessibilityButtonController) {
        }
    }

    AccessibilityButtonController(IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mServiceConnection = iAccessibilityServiceConnection;
    }

    public boolean isAccessibilityButtonAvailable() {
        IAccessibilityServiceConnection iAccessibilityServiceConnection = this.mServiceConnection;
        if (iAccessibilityServiceConnection != null) {
            try {
                return iAccessibilityServiceConnection.isAccessibilityButtonAvailable();
            } catch (RemoteException e) {
                Slog.w(LOG_TAG, "Failed to get accessibility button availability.", e);
                e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback) {
        registerAccessibilityButtonCallback(accessibilityButtonCallback, new Handler(Looper.getMainLooper()));
    }

    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback, Handler handler) {
        Objects.requireNonNull(accessibilityButtonCallback);
        Objects.requireNonNull(handler);
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayMap<>();
            }
            this.mCallbacks.put(accessibilityButtonCallback, handler);
        }
    }

    public void unregisterAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback) {
        Objects.requireNonNull(accessibilityButtonCallback);
        synchronized (this.mLock) {
            ArrayMap<AccessibilityButtonCallback, Handler> arrayMap = this.mCallbacks;
            if (arrayMap == null) {
                return;
            }
            int iIndexOfKey = arrayMap.indexOfKey(accessibilityButtonCallback);
            if (iIndexOfKey >= 0) {
                this.mCallbacks.removeAt(iIndexOfKey);
            }
        }
    }

    void dispatchAccessibilityButtonClicked() {
        synchronized (this.mLock) {
            ArrayMap<AccessibilityButtonCallback, Handler> arrayMap = this.mCallbacks;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                ArrayMap arrayMap2 = new ArrayMap(this.mCallbacks);
                int size = arrayMap2.size();
                for (int i = 0; i < size; i++) {
                    final AccessibilityButtonCallback accessibilityButtonCallback = (AccessibilityButtonCallback) arrayMap2.keyAt(i);
                    ((Handler) arrayMap2.valueAt(i)).post(new Runnable() { // from class: android.accessibilityservice.AccessibilityButtonController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$dispatchAccessibilityButtonClicked$0(accessibilityButtonCallback);
                        }
                    });
                }
                return;
            }
            Slog.w(LOG_TAG, "Received accessibility button click with no callbacks!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchAccessibilityButtonClicked$0(AccessibilityButtonCallback accessibilityButtonCallback) {
        accessibilityButtonCallback.onClicked(this);
    }

    void dispatchAccessibilityButtonAvailabilityChanged(final boolean z) {
        synchronized (this.mLock) {
            ArrayMap<AccessibilityButtonCallback, Handler> arrayMap = this.mCallbacks;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                ArrayMap arrayMap2 = new ArrayMap(this.mCallbacks);
                int size = arrayMap2.size();
                for (int i = 0; i < size; i++) {
                    final AccessibilityButtonCallback accessibilityButtonCallback = (AccessibilityButtonCallback) arrayMap2.keyAt(i);
                    ((Handler) arrayMap2.valueAt(i)).post(new Runnable() { // from class: android.accessibilityservice.AccessibilityButtonController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$dispatchAccessibilityButtonAvailabilityChanged$1(accessibilityButtonCallback, z);
                        }
                    });
                }
                return;
            }
            Slog.w(LOG_TAG, "Received accessibility button availability change with no callbacks!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchAccessibilityButtonAvailabilityChanged$1(AccessibilityButtonCallback accessibilityButtonCallback, boolean z) {
        accessibilityButtonCallback.onAvailabilityChanged(this, z);
    }
}
