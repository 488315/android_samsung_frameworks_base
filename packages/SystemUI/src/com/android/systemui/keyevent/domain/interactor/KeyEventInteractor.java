package com.android.systemui.keyevent.domain.interactor;

import com.android.systemui.keyevent.data.repository.KeyEventRepository;
import com.android.systemui.keyevent.data.repository.KeyEventRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyEventInteractor {
    public final Flow isPowerButtonDown;
    public final Flow isPowerButtonLongPressed;

    public KeyEventInteractor(KeyEventRepository keyEventRepository) {
        this.isPowerButtonDown = ((KeyEventRepositoryImpl) keyEventRepository).isPowerButtonDown;
        this.isPowerButtonLongPressed = ((KeyEventRepositoryImpl) keyEventRepository).isPowerButtonLongPressed;
    }
}
