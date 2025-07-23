package androidx.compose.ui.text.android.style;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShadowSpan extends CharacterStyle {
    public final int color;
    public final float offsetX;
    public final float offsetY;
    public final float radius;

    public ShadowSpan(int i, float f, float f2, float f3) {
        this.color = i;
        this.offsetX = f;
        this.offsetY = f2;
        this.radius = f3;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        textPaint.setShadowLayer(this.radius, this.offsetX, this.offsetY, this.color);
    }
}
