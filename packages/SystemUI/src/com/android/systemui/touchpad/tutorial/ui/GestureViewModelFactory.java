package com.android.systemui.touchpad.tutorial.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
