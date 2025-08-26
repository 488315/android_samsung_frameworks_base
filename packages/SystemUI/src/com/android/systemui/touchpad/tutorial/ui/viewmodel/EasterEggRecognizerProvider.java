package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.touchpad.tutorial.ui.gesture.EasterEggGestureRecognizer;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class EasterEggRecognizerProvider implements GestureRecognizerProvider {
    public final StateFlowImpl recognizer = StateFlowKt.MutableStateFlow(new EasterEggGestureRecognizer());

    @Override // com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerProvider
    public final Flow getRecognizer() {
        return this.recognizer;
    }
}
