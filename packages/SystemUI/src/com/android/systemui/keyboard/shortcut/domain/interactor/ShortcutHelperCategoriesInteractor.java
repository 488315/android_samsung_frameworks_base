package com.android.systemui.keyboard.shortcut.domain.interactor;

import android.content.Context;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutCategoriesRepository;
import dagger.Lazy;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class ShortcutHelperCategoriesInteractor {
    public final Context context;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shortcutCategories;

    public ShortcutHelperCategoriesInteractor(Context context, ShortcutCategoriesRepository shortcutCategoriesRepository, Lazy lazy) {
        this.context = context;
        this.shortcutCategories = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shortcutCategoriesRepository.getCategories(), ((ShortcutCategoriesRepository) lazy.get()).getCategories(), new ShortcutHelperCategoriesInteractor$shortcutCategories$1(this, null));
    }
}
