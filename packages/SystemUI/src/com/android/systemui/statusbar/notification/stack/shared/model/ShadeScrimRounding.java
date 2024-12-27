package com.android.systemui.statusbar.notification.stack.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeScrimRounding {
    public final boolean isBottomRounded;
    public final boolean isTopRounded;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ShadeScrimRounding() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimRounding.<init>():void");
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
