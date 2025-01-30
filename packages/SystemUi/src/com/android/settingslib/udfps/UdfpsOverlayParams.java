package com.android.settingslib.udfps;

import android.graphics.Rect;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    public UdfpsOverlayParams() {
        this(null, null, 0, 0, 0.0f, 0, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UdfpsOverlayParams)) {
            return false;
        }
        UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) obj;
        return Intrinsics.areEqual(this.sensorBounds, udfpsOverlayParams.sensorBounds) && Intrinsics.areEqual(this.overlayBounds, udfpsOverlayParams.overlayBounds) && this.naturalDisplayWidth == udfpsOverlayParams.naturalDisplayWidth && this.naturalDisplayHeight == udfpsOverlayParams.naturalDisplayHeight && Float.compare(this.scaleFactor, udfpsOverlayParams.scaleFactor) == 0 && this.rotation == udfpsOverlayParams.rotation;
    }

    public final int hashCode() {
        return Integer.hashCode(this.rotation) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.scaleFactor, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.naturalDisplayHeight, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.naturalDisplayWidth, (this.overlayBounds.hashCode() + (this.sensorBounds.hashCode() * 31)) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UdfpsOverlayParams(sensorBounds=");
        sb.append(this.sensorBounds);
        sb.append(", overlayBounds=");
        sb.append(this.overlayBounds);
        sb.append(", naturalDisplayWidth=");
        sb.append(this.naturalDisplayWidth);
        sb.append(", naturalDisplayHeight=");
        sb.append(this.naturalDisplayHeight);
        sb.append(", scaleFactor=");
        sb.append(this.scaleFactor);
        sb.append(", rotation=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.rotation, ")");
    }

    public UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3) {
        this.sensorBounds = rect;
        this.overlayBounds = rect2;
        this.naturalDisplayWidth = i;
        this.naturalDisplayHeight = i2;
        this.scaleFactor = f;
        this.rotation = i3;
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

    public /* synthetic */ UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? new Rect() : rect, (i4 & 2) != 0 ? new Rect() : rect2, (i4 & 4) != 0 ? 0 : i, (i4 & 8) != 0 ? 0 : i2, (i4 & 16) != 0 ? 1.0f : f, (i4 & 32) == 0 ? i3 : 0);
    }
}
