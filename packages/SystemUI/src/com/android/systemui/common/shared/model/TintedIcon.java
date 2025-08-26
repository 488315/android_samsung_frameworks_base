package com.android.systemui.common.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TintedIcon {
    public final Icon icon;
    public final Integer tint;

    public TintedIcon(Icon icon, Integer num) {
        this.icon = icon;
        this.tint = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TintedIcon)) {
            return false;
        }
        TintedIcon tintedIcon = (TintedIcon) obj;
        return Intrinsics.areEqual(this.icon, tintedIcon.icon) && Intrinsics.areEqual(this.tint, tintedIcon.tint);
    }

    public final int hashCode() {
        int iHashCode = this.icon.hashCode() * 31;
        Integer num = this.tint;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "TintedIcon(icon=" + this.icon + ", tint=" + this.tint + ")";
    }
}
