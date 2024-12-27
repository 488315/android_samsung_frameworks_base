package com.android.systemui.touchpad.tutorial.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TouchpadTutorialViewModel extends ViewModel {
    public final StateFlowImpl _screen;
    public final StateFlowImpl screen;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory implements ViewModelProvider.Factory {
        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            return new TouchpadTutorialViewModel();
        }
    }

    public TouchpadTutorialViewModel() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Screen.TUTORIAL_SELECTION);
        this._screen = MutableStateFlow;
        this.screen = MutableStateFlow;
    }
}
