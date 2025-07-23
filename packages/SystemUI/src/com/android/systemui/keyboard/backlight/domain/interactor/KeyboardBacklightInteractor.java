package com.android.systemui.keyboard.backlight.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardBacklightInteractor {
    public final ChannelFlowTransformLatest backlight;
    public final KeyboardRepository keyboardRepository;

    public KeyboardBacklightInteractor(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.backlight = FlowKt.transformLatest(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, new KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
