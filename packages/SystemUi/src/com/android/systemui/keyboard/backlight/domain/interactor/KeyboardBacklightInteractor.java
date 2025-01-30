package com.android.systemui.keyboard.backlight.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyboardBacklightInteractor {
    public final ChannelFlowTransformLatest backlight;
    public final KeyboardRepository keyboardRepository;

    public KeyboardBacklightInteractor(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.backlight = FlowKt.transformLatest(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, new KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
