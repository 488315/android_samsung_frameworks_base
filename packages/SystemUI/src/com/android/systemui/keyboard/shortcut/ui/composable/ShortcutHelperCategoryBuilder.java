package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.ArrayList;
import java.util.List;

public final class ShortcutHelperCategoryBuilder {
    public final ImageVector icon;
    public final int labelResId;
    public final List subCategories = new ArrayList();

    public ShortcutHelperCategoryBuilder(int i, ImageVector imageVector) {
        this.labelResId = i;
        this.icon = imageVector;
    }
}
