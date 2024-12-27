package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class SubCategory {
    public final String label;
    public final List shortcuts;

    public SubCategory(String str, List<Shortcut> list) {
        this.label = str;
        this.shortcuts = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubCategory)) {
            return false;
        }
        SubCategory subCategory = (SubCategory) obj;
        return Intrinsics.areEqual(this.label, subCategory.label) && Intrinsics.areEqual(this.shortcuts, subCategory.shortcuts);
    }

    public final int hashCode() {
        return this.shortcuts.hashCode() + (this.label.hashCode() * 31);
    }

    public final String toString() {
        return "SubCategory(label=" + this.label + ", shortcuts=" + this.shortcuts + ")";
    }
}
