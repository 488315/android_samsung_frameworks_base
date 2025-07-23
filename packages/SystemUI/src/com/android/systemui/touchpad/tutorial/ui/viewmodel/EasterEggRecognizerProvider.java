package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.touchpad.tutorial.ui.gesture.EasterEggGestureRecognizer;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EasterEggRecognizerProvider implements GestureRecognizerProvider {
    public final StateFlowImpl recognizer = StateFlowKt.MutableStateFlow(new EasterEggGestureRecognizer());

    @Override // com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerProvider
    public final Flow getRecognizer() {
        return this.recognizer;
    }
}
