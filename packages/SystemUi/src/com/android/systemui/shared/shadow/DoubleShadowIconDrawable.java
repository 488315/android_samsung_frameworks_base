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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        float f = shadowInfo2.offsetY;
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.argb(shadowInfo2.alpha, 0.0f, 0.0f, 0.0f), PorterDuff.Mode.MULTIPLY);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        float f2 = shadowInfo2.blur;
        RenderEffect createColorFilterEffect = RenderEffect.createColorFilterEffect(porterDuffColorFilter, RenderEffect.createOffsetEffect(shadowInfo2.offsetX, f, RenderEffect.createBlurEffect(f2, f2, tileMode)));
        float f3 = shadowInfo.offsetY;
        PorterDuffColorFilter porterDuffColorFilter2 = new PorterDuffColorFilter(Color.argb(shadowInfo.alpha, 0.0f, 0.0f, 0.0f), PorterDuff.Mode.MULTIPLY);
        Shader.TileMode tileMode2 = Shader.TileMode.CLAMP;
        float f4 = shadowInfo.blur;
        renderNode.setRenderEffect(RenderEffect.createBlendModeEffect(createColorFilterEffect, RenderEffect.createColorFilterEffect(porterDuffColorFilter2, RenderEffect.createOffsetEffect(shadowInfo.offsetX, f3, RenderEffect.createBlurEffect(f4, f4, tileMode2))), BlendMode.DST_ATOP));
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
