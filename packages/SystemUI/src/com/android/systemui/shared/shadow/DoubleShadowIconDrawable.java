package com.android.systemui.shared.shadow;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DoubleShadowIconDrawable extends Drawable {
    public final int mCanvasSize;
    public final RenderNode mDoubleShadowNode;
    public final InsetDrawable mIconDrawable;

    public DoubleShadowIconDrawable(DoubleShadowTextHelper.ShadowInfo shadowInfo, DoubleShadowTextHelper.ShadowInfo shadowInfo2, Drawable drawable, int i, int i2) {
        int i3 = (i2 * 2) + i;
        this.mCanvasSize = i3;
        setBounds(0, 0, i3, i3);
        InsetDrawable insetDrawable = new InsetDrawable(drawable, i2);
        this.mIconDrawable = insetDrawable;
        insetDrawable.setBounds(0, 0, i3, i3);
        RenderNode renderNode = new RenderNode("DoubleShadowNode");
        renderNode.setPosition(0, 0, i3, i3);
        float f = shadowInfo2.blur;
        int argb = Color.argb(shadowInfo2.alpha, 0.0f, 0.0f, 0.0f);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(argb, mode);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        RenderEffect createColorFilterEffect = RenderEffect.createColorFilterEffect(porterDuffColorFilter, RenderEffect.createOffsetEffect(shadowInfo2.offsetX, shadowInfo2.offsetY, RenderEffect.createBlurEffect(f, f, tileMode)));
        float f2 = shadowInfo.blur;
        renderNode.setRenderEffect(RenderEffect.createBlendModeEffect(createColorFilterEffect, RenderEffect.createColorFilterEffect(new PorterDuffColorFilter(Color.argb(shadowInfo.alpha, 0.0f, 0.0f, 0.0f), mode), RenderEffect.createOffsetEffect(shadowInfo.offsetX, shadowInfo.offsetY, RenderEffect.createBlurEffect(f2, f2, tileMode))), BlendMode.DST_ATOP));
        this.mDoubleShadowNode = renderNode;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            if (!this.mDoubleShadowNode.hasDisplayList()) {
                this.mIconDrawable.draw(this.mDoubleShadowNode.beginRecording());
                this.mDoubleShadowNode.endRecording();
            }
            canvas.drawRenderNode(this.mDoubleShadowNode);
        }
        this.mIconDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mCanvasSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mCanvasSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mIconDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mIconDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        this.mIconDrawable.setTint(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.mIconDrawable.setTintList(colorStateList);
    }
}
