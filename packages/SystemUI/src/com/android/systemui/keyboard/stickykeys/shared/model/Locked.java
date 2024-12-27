package com.android.systemui.keyboard.stickykeys.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

public final class Locked {
    public final boolean locked;

    private /* synthetic */ Locked(boolean z) {
        this.locked = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Locked m1945boximpl(boolean z) {
        return new Locked(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Locked) {
            return this.locked == ((Locked) obj).locked;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.locked);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Locked(locked="), this.locked, ")");
    }
}
