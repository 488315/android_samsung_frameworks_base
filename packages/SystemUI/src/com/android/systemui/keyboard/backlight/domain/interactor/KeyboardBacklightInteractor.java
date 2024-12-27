package com.android.systemui.keyboard.backlight.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class KeyboardBacklightInteractor {
    public final ChannelFlowTransformLatest backlight;
    public final KeyboardRepository keyboardRepository;

    public KeyboardBacklightInteractor(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.backlight = FlowKt.transformLatest(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, new KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
