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

/* loaded from: classes4.dex */
public abstract class InputEventSender {
    private static final String TAG = "InputEventSender";
    private final CloseGuard mCloseGuard;
    private Handler mHandler;
    private InputChannel mInputChannel;
    private long mSenderPtr;

    private static native void nativeDispose(long j);

    private static native long nativeInit(WeakReference<InputEventSender> weakReference, InputChannel inputChannel, MessageQueue messageQueue);

    private static native boolean nativeSendKeyEvent(long j, int i, KeyEvent keyEvent);

    private static native boolean nativeSendMotionEvent(long j, int i, MotionEvent motionEvent);

    public void onInputEventFinished(int i, boolean z) {
    }

    public void onTimelineReported(int i, long j, long j2) {
    }

    public InputEventSender(InputChannel inputChannel, Looper looper) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        if (inputChannel == null) {
            throw new IllegalArgumentException("inputChannel must not be null");
        }
        if (looper == null) {
            throw new IllegalArgumentException("looper must not be null");
        }
        this.mInputChannel = inputChannel;
        this.mHandler = new Handler(looper);
        this.mSenderPtr = nativeInit(new WeakReference(this), this.mInputChannel, looper.getQueue());
        closeGuard.open("InputEventSender.dispose");
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

    private void dispose(boolean z) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (z) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        long j = this.mSenderPtr;
        if (j != 0) {
            nativeDispose(j);
            this.mSenderPtr = 0L;
        }
        this.mHandler = null;
        this.mInputChannel = null;
    }

    public final boolean sendInputEvent(final int i, final InputEvent inputEvent) {
        if (inputEvent == null) {
            throw new IllegalArgumentException("event must not be null");
        }
        if (this.mSenderPtr == 0) {
            Log.w(TAG, "Attempted to send an input event but the input event sender has already been disposed.");
            return false;
        }
        if (this.mHandler.getLooper().isCurrentThread()) {
            return sendInputEventInternal(i, inputEvent);
        }
        FutureTask futureTask = new FutureTask(new Callable<Boolean>() { // from class: android.view.InputEventSender.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                return Boolean.valueOf(InputEventSender.this.sendInputEventInternal(i, inputEvent));
            }
        });
        this.mHandler.post(futureTask);
        try {
            return ((Boolean) futureTask.get()).booleanValue();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted while sending " + inputEvent + ": " + e);
        } catch (ExecutionException e2) {
            throw new IllegalStateException("Couldn't send " + inputEvent + ": " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendInputEventInternal(int i, InputEvent inputEvent) {
        if (inputEvent instanceof KeyEvent) {
            return nativeSendKeyEvent(this.mSenderPtr, i, (KeyEvent) inputEvent);
        }
        return nativeSendMotionEvent(this.mSenderPtr, i, (MotionEvent) inputEvent);
    }

    private void dispatchInputEventFinished(int i, boolean z) {
        onInputEventFinished(i, z);
    }

    private void dispatchTimelineReported(int i, long j, long j2) {
        onTimelineReported(i, j, j2);
    }
}
