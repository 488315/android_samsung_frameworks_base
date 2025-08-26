package com.android.systemui.keyboard.shortcut.shared.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ShortcutSubCategory {
    public final boolean containsCustomShortcuts;
    public final String label;
    public final List shortcuts;

    public ShortcutSubCategory(String str, List<Shortcut> list) {
        this.label = str;
        this.shortcuts = list;
        List<Shortcut> list2 = list;
        boolean z = false;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((Shortcut) it.next()).containsCustomShortcutCommands) {
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
        if (!(obj instanceof ShortcutSubCategory)) {
            return false;
        }
        ShortcutSubCategory shortcutSubCategory = (ShortcutSubCategory) obj;
        return Intrinsics.areEqual(this.label, shortcutSubCategory.label) && Intrinsics.areEqual(this.shortcuts, shortcutSubCategory.shortcuts);
    }

    public final int hashCode() {
        return this.shortcuts.hashCode() + (this.label.hashCode() * 31);
    }

    public final String toString() {
        return "ShortcutSubCategory(label=" + this.label + ", shortcuts=" + this.shortcuts + ")";
    }
}
