package com.android.keyguard.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardKeyboardInteractor {
    public final Flow isAnyKeyboardConnected;

    public KeyguardKeyboardInteractor(KeyboardRepository keyboardRepository) {
        this.isAnyKeyboardConnected = ((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected;
    }
}
