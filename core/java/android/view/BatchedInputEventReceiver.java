package android.view;

import android.os.Handler;
import android.os.Looper;
import android.os.Trace;

public class BatchedInputEventReceiver extends InputEventReceiver {
    private final BatchedInputRunnable mBatchedInputRunnable;
    private boolean mBatchedInputScheduled;
    private boolean mBatchingEnabled;
    private Choreographer mChoreographer;
    private final Runnable mConsumeBatchedInputEvents;
    private final Handler mHandler;
    private final String mTag;

    public BatchedInputEventReceiver(
            InputChannel inputChannel, Looper looper, Choreographer choreographer) {
        super(inputChannel, looper);
        this.mConsumeBatchedInputEvents =
                new Runnable() { // from class: android.view.BatchedInputEventReceiver.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BatchedInputEventReceiver.this.consumeBatchedInputEvents(-1L);
                    }
                };
        this.mBatchedInputRunnable = new BatchedInputRunnable();
        this.mChoreographer = choreographer;
        this.mBatchingEnabled = true;
        this.mTag = inputChannel.getName();
        traceBoolVariable("mBatchingEnabled", this.mBatchingEnabled);
        traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
        this.mHandler = new Handler(looper);
    }

    @Override // android.view.InputEventReceiver
    public void onBatchedInputEventPending(int source) {
        if (this.mBatchingEnabled) {
            scheduleBatchedInput();
        } else {
            consumeBatchedInputEvents(-1L);
        }
    }

    @Override // android.view.InputEventReceiver
    public void dispose() {
        unscheduleBatchedInput();
        consumeBatchedInputEvents(-1L);
        super.dispose();
    }

    public void setBatchingEnabled(boolean batchingEnabled) {
        if (this.mBatchingEnabled == batchingEnabled) {
            return;
        }
        this.mBatchingEnabled = batchingEnabled;
        traceBoolVariable("mBatchingEnabled", this.mBatchingEnabled);
        this.mHandler.removeCallbacks(this.mConsumeBatchedInputEvents);
        if (!batchingEnabled) {
            unscheduleBatchedInput();
            this.mHandler.post(this.mConsumeBatchedInputEvents);
        }
    }

    protected void doConsumeBatchedInput(long frameTimeNanos) {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            if (consumeBatchedInputEvents(frameTimeNanos) && frameTimeNanos != -1) {
                scheduleBatchedInput();
            }
        }
    }

    private void scheduleBatchedInput() {
        if (!this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = true;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            this.mChoreographer.postCallback(0, this.mBatchedInputRunnable, null);
        }
    }

    private void unscheduleBatchedInput() {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            this.mChoreographer.removeCallbacks(0, this.mBatchedInputRunnable, null);
        }
    }

    private void traceBoolVariable(String str, boolean z) {
        Trace.traceCounter(4L, str, z ? 1 : 0);
    }

    private final class BatchedInputRunnable implements Runnable {
        private BatchedInputRunnable() {}

        @Override // java.lang.Runnable
        public void run() {
            try {
                Trace.traceBegin(4L, BatchedInputEventReceiver.this.mTag);
                BatchedInputEventReceiver.this.doConsumeBatchedInput(
                        BatchedInputEventReceiver.this.mChoreographer.getFrameTimeNanos());
            } finally {
                Trace.traceEnd(4L);
            }
        }
    }

    public static class SimpleBatchedInputEventReceiver extends BatchedInputEventReceiver {
        protected InputEventListener mListener;

        public interface InputEventListener {
            boolean onInputEvent(InputEvent inputEvent);
        }

        public SimpleBatchedInputEventReceiver(
                InputChannel inputChannel,
                Looper looper,
                Choreographer choreographer,
                InputEventListener listener) {
            super(inputChannel, looper, choreographer);
            this.mListener = listener;
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            boolean handled = false;
            try {
                handled = this.mListener.onInputEvent(event);
            } finally {
                finishInputEvent(event, handled);
            }
        }
    }
}
