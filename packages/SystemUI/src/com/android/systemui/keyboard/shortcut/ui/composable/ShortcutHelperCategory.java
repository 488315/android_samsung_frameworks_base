package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class ShortcutHelperCategory {
    public final ImageVector icon;
    public final int labelResId;
    public final List subCategories;

    public ShortcutHelperCategory(int i, ImageVector imageVector, List<SubCategory> list) {
        this.labelResId = i;
        this.icon = imageVector;
        this.subCategories = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutHelperCategory)) {
            return false;
        }
        ShortcutHelperCategory shortcutHelperCategory = (ShortcutHelperCategory) obj;
        return this.labelResId == shortcutHelperCategory.labelResId && Intrinsics.areEqual(this.icon, shortcutHelperCategory.icon) && Intrinsics.areEqual(this.subCategories, shortcutHelperCategory.subCategories);
    }

    public final int hashCode() {
        return this.subCategories.hashCode() + ((this.icon.hashCode() + (Integer.hashCode(this.labelResId) * 31)) * 31);
    }

    public final String toString() {
        return "ShortcutHelperCategory(labelResId=" + this.labelResId + ", icon=" + this.icon + ", subCategories=" + this.subCategories + ")";
    }
}
