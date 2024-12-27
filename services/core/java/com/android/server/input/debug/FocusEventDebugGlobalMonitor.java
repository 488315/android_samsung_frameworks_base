package com.android.server.input.debug;

import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;

import com.android.server.UiThread;
import com.android.server.input.InputManagerService;
import com.android.server.location.gnss.hal.GnssNative;

public final class FocusEventDebugGlobalMonitor extends InputEventReceiver {
    public final FocusEventDebugView mDebugView;

    public FocusEventDebugGlobalMonitor(
            FocusEventDebugView focusEventDebugView, InputManagerService inputManagerService) {
        super(
                inputManagerService.monitorInput(
                        "FocusEventDebugGlobalMonitor", 0, GnssNative.GNSS_AIDING_TYPE_ALL),
                UiThread.getHandler().getLooper());
        this.mDebugView = focusEventDebugView;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        try {
            if (inputEvent instanceof MotionEvent) {
                FocusEventDebugView focusEventDebugView = this.mDebugView;
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                focusEventDebugView.getClass();
                if (motionEvent.getSource() == 4194304) {
                    focusEventDebugView.post(
                            new FocusEventDebugView$$ExternalSyntheticLambda1(
                                    focusEventDebugView, MotionEvent.obtain(motionEvent)));
                }
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
