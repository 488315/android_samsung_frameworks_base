package com.android.systemui.keyboard.docking.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class KeyboardDockingIndicationInteractor {
    public final Flow onKeyboardConnected;

    public KeyboardDockingIndicationInteractor(KeyboardRepository keyboardRepository) {
        this.onKeyboardConnected = ((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected;
    }
}
