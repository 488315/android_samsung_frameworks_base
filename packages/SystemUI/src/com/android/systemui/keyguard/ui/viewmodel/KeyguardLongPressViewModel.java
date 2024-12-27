package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardLongPressViewModel {
    public final KeyguardLongPressInteractor interactor;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;

    public KeyguardLongPressViewModel(KeyguardLongPressInteractor keyguardLongPressInteractor) {
        this.interactor = keyguardLongPressInteractor;
        ReadonlyStateFlow readonlyStateFlow = keyguardLongPressInteractor.isLongPressHandlingEnabled;
    }
}
