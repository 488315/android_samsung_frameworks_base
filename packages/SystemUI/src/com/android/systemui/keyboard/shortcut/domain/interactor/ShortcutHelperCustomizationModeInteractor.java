package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperCustomizationModeRepository;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperCustomizationModeInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 customizationMode;
    public final ShortcutHelperCustomizationModeRepository customizationModeRepository;

    public ShortcutHelperCustomizationModeInteractor(ShortcutHelperCustomizationModeRepository shortcutHelperCustomizationModeRepository) {
        this.customizationModeRepository = shortcutHelperCustomizationModeRepository;
        this.customizationMode = shortcutHelperCustomizationModeRepository.isCustomizationModeEnabled;
    }
}
