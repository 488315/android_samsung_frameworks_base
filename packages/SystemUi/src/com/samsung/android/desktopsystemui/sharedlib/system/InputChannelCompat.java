package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.Matrix;
import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputChannelCompat {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface InputEventListener {
        void onInputEvent(InputEvent inputEvent);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class InputEventReceiver {
        private final BatchedInputEventReceiver mReceiver;

        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer, final InputEventListener inputEventListener) {
            this.mReceiver = new BatchedInputEventReceiver(inputChannel, looper, choreographer) { // from class: com.samsung.android.desktopsystemui.sharedlib.system.InputChannelCompat.InputEventReceiver.1
                public void onInputEvent(InputEvent inputEvent) {
                    inputEventListener.onInputEvent(inputEvent);
                    finishInputEvent(inputEvent, true);
                }
            };
        }

        public void dispose() {
            this.mReceiver.dispose();
        }

        public void setBatchingEnabled(boolean z) {
            this.mReceiver.setBatchingEnabled(z);
        }
    }

    public static Matrix createRotationMatrix(int i, int i2, int i3) {
        return MotionEvent.createRotateMatrix(i, i2, i3);
    }

    public static boolean mergeMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2) {
        return motionEvent2.addBatch(motionEvent);
    }
}
