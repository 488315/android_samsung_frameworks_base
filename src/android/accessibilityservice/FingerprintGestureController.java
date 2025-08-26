package android.accessibilityservice;

import android.os.Handler;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;

/* loaded from: classes.dex */
public final class FingerprintGestureController {
    public static final int FINGERPRINT_GESTURE_SWIPE_DOWN = 8;
    public static final int FINGERPRINT_GESTURE_SWIPE_LEFT = 2;
    public static final int FINGERPRINT_GESTURE_SWIPE_RIGHT = 1;
    public static final int FINGERPRINT_GESTURE_SWIPE_UP = 4;
    private static final String LOG_TAG = "FingerprintGestureController";
    private final IAccessibilityServiceConnection mAccessibilityServiceConnection;
    private final Object mLock = new Object();
    private final ArrayMap<FingerprintGestureCallback, Handler> mCallbackHandlerMap = new ArrayMap<>(1);

    public static abstract class FingerprintGestureCallback {
        public void onGestureDetected(int i) {
        }

        public void onGestureDetectionAvailabilityChanged(boolean z) {
        }
    }

    public FingerprintGestureController(IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mAccessibilityServiceConnection = iAccessibilityServiceConnection;
    }

    public boolean isGestureDetectionAvailable() {
        try {
            return this.mAccessibilityServiceConnection.isFingerprintGestureDetectionAvailable();
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Failed to check if fingerprint gestures are active", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerFingerprintGestureCallback(FingerprintGestureCallback fingerprintGestureCallback, Handler handler) {
        synchronized (this.mLock) {
            this.mCallbackHandlerMap.put(fingerprintGestureCallback, handler);
        }
    }

    public void unregisterFingerprintGestureCallback(FingerprintGestureCallback fingerprintGestureCallback) {
        synchronized (this.mLock) {
            this.mCallbackHandlerMap.remove(fingerprintGestureCallback);
        }
    }

    public void onGestureDetectionActiveChanged(final boolean z) {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mCallbackHandlerMap);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            final FingerprintGestureCallback fingerprintGestureCallback = (FingerprintGestureCallback) arrayMap.keyAt(i);
            Handler handler = (Handler) arrayMap.valueAt(i);
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.accessibilityservice.FingerprintGestureController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        fingerprintGestureCallback.onGestureDetectionAvailabilityChanged(z);
                    }
                });
            } else {
                fingerprintGestureCallback.onGestureDetectionAvailabilityChanged(z);
            }
        }
    }

    public void onGesture(final int i) {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mCallbackHandlerMap);
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            final FingerprintGestureCallback fingerprintGestureCallback = (FingerprintGestureCallback) arrayMap.keyAt(i2);
            Handler handler = (Handler) arrayMap.valueAt(i2);
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.accessibilityservice.FingerprintGestureController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        fingerprintGestureCallback.onGestureDetected(i);
                    }
                });
            } else {
                fingerprintGestureCallback.onGestureDetected(i);
            }
        }
    }
}
