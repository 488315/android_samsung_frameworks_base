package androidx.compose.ui.graphics;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Shadow {
    public static final Companion Companion = new Companion(null);
    public static final Shadow None = new Shadow(0, 0, 0.0f, 7, null);
    public final float blurRadius;
    public final long color;
    public final long offset;

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
        return ULong.m3446equalsimpl0(this.color, j) && Offset.m398equalsimpl0(this.offset, shadow.offset) && this.blurRadius == shadow.blurRadius;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int iHashCode = Long.hashCode(this.color) * 31;
        Offset.Companion companion2 = Offset.Companion;
        return Float.hashCode(this.blurRadius) + MoveResult$$ExternalSyntheticOutline0.m(iHashCode, 31, this.offset);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Shadow(color=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.color, ", offset=", sb);
        sb.append((Object) Offset.m405toStringimpl(this.offset));
        sb.append(", blurRadius=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.blurRadius, ')');
    }

    private Shadow(long j, long j2, float f) {
        this.color = j;
        this.offset = j2;
        this.blurRadius = f;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Shadow(long j, long j2, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long jColor = (i & 1) != 0 ? ColorKt.Color(4278190080L) : j;
        if ((i & 2) != 0) {
            Offset.Companion.getClass();
            j2 = 0;
        }
        this(jColor, j2, (i & 4) != 0 ? 0.0f : f, null);
    }
}
