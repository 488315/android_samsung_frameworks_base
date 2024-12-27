package android.view;

import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import dalvik.system.CloseGuard;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class InputEventReceiver {
    private static final String TAG = "InputEventReceiver";
    private static final String TAG_DOT = "InputEventReceiver_DOT";
    private Choreographer mChoreographer;
    private InputChannel mInputChannel;
    private MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final SparseIntArray mSeqMap = new SparseIntArray();

    private static native boolean nativeConsumeBatchedInputEvents(long j, long j2);

    private static native void nativeDispose(long j);

    private static native String nativeDump(long j, String str);

    private static native void nativeFinishInputEvent(long j, int i, boolean z);

    private static native long nativeInit(WeakReference<InputEventReceiver> weakReference, InputChannel inputChannel, MessageQueue messageQueue);

    private static native boolean nativeProbablyHasInput(long j);

    private static native void nativeReportTimeline(long j, int i, long j2, long j3);

    private static native void nativeSetImprovementEvent(long j, boolean z, float f, float f2);

    public InputEventReceiver(InputChannel inputChannel, Looper looper) {
        synchronized (this) {
            if (inputChannel == null) {
                throw new IllegalArgumentException("inputChannel must not be null");
            }
            if (looper == null) {
                throw new IllegalArgumentException("looper must not be null");
            }
            this.mInputChannel = inputChannel;
            this.mMessageQueue = looper.getQueue();
            this.mReceiverPtr = nativeInit(new WeakReference(this), this.mInputChannel, this.mMessageQueue);
            this.mCloseGuard.open("InputEventReceiver.dispose");
        }
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public boolean probablyHasInput() {
        synchronized (this) {
            if (this.mReceiverPtr == 0) {
                return false;
            }
            return nativeProbablyHasInput(this.mReceiverPtr);
        }
    }

    public void dispose() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        synchronized (this) {
            if (this.mCloseGuard != null) {
                if (finalized) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            if (this.mReceiverPtr != 0) {
                nativeDispose(this.mReceiverPtr);
                this.mReceiverPtr = 0L;
            }
            if (this.mInputChannel != null) {
                this.mInputChannel.dispose();
                this.mInputChannel = null;
            }
            this.mMessageQueue = null;
            Reference.reachabilityFence(this);
        }
    }

    public void onInputEvent(InputEvent event) {
        finishInputEvent(event, false);
    }

    public void onFocusEvent(boolean hasFocus) {
    }

    public void setImprovementEvent(boolean enhancedPerformance, float xdpi, float ydpi) {
        nativeSetImprovementEvent(this.mReceiverPtr, enhancedPerformance, xdpi, ydpi);
    }

    public void onPointerCaptureEvent(boolean pointerCaptureEnabled) {
    }

    public void onDragEvent(boolean isExiting, float x, float y) {
    }

    public void onTouchModeChanged(boolean inTouchMode) {
    }

    public void onBatchedInputEventPending(int source) {
        consumeBatchedInputEvents(-1L);
    }

    public final void finishInputEvent(InputEvent event, boolean handled) {
        synchronized (this) {
            try {
                if (event == null) {
                    throw new IllegalArgumentException("event must not be null");
                }
                if (this.mReceiverPtr == 0) {
                    Log.w(TAG, "Attempted to finish an input event but the input event receiver has already been disposed.");
                } else {
                    int index = this.mSeqMap.indexOfKey(event.getSequenceNumber());
                    if (index < 0) {
                        Log.w(TAG, "Attempted to finish an input event that is not in progress.");
                    } else {
                        int seq = this.mSeqMap.valueAt(index);
                        this.mSeqMap.removeAt(index);
                        nativeFinishInputEvent(this.mReceiverPtr, seq, handled);
                    }
                }
                event.recycleIfNeededAfterDispatch();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportTimeline(int inputEventId, long gpuCompletedTime, long presentTime) {
        synchronized (this) {
            Trace.traceBegin(4L, "reportTimeline");
            nativeReportTimeline(this.mReceiverPtr, inputEventId, gpuCompletedTime, presentTime);
            Trace.traceEnd(4L);
        }
    }

    public final boolean consumeBatchedInputEvents(long frameTimeNanos) {
        synchronized (this) {
            if (this.mReceiverPtr == 0) {
                Log.w(TAG, "Attempted to consume batched input events but the input event receiver has already been disposed.");
                return false;
            }
            return nativeConsumeBatchedInputEvents(this.mReceiverPtr, frameTimeNanos);
        }
    }

    private float getSlopInPixels() {
        DisplayMetrics slopMetrics;
        if (this.mChoreographer == null) {
            this.mChoreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
        }
        if (this.mChoreographer != null && (slopMetrics = this.mChoreographer.getMetrics()) != null) {
            return TypedValue.applyDimension(1, 8.0f, slopMetrics);
        }
        return -1.0f;
    }

    private void scheduleInputVsync() {
        try {
            Log.d(TAG_DOT, "IER.scheduleInputVsync");
            if (this.mChoreographer == null) {
                this.mChoreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
            }
            if (this.mChoreographer != null) {
                Choreographer choreographer = this.mChoreographer;
                Objects.requireNonNull(this.mChoreographer);
                choreographer.scheduleVsyncSS(1);
            }
        } catch (Exception e) {
            Log.e(TAG_DOT, "Error IER.scheduleInputVsync.", e);
        }
    }

    public IBinder getToken() {
        if (this.mInputChannel == null) {
            return null;
        }
        return this.mInputChannel.getToken();
    }

    private String getShortDescription(InputEvent event) {
        if (event instanceof MotionEvent) {
            MotionEvent motion = (MotionEvent) event;
            return "MotionEvent " + MotionEvent.actionToString(motion.getAction()) + " deviceId=" + motion.getDeviceId() + " source=0x" + Integer.toHexString(motion.getSource()) + " historySize=" + motion.getHistorySize();
        }
        if (event instanceof KeyEvent) {
            KeyEvent key = (KeyEvent) event;
            return "KeyEvent " + KeyEvent.actionToString(key.getAction()) + " deviceId=" + key.getDeviceId();
        }
        Log.wtf(TAG, "Illegal InputEvent type: " + event);
        return "InputEvent";
    }

    private void dispatchInputEvent(int seq, InputEvent event) {
        Trace.traceBegin(4L, "dispatchInputEvent " + getShortDescription(event));
        this.mSeqMap.put(event.getSequenceNumber(), seq);
        onInputEvent(event);
        Trace.traceEnd(4L);
    }

    public void dump(String prefix, PrintWriter writer) {
        writer.println(prefix + getClass().getName());
        writer.println(prefix + " mInputChannel: " + this.mInputChannel);
        writer.println(prefix + " mSeqMap: " + this.mSeqMap);
        writer.println(prefix + " mReceiverPtr:\n" + nativeDump(this.mReceiverPtr, prefix + "  "));
    }
}
