package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ColorStyle implements TextForegroundStyle {
    public final long value;

    public /* synthetic */ ColorStyle(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorStyle)) {
            return false;
        }
        long j = ((ColorStyle) obj).value;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.value, j);
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    public final float getAlpha() {
        return Color.m457getAlphaimpl(this.value);
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    public final Brush getBrush() {
        return null;
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    /* renamed from: getColor-0d7_KjU */
    public final long mo792getColor0d7_KjU() {
        return this.value;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "ColorStyle(value=" + ((Object) Color.m462toStringimpl(this.value)) + ')';
    }

    private ColorStyle(long j) {
        this.value = j;
        if (j != 16) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("ColorStyle value must be specified, use TextForegroundStyle.Unspecified instead.");
    }
}
