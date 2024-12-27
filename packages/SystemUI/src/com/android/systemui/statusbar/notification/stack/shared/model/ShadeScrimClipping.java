package com.android.systemui.statusbar.notification.stack.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeScrimClipping {
    public final ShadeScrimBounds bounds;
    public final ShadeScrimRounding rounding;

    public ShadeScrimClipping() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeScrimClipping)) {
            return false;
        }
        ShadeScrimClipping shadeScrimClipping = (ShadeScrimClipping) obj;
        return Intrinsics.areEqual(this.bounds, shadeScrimClipping.bounds) && Intrinsics.areEqual(this.rounding, shadeScrimClipping.rounding);
    }

    public final int hashCode() {
        return this.rounding.hashCode() + (this.bounds.hashCode() * 31);
    }

    public final String toString() {
        return "ShadeScrimClipping(bounds=" + this.bounds + ", rounding=" + this.rounding + ")";
    }

    public ShadeScrimClipping(ShadeScrimBounds shadeScrimBounds, ShadeScrimRounding shadeScrimRounding) {
        this.bounds = shadeScrimBounds;
        this.rounding = shadeScrimRounding;
    }

    public /* synthetic */ ShadeScrimClipping(ShadeScrimBounds shadeScrimBounds, ShadeScrimRounding shadeScrimRounding, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ShadeScrimBounds(0.0f, 0.0f, 0.0f, 0.0f, 15, null) : shadeScrimBounds, (i & 2) != 0 ? new ShadeScrimRounding(false, false, 3, null) : shadeScrimRounding);
    }
}
