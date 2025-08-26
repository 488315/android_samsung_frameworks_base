package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public final class BackGestureScreenViewModel {
    public final GestureRecognizerAdapter gestureRecognizer;
    public final SafeFlow tutorialState;

    public BackGestureScreenViewModel(GestureRecognizerAdapter gestureRecognizerAdapter) {
        this.gestureRecognizer = gestureRecognizerAdapter;
        this.tutorialState = new SafeFlow(new TouchpadTutorialScreenViewModelKt$mapToTutorialState$1(FlowKt.pairwiseBy(gestureRecognizerAdapter.gestureState, GestureState.NotStarted.INSTANCE, new BackGestureScreenViewModel$tutorialState$1(this, null)), null));
    }
}
