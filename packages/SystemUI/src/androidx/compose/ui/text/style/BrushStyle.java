package androidx.compose.ui.text.style;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ShaderBrush;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BrushStyle implements TextForegroundStyle {
    public final float alpha;
    public final ShaderBrush value;

    public BrushStyle(ShaderBrush shaderBrush, float f) {
        this.value = shaderBrush;
        this.alpha = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BrushStyle)) {
            return false;
        }
        BrushStyle brushStyle = (BrushStyle) obj;
        return Intrinsics.areEqual(this.value, brushStyle.value) && Float.compare(this.alpha, brushStyle.alpha) == 0;
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    public final float getAlpha() {
        return this.alpha;
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    public final Brush getBrush() {
        return this.value;
    }

    @Override // androidx.compose.ui.text.style.TextForegroundStyle
    /* renamed from: getColor-0d7_KjU, reason: not valid java name */
    public final long mo792getColor0d7_KjU() {
        Color.Companion.getClass();
        return Color.Unspecified;
    }

    public final int hashCode() {
        return Float.hashCode(this.alpha) + (this.value.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BrushStyle(value=");
        sb.append(this.value);
        sb.append(", alpha=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.alpha, ')');
    }
}
