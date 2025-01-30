package com.android.systemui.shade.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeModel {
    public final float expansionAmount;
    public final boolean isExpanded;
    public final boolean isUserDragging;

    public ShadeModel() {
        this(0.0f, false, false, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeModel)) {
            return false;
        }
        ShadeModel shadeModel = (ShadeModel) obj;
        return Float.compare(this.expansionAmount, shadeModel.expansionAmount) == 0 && this.isExpanded == shadeModel.isExpanded && this.isUserDragging == shadeModel.isUserDragging;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Float.hashCode(this.expansionAmount) * 31;
        boolean z = this.isExpanded;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.isUserDragging;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShadeModel(expansionAmount=");
        sb.append(this.expansionAmount);
        sb.append(", isExpanded=");
        sb.append(this.isExpanded);
        sb.append(", isUserDragging=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isUserDragging, ")");
    }

    public ShadeModel(float f, boolean z, boolean z2) {
        this.expansionAmount = f;
        this.isExpanded = z;
        this.isUserDragging = z2;
    }

    public /* synthetic */ ShadeModel(float f, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2);
    }
}
