package com.android.systemui.common.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        int hashCode = this.icon.hashCode() * 31;
        Integer num = this.tint;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "TintedIcon(icon=" + this.icon + ", tint=" + this.tint + ")";
    }
}
