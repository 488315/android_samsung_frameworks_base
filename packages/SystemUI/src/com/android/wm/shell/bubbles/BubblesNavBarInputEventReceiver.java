package com.android.wm.shell.bubbles;

import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.MotionEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubblesNavBarInputEventReceiver extends BatchedInputEventReceiver {
    public final BubblesNavBarMotionEventHandler mMotionEventHandler;

    public BubblesNavBarInputEventReceiver(InputChannel inputChannel, Choreographer choreographer, BubblesNavBarMotionEventHandler bubblesNavBarMotionEventHandler) {
        super(inputChannel, Looper.myLooper(), choreographer);
        this.mMotionEventHandler = bubblesNavBarMotionEventHandler;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        try {
            if (inputEvent instanceof MotionEvent) {
                finishInputEvent(inputEvent, this.mMotionEventHandler.onMotionEvent((MotionEvent) inputEvent));
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
