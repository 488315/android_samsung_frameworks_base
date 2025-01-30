package com.android.systemui.common.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
