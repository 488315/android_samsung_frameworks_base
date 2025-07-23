package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutCustomizationInteractor {
    public final CustomShortcutCategoriesRepository customShortcutRepository;
    public final ReadonlyStateFlow pressedKeys;

    public ShortcutCustomizationInteractor(CustomShortcutCategoriesRepository customShortcutCategoriesRepository) {
        this.customShortcutRepository = customShortcutCategoriesRepository;
        this.pressedKeys = customShortcutCategoriesRepository.pressedKeys;
    }
}
