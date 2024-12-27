package com.android.systemui.keyboard.shortcut.ui.composable;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Shortcut {
    public final List commands;
    public final String label;

    public Shortcut(String str, List<ShortcutCommand> list) {
        this.label = str;
        this.commands = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Shortcut)) {
            return false;
        }
        Shortcut shortcut = (Shortcut) obj;
        return Intrinsics.areEqual(this.label, shortcut.label) && Intrinsics.areEqual(this.commands, shortcut.commands);
    }

    public final int hashCode() {
        return this.commands.hashCode() + (this.label.hashCode() * 31);
    }

    public final String toString() {
        return "Shortcut(label=" + this.label + ", commands=" + this.commands + ")";
    }
}
