package com.android.systemui.biometrics.shared.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SensorLocation {
    public final int naturalCenterX;
    public final int naturalCenterY;
    public final int naturalRadius;
    public final float scale;

    public SensorLocation(int i, int i2, int i3, float f) {
        this.naturalCenterX = i;
        this.naturalCenterY = i2;
        this.naturalRadius = i3;
        this.scale = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SensorLocation)) {
            return false;
        }
        SensorLocation sensorLocation = (SensorLocation) obj;
        return this.naturalCenterX == sensorLocation.naturalCenterX && this.naturalCenterY == sensorLocation.naturalCenterY && this.naturalRadius == sensorLocation.naturalRadius && Float.compare(this.scale, sensorLocation.scale) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.scale) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.naturalRadius, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.naturalCenterY, Integer.hashCode(this.naturalCenterX) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SensorLocation(naturalCenterX=");
        sb.append(this.naturalCenterX);
        sb.append(", naturalCenterY=");
        sb.append(this.naturalCenterY);
        sb.append(", naturalRadius=");
        sb.append(this.naturalRadius);
        sb.append(", scale=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.scale, ")");
    }

    public /* synthetic */ SensorLocation(int i, int i2, int i3, float f, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i4 & 8) != 0 ? 1.0f : f);
    }
}
