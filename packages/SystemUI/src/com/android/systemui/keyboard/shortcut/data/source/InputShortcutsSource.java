package com.android.systemui.keyboard.shortcut.data.source;

import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputShortcutsSource implements KeyboardShortcutGroupsSource {
    public final InputManager inputManager;
    public final Resources resources;
    public final WindowManager windowManager;

    public InputShortcutsSource(Resources resources, WindowManager windowManager, InputManager inputManager) {
        this.resources = resources;
        this.windowManager = windowManager;
        this.inputManager = inputManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object shortcutGroups(int r12, kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.source.InputShortcutsSource.shortcutGroups(int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
