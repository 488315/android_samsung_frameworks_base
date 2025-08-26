package com.android.systemui.keyboard.shortcut.ui.model;

import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ShortcutCategoryUi {
    public final boolean containsCustomShortcuts;
    public final IconSource iconSource;
    public final String label;
    public final List subCategories;
    public final ShortcutCategoryType type;

    public ShortcutCategoryUi(String str, IconSource iconSource, ShortcutCategoryType shortcutCategoryType, List<ShortcutSubCategory> list) {
        this.label = str;
        this.iconSource = iconSource;
        this.type = shortcutCategoryType;
        this.subCategories = list;
        List<ShortcutSubCategory> list2 = list;
        boolean z = false;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((ShortcutSubCategory) it.next()).containsCustomShortcuts) {
                    z = true;
                    break;
                }
            }
        }
        this.containsCustomShortcuts = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutCategoryUi)) {
            return false;
        }
        ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) obj;
        return Intrinsics.areEqual(this.label, shortcutCategoryUi.label) && Intrinsics.areEqual(this.iconSource, shortcutCategoryUi.iconSource) && Intrinsics.areEqual(this.type, shortcutCategoryUi.type) && Intrinsics.areEqual(this.subCategories, shortcutCategoryUi.subCategories);
    }

    public final int hashCode() {
        return this.subCategories.hashCode() + ((this.type.hashCode() + ((this.iconSource.hashCode() + (this.label.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "ShortcutCategoryUi(label=" + this.label + ", iconSource=" + this.iconSource + ", type=" + this.type + ", subCategories=" + this.subCategories + ")";
    }

    public ShortcutCategoryUi(String str, IconSource iconSource, ShortcutCategory shortcutCategory) {
        this(str, iconSource, shortcutCategory.type, shortcutCategory.subCategories);
    }
}
