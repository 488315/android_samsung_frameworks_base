package com.android.systemui.statusbar.notification.stack.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ShadeScrimRounding {
    public final boolean isBottomRounded;
    public final boolean isTopRounded;

    /* JADX WARN: Illegal instructions before constructor call */
    public ShadeScrimRounding() {
        boolean z = false;
        this(z, z, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeScrimRounding)) {
            return false;
        }
        ShadeScrimRounding shadeScrimRounding = (ShadeScrimRounding) obj;
        return this.isTopRounded == shadeScrimRounding.isTopRounded && this.isBottomRounded == shadeScrimRounding.isBottomRounded;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isBottomRounded) + (Boolean.hashCode(this.isTopRounded) * 31);
    }

    public final String toString() {
        return "ShadeScrimRounding(isTopRounded=" + this.isTopRounded + ", isBottomRounded=" + this.isBottomRounded + ")";
    }

    public ShadeScrimRounding(boolean z, boolean z2) {
        this.isTopRounded = z;
        this.isBottomRounded = z2;
    }

    public /* synthetic */ ShadeScrimRounding(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
    }
}
