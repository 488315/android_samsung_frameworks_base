package com.android.compose.animation.scene;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Scale {
    public static final Companion Companion = new Companion(null);
    public static final Scale Default;
    public static final Scale Unspecified;
    public static final Scale Zero;
    public final long pivot;
    public final float scaleX;
    public final float scaleY;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Offset.Companion companion = Offset.Companion;
        companion.getClass();
        long j = Offset.Unspecified;
        Default = new Scale(1.0f, 1.0f, j, null);
        companion.getClass();
        Zero = new Scale(0.0f, 0.0f, 0L, null);
        companion.getClass();
        Unspecified = new Scale(Float.MAX_VALUE, Float.MAX_VALUE, j, null);
    }

    public /* synthetic */ Scale(float f, float f2, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scale)) {
            return false;
        }
        Scale scale = (Scale) obj;
        return Float.compare(this.scaleX, scale.scaleX) == 0 && Float.compare(this.scaleY, scale.scaleY) == 0 && Offset.m398equalsimpl0(this.pivot, scale.pivot);
    }

    public final int hashCode() {
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleY, Float.hashCode(this.scaleX) * 31, 31);
        Offset.Companion companion = Offset.Companion;
        return Long.hashCode(this.pivot) + iM;
    }

    public final String toString() {
        return "Scale(scaleX=" + this.scaleX + ", scaleY=" + this.scaleY + ", pivot=" + Offset.m405toStringimpl(this.pivot) + ")";
    }

    private Scale(float f, float f2, long j) {
        this.scaleX = f;
        this.scaleY = f2;
        this.pivot = j;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Scale(float f, float f2, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 4) != 0) {
            Offset.Companion.getClass();
            j = Offset.Unspecified;
        }
        this(f, f2, j, null);
    }
}
