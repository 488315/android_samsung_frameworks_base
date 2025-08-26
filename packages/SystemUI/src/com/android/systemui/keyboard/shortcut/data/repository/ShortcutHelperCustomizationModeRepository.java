package com.android.systemui.keyboard.shortcut.data.repository;

import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class ShortcutHelperCustomizationModeRepository {
    public final StateFlowImpl _isCustomizationModeEnabled;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isCustomizationModeEnabled;

    public ShortcutHelperCustomizationModeRepository(ShortcutHelperStateRepository shortcutHelperStateRepository) {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isCustomizationModeEnabled = stateFlowImplMutableStateFlow;
        this.isCustomizationModeEnabled = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, shortcutHelperStateRepository.state, new ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1(null));
    }
}
