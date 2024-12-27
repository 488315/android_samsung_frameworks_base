package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
