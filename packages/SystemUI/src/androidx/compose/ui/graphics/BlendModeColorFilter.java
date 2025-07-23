package androidx.compose.ui.graphics;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BlendModeColorFilter extends ColorFilter {
    public final int blendMode;
    public final long color;

    public /* synthetic */ BlendModeColorFilter(long j, int i, android.graphics.ColorFilter colorFilter, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, i, colorFilter);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlendModeColorFilter)) {
            return false;
        }
        BlendModeColorFilter blendModeColorFilter = (BlendModeColorFilter) obj;
        long j = blendModeColorFilter.color;
        Color.Companion companion = Color.Companion;
        if (!ULong.m3427equalsimpl0(this.color, j)) {
            return false;
        }
        int i = blendModeColorFilter.blendMode;
        BlendMode.Companion companion2 = BlendMode.Companion;
        return this.blendMode == i;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = Long.hashCode(this.color) * 31;
        BlendMode.Companion companion2 = BlendMode.Companion;
        return Integer.hashCode(this.blendMode) + hashCode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BlendModeColorFilter(color=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.color, ", blendMode=", sb);
        sb.append((Object) BlendMode.m448toStringimpl(this.blendMode));
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ BlendModeColorFilter(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private BlendModeColorFilter(long j, int i) {
        this(j, i, new android.graphics.BlendModeColorFilter(ColorKt.m467toArgb8_81llA(j), AndroidBlendMode_androidKt.m422toAndroidBlendModes9anfk8(i)), null);
        BlendModeColorFilterHelper.INSTANCE.getClass();
    }

    private BlendModeColorFilter(long j, int i, android.graphics.ColorFilter colorFilter) {
        super(colorFilter);
        this.color = j;
        this.blendMode = i;
    }
}
