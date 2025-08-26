package com.android.wm.shell.bubbles;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ShortcutKey {
    public final String pkg;
    public final int userId;

    public ShortcutKey(int i, String str) {
        this.userId = i;
        this.pkg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutKey)) {
            return false;
        }
        ShortcutKey shortcutKey = (ShortcutKey) obj;
        return this.userId == shortcutKey.userId && Intrinsics.areEqual(this.pkg, shortcutKey.pkg);
    }

    public final int hashCode() {
        return this.pkg.hashCode() + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        return "ShortcutKey(userId=" + this.userId + ", pkg=" + this.pkg + ")";
    }
}
