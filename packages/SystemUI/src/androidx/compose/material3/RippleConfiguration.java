package androidx.compose.material3;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.material.ripple.RippleAlpha;
import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RippleConfiguration {
    public final long color;
    public final RippleAlpha rippleAlpha;

    public /* synthetic */ RippleConfiguration(long j, RippleAlpha rippleAlpha, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, rippleAlpha);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleConfiguration)) {
            return false;
        }
        RippleConfiguration rippleConfiguration = (RippleConfiguration) obj;
        long j = rippleConfiguration.color;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.color, j) && Intrinsics.areEqual(this.rippleAlpha, rippleConfiguration.rippleAlpha);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = Long.hashCode(this.color) * 31;
        RippleAlpha rippleAlpha = this.rippleAlpha;
        return hashCode + (rippleAlpha != null ? rippleAlpha.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RippleConfiguration(color=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.color, ", rippleAlpha=", sb);
        sb.append(this.rippleAlpha);
        sb.append(')');
        return sb.toString();
    }

    private RippleConfiguration(long j, RippleAlpha rippleAlpha) {
        this.color = j;
        this.rippleAlpha = rippleAlpha;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RippleConfiguration(long r1, androidx.compose.material.ripple.RippleAlpha r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r0 = this;
            r5 = r4 & 1
            if (r5 == 0) goto Lb
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Unspecified
        Lb:
            r4 = r4 & 2
            r5 = 0
            if (r4 == 0) goto L11
            r3 = r5
        L11:
            r0.<init>(r1, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.RippleConfiguration.<init>(long, androidx.compose.material.ripple.RippleAlpha, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
