package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
