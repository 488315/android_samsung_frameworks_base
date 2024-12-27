package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShortcutCommand {
    public final List keys;

    public ShortcutCommand(List<? extends ShortcutKey> list) {
        this.keys = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ShortcutCommand) && Intrinsics.areEqual(this.keys, ((ShortcutCommand) obj).keys);
    }

    public final int hashCode() {
        return this.keys.hashCode();
    }

    public final String toString() {
        return "ShortcutCommand(keys=" + this.keys + ")";
    }
}
