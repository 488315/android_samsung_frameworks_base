package com.android.systemui.shade;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeExpansionChangeEvent {
    public final float dragDownPxAmount;
    public final boolean expanded;
    public final float fraction;
    public final boolean tracking;

    public ShadeExpansionChangeEvent(float f, boolean z, boolean z2, float f2) {
        this.fraction = f;
        this.expanded = z;
        this.tracking = z2;
        this.dragDownPxAmount = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeExpansionChangeEvent)) {
            return false;
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = (ShadeExpansionChangeEvent) obj;
        return Float.compare(this.fraction, shadeExpansionChangeEvent.fraction) == 0 && this.expanded == shadeExpansionChangeEvent.expanded && this.tracking == shadeExpansionChangeEvent.tracking && Float.compare(this.dragDownPxAmount, shadeExpansionChangeEvent.dragDownPxAmount) == 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Float.hashCode(this.fraction) * 31;
        boolean z = this.expanded;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.tracking;
        return Float.hashCode(this.dragDownPxAmount) + ((i2 + (z2 ? 1 : z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        return "ShadeExpansionChangeEvent(fraction=" + this.fraction + ", expanded=" + this.expanded + ", tracking=" + this.tracking + ", dragDownPxAmount=" + this.dragDownPxAmount + ")";
    }
}
