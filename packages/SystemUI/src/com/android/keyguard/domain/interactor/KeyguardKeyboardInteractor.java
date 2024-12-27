package com.android.keyguard.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class KeyguardKeyboardInteractor {
    public final Flow isAnyKeyboardConnected;

    public KeyguardKeyboardInteractor(KeyboardRepository keyboardRepository) {
        this.isAnyKeyboardConnected = ((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected;
    }
}
