package com.android.systemui.keyboard.shortcut.data.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class InternalKeyboardShortcutGroup {
    public final List items;
    public final String label;
    public final String packageName;

    public InternalKeyboardShortcutGroup(String str, List<InternalKeyboardShortcutInfo> list, String str2) {
        this.label = str;
        this.items = list;
        this.packageName = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InternalKeyboardShortcutGroup)) {
            return false;
        }
        InternalKeyboardShortcutGroup internalKeyboardShortcutGroup = (InternalKeyboardShortcutGroup) obj;
        return Intrinsics.areEqual(this.label, internalKeyboardShortcutGroup.label) && Intrinsics.areEqual(this.items, internalKeyboardShortcutGroup.items) && Intrinsics.areEqual(this.packageName, internalKeyboardShortcutGroup.packageName);
    }

    public final int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.items, this.label.hashCode() * 31, 31);
        String str = this.packageName;
        return iM + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        List list = this.items;
        StringBuilder sb = new StringBuilder("InternalKeyboardShortcutGroup(label=");
        sb.append(this.label);
        sb.append(", items=");
        sb.append(list);
        sb.append(", packageName=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.packageName, ")");
    }

    public /* synthetic */ InternalKeyboardShortcutGroup(String str, List list, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : str2);
    }
}
