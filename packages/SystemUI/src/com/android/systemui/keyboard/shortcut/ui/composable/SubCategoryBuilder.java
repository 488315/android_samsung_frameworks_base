package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.ArrayList;
import java.util.List;

public final class SubCategoryBuilder {
    public final String label;
    public final List shortcuts = new ArrayList();

    public SubCategoryBuilder(String str) {
        this.label = str;
    }
}
