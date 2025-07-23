package com.android.compose.animation.scene;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Scale {
    public static final Companion Companion = new Companion(null);
    public static final Scale Default;
    public static final Scale Unspecified;
    public static final Scale Zero;
    public final long pivot;
    public final float scaleX;
    public final float scaleY;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return Float.compare(this.scaleX, scale.scaleX) == 0 && Float.compare(this.scaleY, scale.scaleY) == 0 && Offset.m396equalsimpl0(this.pivot, scale.pivot);
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleY, Float.hashCode(this.scaleX) * 31, 31);
        Offset.Companion companion = Offset.Companion;
        return Long.hashCode(this.pivot) + m;
    }

    public final String toString() {
        return "Scale(scaleX=" + this.scaleX + ", scaleY=" + this.scaleY + ", pivot=" + Offset.m403toStringimpl(this.pivot) + ")";
    }

    private Scale(float f, float f2, long j) {
        this.scaleX = f;
        this.scaleY = f2;
        this.pivot = j;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Scale(float r7, float r8, long r9, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r11 = r11 & 4
            if (r11 == 0) goto Lb
            androidx.compose.ui.geometry.Offset$Companion r9 = androidx.compose.ui.geometry.Offset.Companion
            r9.getClass()
            long r9 = androidx.compose.ui.geometry.Offset.Unspecified
        Lb:
            r3 = r9
            r5 = 0
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.Scale.<init>(float, float, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
