package com.android.systemui.dreams.touch;

import android.os.Looper;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InputSession {
    public final GestureDetector mGestureDetector;
    public final InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public final InputMonitorCompat mInputMonitor;

    public InputSession(String str, final InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, DisplayTracker displayTracker, final boolean z) {
        displayTracker.getClass();
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat(str, 0);
        this.mInputMonitor = inputMonitorCompat;
        this.mGestureDetector = new GestureDetector(onGestureListener);
        this.mInputEventReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorCompat.mInputMonitor.getInputChannel(), Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.InputSession$$ExternalSyntheticLambda0
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                InputSession inputSession = InputSession.this;
                inputSession.getClass();
                inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
                if ((inputEvent instanceof MotionEvent) && inputSession.mGestureDetector.onTouchEvent((MotionEvent) inputEvent) && z) {
                    inputSession.mInputMonitor.mInputMonitor.pilferPointers();
                }
            }
        });
    }
}
