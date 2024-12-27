package com.android.systemui.touchpad.tutorial.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class TouchpadTutorialViewModel extends ViewModel {
    public final StateFlowImpl _screen;
    public final StateFlowImpl screen;

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
