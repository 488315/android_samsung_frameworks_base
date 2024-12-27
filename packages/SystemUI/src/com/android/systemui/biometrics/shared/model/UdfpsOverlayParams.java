package com.android.systemui.biometrics.shared.model;

import android.graphics.Rect;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class UdfpsOverlayParams {
    public final int logicalDisplayHeight;
    public final int logicalDisplayWidth;
    public final Rect nativeOverlayBounds;
    public final Rect nativeSensorBounds;
    public final int naturalDisplayHeight;
    public final int naturalDisplayWidth;
    public final Rect overlayBounds;
    public final int rotation;
    public final float scaleFactor;
    public final Rect sensorBounds;
    public final int sensorType;

    public UdfpsOverlayParams() {
        this(null, null, 0, 0, 0.0f, 0, 0, 127, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UdfpsOverlayParams)) {
            return false;
        }
        UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) obj;
        return Intrinsics.areEqual(this.sensorBounds, udfpsOverlayParams.sensorBounds) && Intrinsics.areEqual(this.overlayBounds, udfpsOverlayParams.overlayBounds) && this.naturalDisplayWidth == udfpsOverlayParams.naturalDisplayWidth && this.naturalDisplayHeight == udfpsOverlayParams.naturalDisplayHeight && Float.compare(this.scaleFactor, udfpsOverlayParams.scaleFactor) == 0 && this.rotation == udfpsOverlayParams.rotation && this.sensorType == udfpsOverlayParams.sensorType;
    }

    public final int hashCode() {
        return Integer.hashCode(this.sensorType) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.rotation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleFactor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.naturalDisplayHeight, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.naturalDisplayWidth, (this.overlayBounds.hashCode() + (this.sensorBounds.hashCode() * 31)) * 31, 31), 31), 31), 31);
    }

    public final String toString() {
        Rect rect = this.sensorBounds;
        Rect rect2 = this.overlayBounds;
        StringBuilder sb = new StringBuilder("UdfpsOverlayParams(sensorBounds=");
        sb.append(rect);
        sb.append(", overlayBounds=");
        sb.append(rect2);
        sb.append(", naturalDisplayWidth=");
        sb.append(this.naturalDisplayWidth);
        sb.append(", naturalDisplayHeight=");
        sb.append(this.naturalDisplayHeight);
        sb.append(", scaleFactor=");
        sb.append(this.scaleFactor);
        sb.append(", rotation=");
        sb.append(this.rotation);
        sb.append(", sensorType=");
        return Anchor$$ExternalSyntheticOutline0.m(this.sensorType, ")", sb);
    }

    public UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3, int i4) {
        this.sensorBounds = rect;
        this.overlayBounds = rect2;
        this.naturalDisplayWidth = i;
        this.naturalDisplayHeight = i2;
        this.scaleFactor = f;
        this.rotation = i3;
        this.sensorType = i4;
        Rect rect3 = new Rect(rect);
        rect3.scale(1.0f / f);
        this.nativeSensorBounds = rect3;
        Rect rect4 = new Rect(rect2);
        rect4.scale(1.0f / f);
        this.nativeOverlayBounds = rect4;
        this.logicalDisplayWidth = (i3 == 1 || i3 == 3) ? i2 : i;
        if (i3 != 1 && i3 != 3) {
            i = i2;
        }
        this.logicalDisplayHeight = i;
    }

    public /* synthetic */ UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? new Rect() : rect, (i5 & 2) != 0 ? new Rect() : rect2, (i5 & 4) != 0 ? 0 : i, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? 1.0f : f, (i5 & 32) == 0 ? i3 : 0, (i5 & 64) != 0 ? 3 : i4);
    }
}
