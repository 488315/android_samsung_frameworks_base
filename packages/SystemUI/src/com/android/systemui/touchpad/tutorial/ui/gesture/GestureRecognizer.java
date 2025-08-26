package com.android.systemui.touchpad.tutorial.ui.gesture;

import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public interface GestureRecognizer extends Consumer {
    void addGestureStateCallback(GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0 gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0);

    void clearGestureStateCallback();
}
