package com.android.systemui.keyboard.shortcut.shared.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Shortcut {
    public final List commands;
    public final boolean containsCustomShortcutCommands;
    public final String contentDescription;
    public final ShortcutIcon icon;
    public final boolean isCustomizable;
    public final String label;

    public Shortcut(String str, List<ShortcutCommand> list, ShortcutIcon shortcutIcon, String str2, boolean z) {
        this.label = str;
        this.commands = list;
        this.icon = shortcutIcon;
        this.contentDescription = str2;
        this.isCustomizable = z;
        List<ShortcutCommand> list2 = list;
        boolean z2 = false;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((ShortcutCommand) it.next()).isCustom) {
                    z2 = true;
                    break;
                }
            }
        }
        this.containsCustomShortcutCommands = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Shortcut)) {
            return false;
        }
        Shortcut shortcut = (Shortcut) obj;
        return Intrinsics.areEqual(this.label, shortcut.label) && Intrinsics.areEqual(this.commands, shortcut.commands) && Intrinsics.areEqual(this.icon, shortcut.icon) && Intrinsics.areEqual(this.contentDescription, shortcut.contentDescription) && this.isCustomizable == shortcut.isCustomizable;
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.commands, this.label.hashCode() * 31, 31);
        ShortcutIcon shortcutIcon = this.icon;
        return Boolean.hashCode(this.isCustomizable) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((m + (shortcutIcon == null ? 0 : shortcutIcon.hashCode())) * 31, 31, this.contentDescription);
    }

    public final String toString() {
        List list = this.commands;
        StringBuilder sb = new StringBuilder("Shortcut(label=");
        sb.append(this.label);
        sb.append(", commands=");
        sb.append(list);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", contentDescription=");
        sb.append(this.contentDescription);
        sb.append(", isCustomizable=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCustomizable, ")");
    }

    public /* synthetic */ Shortcut(String str, List list, ShortcutIcon shortcutIcon, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : shortcutIcon, (i & 8) != 0 ? "" : str2, (i & 16) != 0 ? true : z);
    }
}
