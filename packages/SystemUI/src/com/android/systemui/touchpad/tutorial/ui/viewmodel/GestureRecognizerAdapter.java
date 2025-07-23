package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import android.view.MotionEvent;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer;
import java.util.function.Consumer;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GestureRecognizerAdapter implements Consumer {
    public GestureRecognizer gestureRecognizer;
    public final ChannelFlowTransformLatest gestureState;
    public final InputDeviceTutorialLogger logger;

    public GestureRecognizerAdapter(GestureRecognizerProvider gestureRecognizerProvider, InputDeviceTutorialLogger inputDeviceTutorialLogger) {
        this.logger = inputDeviceTutorialLogger;
        this.gestureState = FlowKt.transformLatest(gestureRecognizerProvider.getRecognizer(), new GestureRecognizerAdapter$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        GestureRecognizer gestureRecognizer = this.gestureRecognizer;
        if (gestureRecognizer != null) {
            gestureRecognizer.accept(motionEvent);
            return;
        }
        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.logger.$$delegate_0;
        constantStringsLoggerImpl.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.WARNING, "sending MotionEvent before gesture recognizer is initialized");
    }
}
