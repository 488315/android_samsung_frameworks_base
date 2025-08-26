package com.android.systemui.qs.tileimpl;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

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
        return Integer.hashCode(this.iconColor) + ReorderTile$$ExternalSyntheticOutline0.m(this.overlayColor, ReorderTile$$ExternalSyntheticOutline0.m(this.chevronColor, ReorderTile$$ExternalSyntheticOutline0.m(this.secondaryLabelColor, ReorderTile$$ExternalSyntheticOutline0.m(this.labelColor, ReorderTile$$ExternalSyntheticOutline0.m(this.backgroundColor, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.width, Float.hashCode(this.height) * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("QSLongPressProperties(height=", this.height, ", width=", this.width, ", cornerRadius=");
        sbM.append(this.cornerRadius);
        sbM.append(", backgroundColor=");
        sbM.append(this.backgroundColor);
        sbM.append(", labelColor=");
        sbM.append(this.labelColor);
        sbM.append(", secondaryLabelColor=");
        sbM.append(this.secondaryLabelColor);
        sbM.append(", chevronColor=");
        sbM.append(this.chevronColor);
        sbM.append(", overlayColor=");
        sbM.append(this.overlayColor);
        sbM.append(", iconColor=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.iconColor, ")", sbM);
    }
}
