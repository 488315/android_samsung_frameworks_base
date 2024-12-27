package com.android.systemui.keyboard.data.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

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
