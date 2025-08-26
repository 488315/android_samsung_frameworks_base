package com.android.systemui.keyboard.shortcut.data.model;

import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class InternalGroupsSource {
    public final List groups;
    public final ShortcutCategoryType type;

    public InternalGroupsSource(List<InternalKeyboardShortcutGroup> list, ShortcutCategoryType shortcutCategoryType) {
        this.groups = list;
        this.type = shortcutCategoryType;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InternalGroupsSource)) {
            return false;
        }
        InternalGroupsSource internalGroupsSource = (InternalGroupsSource) obj;
        return Intrinsics.areEqual(this.groups, internalGroupsSource.groups) && Intrinsics.areEqual(this.type, internalGroupsSource.type);
    }

    public final int hashCode() {
        return this.type.hashCode() + (this.groups.hashCode() * 31);
    }

    public final String toString() {
        return "InternalGroupsSource(groups=" + this.groups + ", type=" + this.type + ")";
    }
}
