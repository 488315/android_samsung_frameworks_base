package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class ShortcutCustomizationInteractor {
    public final CustomShortcutCategoriesRepository customShortcutRepository;
    public final ReadonlyStateFlow pressedKeys;

    public ShortcutCustomizationInteractor(CustomShortcutCategoriesRepository customShortcutCategoriesRepository) {
        this.customShortcutRepository = customShortcutCategoriesRepository;
        this.pressedKeys = customShortcutCategoriesRepository.pressedKeys;
    }
}
