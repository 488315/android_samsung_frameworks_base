package com.android.systemui.qs.tileimpl;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSLongPressProperties {
    public final int backgroundColor;
    public final int chevronColor;
    public final float cornerRadius;
    public float height;
    public final int iconColor;
    public final int labelColor;
    public final int overlayColor;
    public final int secondaryLabelColor;
    public float width;

    public QSLongPressProperties(float f, float f2, float f3, int i, int i2, int i3, int i4, int i5, int i6) {
        this.height = f;
        this.width = f2;
        this.cornerRadius = f3;
        this.backgroundColor = i;
        this.labelColor = i2;
        this.secondaryLabelColor = i3;
        this.chevronColor = i4;
        this.overlayColor = i5;
        this.iconColor = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSLongPressProperties)) {
            return false;
        }
        QSLongPressProperties qSLongPressProperties = (QSLongPressProperties) obj;
        return Float.compare(this.height, qSLongPressProperties.height) == 0 && Float.compare(this.width, qSLongPressProperties.width) == 0 && Float.compare(this.cornerRadius, qSLongPressProperties.cornerRadius) == 0 && this.backgroundColor == qSLongPressProperties.backgroundColor && this.labelColor == qSLongPressProperties.labelColor && this.secondaryLabelColor == qSLongPressProperties.secondaryLabelColor && this.chevronColor == qSLongPressProperties.chevronColor && this.overlayColor == qSLongPressProperties.overlayColor && this.iconColor == qSLongPressProperties.iconColor;
    }

    public final int hashCode() {
        return Integer.hashCode(this.iconColor) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.overlayColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.chevronColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.secondaryLabelColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.labelColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.backgroundColor, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.width, Float.hashCode(this.height) * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("QSLongPressProperties(height=", this.height, ", width=", this.width, ", cornerRadius=");
        m.append(this.cornerRadius);
        m.append(", backgroundColor=");
        m.append(this.backgroundColor);
        m.append(", labelColor=");
        m.append(this.labelColor);
        m.append(", secondaryLabelColor=");
        m.append(this.secondaryLabelColor);
        m.append(", chevronColor=");
        m.append(this.chevronColor);
        m.append(", overlayColor=");
        m.append(this.overlayColor);
        m.append(", iconColor=");
        return Anchor$$ExternalSyntheticOutline0.m(this.iconColor, ")", m);
    }
}
