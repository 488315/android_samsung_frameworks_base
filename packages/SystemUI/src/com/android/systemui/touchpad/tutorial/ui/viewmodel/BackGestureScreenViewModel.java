package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BackGestureScreenViewModel {
    public final GestureRecognizerAdapter gestureRecognizer;
    public final SafeFlow tutorialState;

    public BackGestureScreenViewModel(GestureRecognizerAdapter gestureRecognizerAdapter) {
        this.gestureRecognizer = gestureRecognizerAdapter;
        this.tutorialState = new SafeFlow(new TouchpadTutorialScreenViewModelKt$mapToTutorialState$1(FlowKt.pairwiseBy(gestureRecognizerAdapter.gestureState, GestureState.NotStarted.INSTANCE, new BackGestureScreenViewModel$tutorialState$1(this, null)), null));
    }
}
