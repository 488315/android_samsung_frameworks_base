package com.android.systemui.touchpad.tutorial.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public final class GestureViewModelFactory implements ViewModelProvider.Factory {
    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        if (cls.equals(BackGestureTutorialViewModel.class)) {
            return new BackGestureTutorialViewModel();
        }
        if (cls.equals(HomeGestureTutorialViewModel.class)) {
            return new HomeGestureTutorialViewModel();
        }
        throw new IllegalStateException("Unknown ViewModel class: ".concat(cls.getName()).toString());
    }
}
