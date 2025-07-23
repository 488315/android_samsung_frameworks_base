package com.android.systemui.inputdevice.tutorial.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import com.android.systemui.touchpad.data.repository.TouchpadRepository;
import com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardTouchpadConnectionInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 connectionState;

    public KeyboardTouchpadConnectionInteractor(KeyboardRepository keyboardRepository, TouchpadRepository touchpadRepository) {
        this.connectionState = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, ((TouchpadRepositoryImpl) touchpadRepository).isAnyTouchpadConnected, new KeyboardTouchpadConnectionInteractor$connectionState$1(null));
    }
}
