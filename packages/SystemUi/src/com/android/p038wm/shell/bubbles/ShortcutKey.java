package com.android.p038wm.shell.bubbles;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
