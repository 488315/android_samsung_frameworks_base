package com.android.wm.shell.bubbles;

import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblesNavBarInputEventReceiver extends BatchedInputEventReceiver {
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
