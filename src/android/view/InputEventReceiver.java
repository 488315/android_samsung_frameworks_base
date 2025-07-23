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
    private final CloseGuard mCloseGuard;
    private InputChannel mInputChannel;
    private MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final SparseIntArray mSeqMap;

    private static native boolean nativeConsumeBatchedInputEvents(long j, long j2);

    private static native void nativeDispose(long j);

    private static native String nativeDump(long j, String str);

    private static native void nativeFinishInputEvent(long j, int i, boolean z);

    private static native long nativeInit(WeakReference<InputEventReceiver> weakReference, InputChannel inputChannel, MessageQueue messageQueue);

    private static native boolean nativeProbablyHasInput(long j);

    private static native void nativeReportTimeline(long j, int i, long j2, long j3);

    private static native void nativeSetImprovementEvent(long j, boolean z, float f, float f2);

    public void onDragEvent(boolean z, float f, float f2, int i) {
    }

    public void onFocusEvent(boolean z) {
    }

    public void onPointerCaptureEvent(boolean z) {
    }

    public void onTouchModeChanged(boolean z) {
    }

    public InputEventReceiver(InputChannel inputChannel, Looper looper) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mSeqMap = new SparseIntArray();
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
            closeGuard.open("InputEventReceiver.dispose");
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
            long j = this.mReceiverPtr;
            if (j == 0) {
                return false;
            }
            return nativeProbablyHasInput(j);
        }
    }

    public void dispose() {
        dispose(false);
    }

    private void dispose(boolean z) {
        synchronized (this) {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                if (z) {
                    closeGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            long j = this.mReceiverPtr;
            if (j != 0) {
                nativeDispose(j);
                this.mReceiverPtr = 0L;
            }
            InputChannel inputChannel = this.mInputChannel;
            if (inputChannel != null) {
                inputChannel.dispose();
                this.mInputChannel = null;
            }
            this.mMessageQueue = null;
            Reference.reachabilityFence(this);
        }
    }

    public void onInputEvent(InputEvent inputEvent) {
        finishInputEvent(inputEvent, false);
    }

    public void setImprovementEvent(boolean z, float f, float f2) {
        synchronized (this) {
            nativeSetImprovementEvent(this.mReceiverPtr, z, f, f2);
        }
    }

    public void onBatchedInputEventPending(int i) {
        consumeBatchedInputEvents(-1L);
    }

    public final void finishInputEvent(InputEvent inputEvent, boolean z) {
        synchronized (this) {
            try {
                if (inputEvent == null) {
                    throw new IllegalArgumentException("event must not be null");
                }
                if (this.mReceiverPtr == 0) {
                    Log.w(TAG, "Attempted to finish an input event but the input event receiver has already been disposed.");
                } else {
                    int indexOfKey = this.mSeqMap.indexOfKey(inputEvent.getSequenceNumber());
                    if (indexOfKey < 0) {
                        Log.w(TAG, "Attempted to finish an input event that is not in progress.");
                    } else {
                        int valueAt = this.mSeqMap.valueAt(indexOfKey);
                        this.mSeqMap.removeAt(indexOfKey);
                        nativeFinishInputEvent(this.mReceiverPtr, valueAt, z);
                    }
                }
                inputEvent.recycleIfNeededAfterDispatch();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportTimeline(int i, long j, long j2) {
        synchronized (this) {
            Trace.traceBegin(4L, "reportTimeline");
            nativeReportTimeline(this.mReceiverPtr, i, j, j2);
            Trace.traceEnd(4L);
        }
    }

    public final boolean consumeBatchedInputEvents(long j) {
        synchronized (this) {
            long j2 = this.mReceiverPtr;
            if (j2 == 0) {
                Log.w(TAG, "Attempted to consume batched input events but the input event receiver has already been disposed.");
                return false;
            }
            return nativeConsumeBatchedInputEvents(j2, j);
        }
    }

    private float getSlopInPixels() {
        DisplayMetrics metrics;
        if (this.mChoreographer == null) {
            this.mChoreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
        }
        Choreographer choreographer = this.mChoreographer;
        if (choreographer == null || (metrics = choreographer.getMetrics()) == null) {
            return -1.0f;
        }
        return TypedValue.applyDimension(1, 8.0f, metrics);
    }

    private void scheduleInputVsync() {
        try {
            Log.d(TAG_DOT, "IER.scheduleInputVsync");
            if (this.mChoreographer == null) {
                this.mChoreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
            }
            Choreographer choreographer = this.mChoreographer;
            if (choreographer != null) {
                Objects.requireNonNull(choreographer);
                choreographer.scheduleVsyncSS(1);
            }
        } catch (Exception e) {
            Log.e(TAG_DOT, "Error IER.scheduleInputVsync.", e);
        }
    }

    public IBinder getToken() {
        InputChannel inputChannel = this.mInputChannel;
        if (inputChannel == null) {
            return null;
        }
        return inputChannel.getToken();
    }

    private String getShortDescription(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            return "MotionEvent " + MotionEvent.actionToString(motionEvent.getAction()) + " deviceId=" + motionEvent.getDeviceId() + " source=0x" + Integer.toHexString(motionEvent.getSource()) + " historySize=" + motionEvent.getHistorySize();
        }
        if (inputEvent instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) inputEvent;
            return "KeyEvent " + KeyEvent.actionToString(keyEvent.getAction()) + " deviceId=" + keyEvent.getDeviceId();
        }
        Log.wtf(TAG, "Illegal InputEvent type: " + inputEvent);
        return "InputEvent";
    }

    private void dispatchInputEvent(int i, InputEvent inputEvent) {
        if (Trace.isTagEnabled(4L)) {
            Trace.traceBegin(4L, "dispatchInputEvent " + getShortDescription(inputEvent));
        }
        this.mSeqMap.put(inputEvent.getSequenceNumber(), i);
        onInputEvent(inputEvent);
        Trace.traceEnd(4L);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + getClass().getName());
        printWriter.println(str + " mInputChannel: " + this.mInputChannel);
        printWriter.println(str + " mSeqMap: " + this.mSeqMap);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" mReceiverPtr:\n");
        sb.append(nativeDump(this.mReceiverPtr, str + "  "));
        printWriter.println(sb.toString());
    }
}
