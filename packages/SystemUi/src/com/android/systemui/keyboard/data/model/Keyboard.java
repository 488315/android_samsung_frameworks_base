package com.android.systemui.keyboard.data.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Keyboard {
    public final int productId;
    public final int vendorId;

    public Keyboard(int i, int i2) {
        this.vendorId = i;
        this.productId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Keyboard)) {
            return false;
        }
        Keyboard keyboard = (Keyboard) obj;
        return this.vendorId == keyboard.vendorId && this.productId == keyboard.productId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.productId) + (Integer.hashCode(this.vendorId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Keyboard(vendorId=");
        sb.append(this.vendorId);
        sb.append(", productId=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.productId, ")");
    }
}
