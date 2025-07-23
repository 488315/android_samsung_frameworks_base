package com.android.systemui.keyboard.shortcut.data.source;

import android.view.WindowManager;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AppCategoriesShortcutsSource implements KeyboardShortcutGroupsSource {
    public final CoroutineDispatcher backgroundDispatcher;
    public final WindowManager windowManager;

    public AppCategoriesShortcutsSource(WindowManager windowManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = windowManager;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AppCategoriesShortcutsSource$shortcutGroups$2(this, i, null), continuation);
    }
}
