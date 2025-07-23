package com.android.systemui.keyboard.stickykeys.ui.viewmodel;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepository;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StickyKeysIndicatorViewModel {
    public final ReadonlyStateFlow indicatorContent;

    public StickyKeysIndicatorViewModel(StickyKeysRepository stickyKeysRepository, KeyboardRepository keyboardRepository, CoroutineScope coroutineScope) {
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(FlowKt.transformLatest(((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected, new StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$1(null, stickyKeysRepository)), new StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2(null, stickyKeysRepository));
        SharingStarted.Companion.getClass();
        this.indicatorContent = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.Lazily, MapsKt__MapsKt.emptyMap());
    }
}
