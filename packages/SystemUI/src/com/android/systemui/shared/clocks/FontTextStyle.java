package com.android.systemui.shared.clocks;

import android.view.animation.Interpolator;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FontTextStyle {
    public final Float fontSizeScale;
    public final Float lineHeight;
    public final long transitionDuration;
    public final Interpolator transitionInterpolator;

    public FontTextStyle() {
        this(null, null, 0L, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FontTextStyle)) {
            return false;
        }
        FontTextStyle fontTextStyle = (FontTextStyle) obj;
        return Intrinsics.areEqual(this.lineHeight, fontTextStyle.lineHeight) && Intrinsics.areEqual(this.fontSizeScale, fontTextStyle.fontSizeScale) && this.transitionDuration == fontTextStyle.transitionDuration && Intrinsics.areEqual(this.transitionInterpolator, fontTextStyle.transitionInterpolator);
    }

    public final int hashCode() {
        Float f = this.lineHeight;
        int hashCode = (f == null ? 0 : f.hashCode()) * 31;
        Float f2 = this.fontSizeScale;
        int m = MoveResult$$ExternalSyntheticOutline0.m((hashCode + (f2 == null ? 0 : f2.hashCode())) * 31, 31, this.transitionDuration);
        Interpolator interpolator = this.transitionInterpolator;
        return m + (interpolator != null ? interpolator.hashCode() : 0);
    }

    public final String toString() {
        return "FontTextStyle(lineHeight=" + this.lineHeight + ", fontSizeScale=" + this.fontSizeScale + ", transitionDuration=" + this.transitionDuration + ", transitionInterpolator=" + this.transitionInterpolator + ")";
    }

    public FontTextStyle(Float f, Float f2, long j, Interpolator interpolator) {
        this.lineHeight = f;
        this.fontSizeScale = f2;
        this.transitionDuration = j;
        this.transitionInterpolator = interpolator;
    }

    public /* synthetic */ FontTextStyle(Float f, Float f2, long j, Interpolator interpolator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : f, (i & 2) != 0 ? null : f2, (i & 4) != 0 ? 300L : j, (i & 8) != 0 ? null : interpolator);
    }
}
