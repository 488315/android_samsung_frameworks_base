package androidx.compose.ui.graphics;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Shadow {
    public static final Companion Companion = new Companion(null);
    public static final Shadow None = new Shadow(0, 0, 0.0f, 7, null);
    public final float blurRadius;
    public final long color;
    public final long offset;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ Shadow(long j, long j2, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, f);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Shadow)) {
            return false;
        }
        Shadow shadow = (Shadow) obj;
        long j = shadow.color;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.color, j) && Offset.m396equalsimpl0(this.offset, shadow.offset) && this.blurRadius == shadow.blurRadius;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = Long.hashCode(this.color) * 31;
        Offset.Companion companion2 = Offset.Companion;
        return Float.hashCode(this.blurRadius) + MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.offset);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Shadow(color=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.color, ", offset=", sb);
        sb.append((Object) Offset.m403toStringimpl(this.offset));
        sb.append(", blurRadius=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.blurRadius, ')');
    }

    private Shadow(long j, long j2, float f) {
        this.color = j;
        this.offset = j2;
        this.blurRadius = f;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Shadow(long r8, long r10, float r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 1
            if (r14 == 0) goto Ld
            r8 = 4278190080(0xff000000, double:2.113706745E-314)
            long r8 = androidx.compose.ui.graphics.ColorKt.Color(r8)
        Ld:
            r1 = r8
            r8 = r13 & 2
            if (r8 == 0) goto L19
            androidx.compose.ui.geometry.Offset$Companion r8 = androidx.compose.ui.geometry.Offset.Companion
            r8.getClass()
            r10 = 0
        L19:
            r3 = r10
            r8 = r13 & 4
            if (r8 == 0) goto L1f
            r12 = 0
        L1f:
            r5 = r12
            r6 = 0
            r0 = r7
            r0.<init>(r1, r3, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.Shadow.<init>(long, long, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
