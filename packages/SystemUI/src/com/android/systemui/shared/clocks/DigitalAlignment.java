package com.android.systemui.shared.clocks;

import com.android.systemui.shared.clocks.view.HorizontalAlignment;
import com.android.systemui.shared.clocks.view.VerticalAlignment;

/* loaded from: classes3.dex */
public final class DigitalAlignment {
    public final HorizontalAlignment horizontalAlignment;
    public final VerticalAlignment verticalAlignment;

    public DigitalAlignment(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DigitalAlignment)) {
            return false;
        }
        DigitalAlignment digitalAlignment = (DigitalAlignment) obj;
        return this.horizontalAlignment == digitalAlignment.horizontalAlignment && this.verticalAlignment == digitalAlignment.verticalAlignment;
    }

    public final int hashCode() {
        HorizontalAlignment horizontalAlignment = this.horizontalAlignment;
        int iHashCode = (horizontalAlignment == null ? 0 : horizontalAlignment.hashCode()) * 31;
        VerticalAlignment verticalAlignment = this.verticalAlignment;
        return iHashCode + (verticalAlignment != null ? verticalAlignment.hashCode() : 0);
    }

    public final String toString() {
        return "DigitalAlignment(horizontalAlignment=" + this.horizontalAlignment + ", verticalAlignment=" + this.verticalAlignment + ")";
    }
}
