package com.android.systemui.keyboard.shortcut.data.repository;

import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperCustomizationModeRepository {
    public final StateFlowImpl _isCustomizationModeEnabled;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isCustomizationModeEnabled;

    public ShortcutHelperCustomizationModeRepository(ShortcutHelperStateRepository shortcutHelperStateRepository) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isCustomizationModeEnabled = MutableStateFlow;
        this.isCustomizationModeEnabled = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow, shortcutHelperStateRepository.state, new ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1(null));
    }
}
