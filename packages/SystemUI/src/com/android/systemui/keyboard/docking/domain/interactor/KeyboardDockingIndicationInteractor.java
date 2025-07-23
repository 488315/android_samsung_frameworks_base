package com.android.systemui.keyboard.docking.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardDockingIndicationInteractor {
    public final Flow onKeyboardConnected;

    public KeyboardDockingIndicationInteractor(KeyboardRepository keyboardRepository) {
        this.onKeyboardConnected = ((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected;
    }
}
