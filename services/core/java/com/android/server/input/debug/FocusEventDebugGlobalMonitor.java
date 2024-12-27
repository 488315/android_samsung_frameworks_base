package com.android.server.input.debug;

import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;

import com.android.server.UiThread;
import com.android.server.input.InputManagerService;
import com.android.server.location.gnss.hal.GnssNative;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
