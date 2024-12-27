package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.ArrayList;
import java.util.List;

public final class ShortcutBuilder {
    public final List commands = new ArrayList();
    public final String label;

    public ShortcutBuilder(String str) {
        this.label = str;
    }
}
