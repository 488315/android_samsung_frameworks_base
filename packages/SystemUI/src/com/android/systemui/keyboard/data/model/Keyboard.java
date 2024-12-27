package com.android.systemui.keyboard.data.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
        return Anchor$$ExternalSyntheticOutline0.m(this.productId, ")", sb);
    }
}
