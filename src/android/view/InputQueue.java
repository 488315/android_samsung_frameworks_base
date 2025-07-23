package android.view;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.LongSparseArray;
import android.util.Pools;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public final class InputQueue {
    private final LongSparseArray<ActiveInputEvent> mActiveEventArray = new LongSparseArray<>(20);
    private final Pools.Pool<ActiveInputEvent> mActiveInputEventPool = new Pools.SimplePool(20);
    private final CloseGuard mCloseGuard;
    private long mPtr;

    public interface Callback {
        void onInputQueueCreated(InputQueue inputQueue);

        void onInputQueueDestroyed(InputQueue inputQueue);
    }

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(Object obj, boolean z);
    }

    private static native void nativeDispose(long j);

    private static native long nativeInit(WeakReference<InputQueue> weakReference, MessageQueue messageQueue);

    private static native long nativeSendKeyEvent(long j, KeyEvent keyEvent, boolean z);

    private static native long nativeSendMotionEvent(long j, MotionEvent motionEvent);

    public InputQueue() {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPtr = nativeInit(new WeakReference(this), Looper.myQueue());
        closeGuard.open("InputQueue.dispose");
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public void dispose() {
        dispose(false);
    }

    public void dispose(boolean z) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (z) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        long j = this.mPtr;
        if (j != 0) {
            nativeDispose(j);
            this.mPtr = 0L;
        }
    }

    public long getNativePtr() {
        return this.mPtr;
    }

    public void sendInputEvent(InputEvent inputEvent, Object obj, boolean z, FinishedInputEventCallback finishedInputEventCallback) {
        long nativeSendMotionEvent;
        ActiveInputEvent obtainActiveInputEvent = obtainActiveInputEvent(obj, finishedInputEventCallback);
        if (inputEvent instanceof KeyEvent) {
            nativeSendMotionEvent = nativeSendKeyEvent(this.mPtr, (KeyEvent) inputEvent, z);
        } else {
            nativeSendMotionEvent = nativeSendMotionEvent(this.mPtr, (MotionEvent) inputEvent);
        }
        this.mActiveEventArray.put(nativeSendMotionEvent, obtainActiveInputEvent);
    }

    private void finishInputEvent(long j, boolean z) {
        int indexOfKey = this.mActiveEventArray.indexOfKey(j);
        if (indexOfKey >= 0) {
            ActiveInputEvent valueAt = this.mActiveEventArray.valueAt(indexOfKey);
            this.mActiveEventArray.removeAt(indexOfKey);
            valueAt.mCallback.onFinishedInputEvent(valueAt.mToken, z);
            recycleActiveInputEvent(valueAt);
        }
    }

    private ActiveInputEvent obtainActiveInputEvent(Object obj, FinishedInputEventCallback finishedInputEventCallback) {
        ActiveInputEvent acquire = this.mActiveInputEventPool.acquire();
        if (acquire == null) {
            acquire = new ActiveInputEvent();
        }
        acquire.mToken = obj;
        acquire.mCallback = finishedInputEventCallback;
        return acquire;
    }

    private void recycleActiveInputEvent(ActiveInputEvent activeInputEvent) {
        activeInputEvent.recycle();
        this.mActiveInputEventPool.release(activeInputEvent);
    }

    private final class ActiveInputEvent {
        public FinishedInputEventCallback mCallback;
        public Object mToken;

        private ActiveInputEvent(InputQueue inputQueue) {
        }

        public void recycle() {
            this.mToken = null;
            this.mCallback = null;
        }
    }
}
