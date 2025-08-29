package androidx.compose.foundation;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class OverscrollConfiguration {
    public final PaddingValues drawPadding;
    public final long glowColor;

    public /* synthetic */ OverscrollConfiguration(long j, PaddingValues paddingValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, paddingValues);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!OverscrollConfiguration.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        OverscrollConfiguration overscrollConfiguration = (OverscrollConfiguration) obj;
        long j = overscrollConfiguration.glowColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.glowColor, j) && Intrinsics.areEqual(this.drawPadding, overscrollConfiguration.drawPadding);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.drawPadding.hashCode() + (Long.hashCode(this.glowColor) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("OverscrollConfiguration(glowColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.glowColor, ", drawPadding=", sb);
        sb.append(this.drawPadding);
        sb.append(')');
        return sb.toString();
    }

    private OverscrollConfiguration(long j, PaddingValues paddingValues) {
        this.glowColor = j;
        this.drawPadding = paddingValues;
    }

    public /* synthetic */ OverscrollConfiguration(long j, PaddingValues paddingValues, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ColorKt.Color(4284900966L) : j, (i & 2) != 0 ? PaddingKt.m122PaddingValuesYgX7TsA$default(0.0f, 3) : paddingValues, null);
    }
}
