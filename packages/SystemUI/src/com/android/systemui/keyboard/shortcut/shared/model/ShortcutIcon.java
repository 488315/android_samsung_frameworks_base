package com.android.systemui.keyboard.shortcut.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ShortcutIcon {
    public final String packageName;
    public final int resourceId;

    public ShortcutIcon(String str, int i) {
        this.packageName = str;
        this.resourceId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutIcon)) {
            return false;
        }
        ShortcutIcon shortcutIcon = (ShortcutIcon) obj;
        return Intrinsics.areEqual(this.packageName, shortcutIcon.packageName) && this.resourceId == shortcutIcon.resourceId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.resourceId) + (this.packageName.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShortcutIcon(packageName=");
        sb.append(this.packageName);
        sb.append(", resourceId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.resourceId, ")", sb);
    }
}
