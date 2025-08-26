package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperCustomizationModeRepository;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class ShortcutHelperCustomizationModeInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 customizationMode;
    public final ShortcutHelperCustomizationModeRepository customizationModeRepository;

    public ShortcutHelperCustomizationModeInteractor(ShortcutHelperCustomizationModeRepository shortcutHelperCustomizationModeRepository) {
        this.customizationModeRepository = shortcutHelperCustomizationModeRepository;
        this.customizationMode = shortcutHelperCustomizationModeRepository.isCustomizationModeEnabled;
    }
}
