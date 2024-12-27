package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class KeyguardLongPressViewModel {
    public final KeyguardLongPressInteractor interactor;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;

    public KeyguardLongPressViewModel(KeyguardLongPressInteractor keyguardLongPressInteractor) {
        this.interactor = keyguardLongPressInteractor;
        ReadonlyStateFlow readonlyStateFlow = keyguardLongPressInteractor.isLongPressHandlingEnabled;
    }
}
