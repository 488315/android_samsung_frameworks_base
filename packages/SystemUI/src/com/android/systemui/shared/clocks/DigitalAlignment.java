package com.android.systemui.shared.clocks;

import com.android.systemui.shared.clocks.view.HorizontalAlignment;
import com.android.systemui.shared.clocks.view.VerticalAlignment;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = (horizontalAlignment == null ? 0 : horizontalAlignment.hashCode()) * 31;
        VerticalAlignment verticalAlignment = this.verticalAlignment;
        return hashCode + (verticalAlignment != null ? verticalAlignment.hashCode() : 0);
    }

    public final String toString() {
        return "DigitalAlignment(horizontalAlignment=" + this.horizontalAlignment + ", verticalAlignment=" + this.verticalAlignment + ")";
    }
}
