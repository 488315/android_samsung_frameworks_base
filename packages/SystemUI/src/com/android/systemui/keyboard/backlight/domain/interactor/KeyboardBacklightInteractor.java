package com.android.systemui.keyboard.backlight.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyboardBacklightInteractor {
    public final ChannelFlowTransformLatest backlight;
    public final KeyboardRepository keyboardRepository;

    public KeyboardBacklightInteractor(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.backlight = FlowKt.transformLatest(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, new KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
