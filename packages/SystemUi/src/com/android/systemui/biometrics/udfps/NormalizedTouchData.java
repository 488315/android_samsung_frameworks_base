package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NormalizedTouchData {
    public final long gestureStart;
    public final float major;
    public final float minor;
    public final float orientation;
    public final int pointerId;
    public final long time;

    /* renamed from: x */
    public final float f234x;

    /* renamed from: y */
    public final float f235y;

    public NormalizedTouchData() {
        this(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NormalizedTouchData)) {
            return false;
        }
        NormalizedTouchData normalizedTouchData = (NormalizedTouchData) obj;
        return this.pointerId == normalizedTouchData.pointerId && Float.compare(this.f234x, normalizedTouchData.f234x) == 0 && Float.compare(this.f235y, normalizedTouchData.f235y) == 0 && Float.compare(this.minor, normalizedTouchData.minor) == 0 && Float.compare(this.major, normalizedTouchData.major) == 0 && Float.compare(this.orientation, normalizedTouchData.orientation) == 0 && this.time == normalizedTouchData.time && this.gestureStart == normalizedTouchData.gestureStart;
    }

    public final int hashCode() {
        return Long.hashCode(this.gestureStart) + TraceMetadata$$ExternalSyntheticOutline0.m51m(this.time, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.orientation, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.major, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.minor, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.f235y, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.f234x, Integer.hashCode(this.pointerId) * 31, 31), 31), 31), 31), 31), 31);
    }

    public final boolean isWithinBounds(Rect rect) {
        float f = rect.left;
        float f2 = this.f234x;
        if (f <= f2 && rect.right >= f2) {
            float f3 = rect.top;
            float f4 = this.f235y;
            if (f3 <= f4 && rect.bottom >= f4) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return "NormalizedTouchData(pointerId=" + this.pointerId + ", x=" + this.f234x + ", y=" + this.f235y + ", minor=" + this.minor + ", major=" + this.major + ", orientation=" + this.orientation + ", time=" + this.time + ", gestureStart=" + this.gestureStart + ")";
    }

    public NormalizedTouchData(int i, float f, float f2, float f3, float f4, float f5, long j, long j2) {
        this.pointerId = i;
        this.f234x = f;
        this.f235y = f2;
        this.minor = f3;
        this.major = f4;
        this.orientation = f5;
        this.time = j;
        this.gestureStart = j2;
    }

    public /* synthetic */ NormalizedTouchData(int i, float f, float f2, float f3, float f4, float f5, long j, long j2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3, (i2 & 16) != 0 ? 0.0f : f4, (i2 & 32) == 0 ? f5 : 0.0f, (i2 & 64) != 0 ? 0L : j, (i2 & 128) == 0 ? j2 : 0L);
    }
}
