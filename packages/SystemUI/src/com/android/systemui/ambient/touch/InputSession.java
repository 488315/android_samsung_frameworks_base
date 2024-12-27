package com.android.systemui.ambient.touch;

import android.os.Looper;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.Flags;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;

public final class InputSession {
    public final GestureDetector mGestureDetector;
    public final InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public final InputMonitorCompat mInputMonitor;
    public boolean mPilfering;

    public InputSession(InputMonitorCompat inputMonitorCompat, GestureDetector gestureDetector, final InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, Choreographer choreographer, Looper looper, final boolean z) {
        this.mInputMonitor = inputMonitorCompat;
        this.mGestureDetector = gestureDetector;
        this.mInputEventReceiver = inputMonitorCompat.getInputReceiver(looper, choreographer, new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.InputSession$$ExternalSyntheticLambda0
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                InputSession inputSession = InputSession.this;
                inputSession.getClass();
                inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
                if ((inputEvent instanceof MotionEvent) && inputSession.mGestureDetector.onTouchEvent((MotionEvent) inputEvent) && z) {
                    if (inputSession.mPilfering) {
                        Flags.FEATURE_FLAGS.getClass();
                    }
                    inputSession.mPilfering = true;
                    inputSession.mInputMonitor.mInputMonitor.pilferPointers();
                }
            }
        });
    }
}
