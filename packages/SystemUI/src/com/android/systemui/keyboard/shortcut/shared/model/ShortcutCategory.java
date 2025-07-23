package com.android.systemui.keyboard.shortcut.shared.model;

import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutCategory {
    public final List subCategories;
    public final ShortcutCategoryType type;

    public ShortcutCategory(ShortcutCategoryType shortcutCategoryType, ShortcutSubCategory... shortcutSubCategoryArr) {
        this(shortcutCategoryType, (List<ShortcutSubCategory>) Arrays.asList(shortcutSubCategoryArr));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutCategory)) {
            return false;
        }
        ShortcutCategory shortcutCategory = (ShortcutCategory) obj;
        return Intrinsics.areEqual(this.type, shortcutCategory.type) && Intrinsics.areEqual(this.subCategories, shortcutCategory.subCategories);
    }

    public final int hashCode() {
        return this.subCategories.hashCode() + (this.type.hashCode() * 31);
    }

    public final String toString() {
        return "ShortcutCategory(type=" + this.type + ", subCategories=" + this.subCategories + ")";
    }

    public ShortcutCategory(ShortcutCategoryType shortcutCategoryType, List<ShortcutSubCategory> list) {
        this.type = shortcutCategoryType;
        this.subCategories = list;
    }
}
