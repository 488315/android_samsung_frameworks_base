package com.android.systemui.keyevent.domain.interactor;

import com.android.systemui.keyevent.data.repository.KeyEventRepository;
import com.android.systemui.keyevent.data.repository.KeyEventRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class KeyEventInteractor {
    public final Flow isPowerButtonDown;

    public KeyEventInteractor(KeyEventRepository keyEventRepository) {
        this.isPowerButtonDown = ((KeyEventRepositoryImpl) keyEventRepository).isPowerButtonDown;
    }
}
