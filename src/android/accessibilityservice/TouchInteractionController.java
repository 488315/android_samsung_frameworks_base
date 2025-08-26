package android.accessibilityservice;

import android.os.RemoteException;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class TouchInteractionController {
    private static final int MAX_POINTER_COUNT = 32;
    public static final int STATE_CLEAR = 0;
    public static final int STATE_DELEGATING = 4;
    public static final int STATE_DRAGGING = 3;
    public static final int STATE_TOUCH_EXPLORING = 2;
    public static final int STATE_TOUCH_INTERACTING = 1;
    private ArrayMap<Callback, Executor> mCallbacks;
    private final int mDisplayId;
    private final Object mLock;
    private final AccessibilityService mService;
    private boolean mServiceDetectsGestures;
    private Queue<MotionEvent> mQueuedMotionEvents = new LinkedList();
    private boolean mStateChangeRequested = false;
    private int mState = 0;

    public interface Callback {
        void onMotionEvent(MotionEvent motionEvent);

        void onStateChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    public int getMaxPointerCount() {
        return 32;
    }

    TouchInteractionController(AccessibilityService accessibilityService, Object obj, int i) {
        this.mDisplayId = i;
        this.mLock = obj;
        this.mService = accessibilityService;
    }

    public void registerCallback(Executor executor, Callback callback) {
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayMap<>();
            }
            this.mCallbacks.put(callback, executor);
            if (this.mCallbacks.size() == 1) {
                setServiceDetectsGestures(true);
            }
        }
    }

    public boolean unregisterCallback(Callback callback) {
        boolean z;
        if (this.mCallbacks == null) {
            return false;
        }
        synchronized (this.mLock) {
            z = this.mCallbacks.remove(callback) != null;
            if (z && this.mCallbacks.size() == 0) {
                setServiceDetectsGestures(false);
            }
        }
        return z;
    }

    public void unregisterAllCallbacks() {
        if (this.mCallbacks != null) {
            synchronized (this.mLock) {
                this.mCallbacks.clear();
                setServiceDetectsGestures(false);
            }
        }
    }

    void onMotionEvent(MotionEvent motionEvent) {
        if (this.mStateChangeRequested) {
            this.mQueuedMotionEvents.add(motionEvent);
        } else {
            sendEventToAllListeners(motionEvent);
        }
    }

    private void sendEventToAllListeners(final MotionEvent motionEvent) {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mCallbacks);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            final Callback callback = (Callback) arrayMap.keyAt(i);
            Executor executor = (Executor) arrayMap.valueAt(i);
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.accessibilityservice.TouchInteractionController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.onMotionEvent(motionEvent);
                    }
                });
            } else {
                callback.onMotionEvent(motionEvent);
            }
        }
    }

    void onStateChanged(final int i) {
        ArrayMap arrayMap;
        this.mState = i;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mCallbacks);
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            final Callback callback = (Callback) arrayMap.keyAt(i2);
            Executor executor = (Executor) arrayMap.valueAt(i2);
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.accessibilityservice.TouchInteractionController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.onStateChanged(i);
                    }
                });
            } else {
                callback.onStateChanged(i);
            }
        }
        this.mStateChangeRequested = false;
        while (this.mQueuedMotionEvents.size() > 0) {
            sendEventToAllListeners(this.mQueuedMotionEvents.poll());
        }
    }

    private void setServiceDetectsGestures(boolean z) {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.setServiceDetectsGesturesEnabled(this.mDisplayId, z);
                this.mServiceDetectsGestures = z;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestTouchExploration() {
        validateTransitionRequest();
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestTouchExploration(this.mDisplayId);
                this.mStateChangeRequested = true;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestDragging(int i) {
        validateTransitionRequest();
        if (i < 0 || i > 32) {
            throw new IllegalArgumentException("Invalid pointer id: " + i);
        }
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestDragging(this.mDisplayId, i);
                this.mStateChangeRequested = true;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestDelegating() {
        validateTransitionRequest();
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestDelegating(this.mDisplayId);
                this.mStateChangeRequested = true;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void performClick() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.onDoubleTap(this.mDisplayId);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void performLongClickAndStartDrag() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.onDoubleTapAndHold(this.mDisplayId);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validateTransitionRequest() {
        if (!this.mServiceDetectsGestures || this.mCallbacks.size() == 0) {
            throw new IllegalStateException("State transitions are not allowed without first adding a callback.");
        }
        int i = this.mState;
        if (i == 4 || i == 2) {
            throw new IllegalStateException("State transition requests are not allowed in " + stateToString(this.mState));
        }
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "STATE_CLEAR";
        }
        if (i == 1) {
            return "STATE_TOUCH_INTERACTING";
        }
        if (i == 2) {
            return "STATE_TOUCH_EXPLORING";
        }
        if (i == 3) {
            return "STATE_DRAGGING";
        }
        if (i == 4) {
            return "STATE_DELEGATING";
        }
        return "Unknown state: " + i;
    }
}
