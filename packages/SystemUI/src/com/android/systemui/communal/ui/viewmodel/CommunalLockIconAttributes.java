package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalLockIconAttributes {
    public final int padding;
    public final int tint;
    public final DeviceEntryIconView.IconType type;

    public CommunalLockIconAttributes(DeviceEntryIconView.IconType iconType, int i, int i2) {
        this.type = iconType;
        this.tint = i;
        this.padding = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalLockIconAttributes)) {
            return false;
        }
        CommunalLockIconAttributes communalLockIconAttributes = (CommunalLockIconAttributes) obj;
        return this.type == communalLockIconAttributes.type && this.tint == communalLockIconAttributes.tint && this.padding == communalLockIconAttributes.padding;
    }

    public final int hashCode() {
        return Integer.hashCode(this.padding) + ReorderTile$$ExternalSyntheticOutline0.m(this.tint, this.type.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CommunalLockIconAttributes(type=");
        sb.append(this.type);
        sb.append(", tint=");
        sb.append(this.tint);
        sb.append(", padding=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.padding, ")", sb);
    }
}
