package android.view;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import dalvik.system.CloseGuard;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public abstract class InputEventSender {
    private static final String TAG = "InputEventSender";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private Handler mHandler;
    private InputChannel mInputChannel;
    private long mSenderPtr;

    private static native void nativeDispose(long j);

    private static native long nativeInit(
            WeakReference<InputEventSender> weakReference,
            InputChannel inputChannel,
            MessageQueue messageQueue);

    private static native boolean nativeSendKeyEvent(long j, int i, KeyEvent keyEvent);

    private static native boolean nativeSendMotionEvent(long j, int i, MotionEvent motionEvent);

    public InputEventSender(InputChannel inputChannel, Looper looper) {
        if (inputChannel == null) {
            throw new IllegalArgumentException("inputChannel must not be null");
        }
        if (looper == null) {
            throw new IllegalArgumentException("looper must not be null");
        }
        this.mInputChannel = inputChannel;
        this.mHandler = new Handler(looper);
        this.mSenderPtr =
                nativeInit(new WeakReference(this), this.mInputChannel, looper.getQueue());
        this.mCloseGuard.open("InputEventSender.dispose");
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

    private void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mSenderPtr != 0) {
            nativeDispose(this.mSenderPtr);
            this.mSenderPtr = 0L;
        }
        this.mHandler = null;
        this.mInputChannel = null;
    }

    public void onInputEventFinished(int seq, boolean handled) {}

    public void onTimelineReported(int inputEventId, long gpuCompletedTime, long presentTime) {}

    public final boolean sendInputEvent(final int seq, final InputEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event must not be null");
        }
        if (this.mSenderPtr == 0) {
            Log.w(
                    TAG,
                    "Attempted to send an input event but the input event sender has already been"
                        + " disposed.");
            return false;
        }
        if (this.mHandler.getLooper().isCurrentThread()) {
            return sendInputEventInternal(seq, event);
        }
        RunnableFuture<Boolean> task =
                new FutureTask<>(
                        new Callable<Boolean>() { // from class: android.view.InputEventSender.1
                            @Override // java.util.concurrent.Callable
                            public Boolean call() throws Exception {
                                return Boolean.valueOf(
                                        InputEventSender.this.sendInputEventInternal(seq, event));
                            }
                        });
        this.mHandler.post(task);
        try {
            return task.get().booleanValue();
        } catch (InterruptedException exc) {
            throw new IllegalStateException("Interrupted while sending " + event + ": " + exc);
        } catch (ExecutionException exc2) {
            throw new IllegalStateException("Couldn't send " + event + ": " + exc2);
        }
    }

    public boolean sendInputEventInternal(int seq, InputEvent event) {
        if (event instanceof KeyEvent) {
            return nativeSendKeyEvent(this.mSenderPtr, seq, (KeyEvent) event);
        }
        return nativeSendMotionEvent(this.mSenderPtr, seq, (MotionEvent) event);
    }

    private void dispatchInputEventFinished(int seq, boolean handled) {
        onInputEventFinished(seq, handled);
    }

    private void dispatchTimelineReported(
            int inputEventId, long gpuCompletedTime, long presentTime) {
        onTimelineReported(inputEventId, gpuCompletedTime, presentTime);
    }
}
