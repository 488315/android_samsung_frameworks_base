package com.android.systemui.touchpad.tutorial.ui.gesture;

import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface GestureRecognizer extends Consumer {
    void addGestureStateCallback(GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0 gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0);

    void clearGestureStateCallback();
}
