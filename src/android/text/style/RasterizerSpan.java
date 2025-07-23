package android.text.style;

import android.graphics.Rasterizer;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class RasterizerSpan extends CharacterStyle implements UpdateAppearance {
    private Rasterizer mRasterizer;

    public RasterizerSpan(Rasterizer rasterizer) {
        this.mRasterizer = rasterizer;
    }

    public Rasterizer getRasterizer() {
        return this.mRasterizer;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setRasterizer(this.mRasterizer);
    }
}
