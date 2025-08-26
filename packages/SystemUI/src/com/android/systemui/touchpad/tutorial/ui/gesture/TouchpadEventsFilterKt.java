package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter;

/* loaded from: classes3.dex */
public abstract class TouchpadEventsFilterKt {
    public static final boolean handleTouchpadMotionEvent(GestureRecognizerAdapter gestureRecognizerAdapter, MotionEvent motionEvent) {
        TouchpadEventsFilter.INSTANCE.getClass();
        boolean z = motionEvent.isFromSource(8194) && motionEvent.getToolType(0) == 1;
        boolean z2 = motionEvent.getActionMasked() == 0 && motionEvent.isButtonPressed(1);
        if (!z || z2) {
            return false;
        }
        GestureRecognizer gestureRecognizer = gestureRecognizerAdapter.gestureRecognizer;
        if (gestureRecognizer != null) {
            gestureRecognizer.accept(motionEvent);
            return true;
        }
        ConstantStringsLoggerImpl constantStringsLoggerImpl = gestureRecognizerAdapter.logger.$$delegate_0;
        constantStringsLoggerImpl.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.WARNING, "sending MotionEvent before gesture recognizer is initialized");
        return true;
    }
}
