package com.android.systemui.keyboard.shortcut.shared.model;

import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ShortcutCommand {
    public final boolean isCustom;
    public final List keys;

    public ShortcutCommand() {
        this(null, false, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutCommand)) {
            return false;
        }
        ShortcutCommand shortcutCommand = (ShortcutCommand) obj;
        return Intrinsics.areEqual(this.keys, shortcutCommand.keys) && this.isCustom == shortcutCommand.isCustom;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isCustom) + (this.keys.hashCode() * 31);
    }

    public final String toString() {
        return "ShortcutCommand(keys=" + this.keys + ", isCustom=" + this.isCustom + ")";
    }

    public ShortcutCommand(List<? extends ShortcutKey> list, boolean z) {
        this.keys = list;
        this.isCustom = z;
    }

    public ShortcutCommand(List list, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EmptyList.INSTANCE : list, (i & 2) != 0 ? false : z);
    }
}
