package com.android.systemui.statusbar.phone;

import android.content.Context;
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
import com.android.systemui.R;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DoubleShadowStatusBarIconDrawable extends Drawable {
    public boolean drawShadowOnly;
    public final int iconHeight;
    public final int iconWidth;
    public final RenderNode mDoubleShadowNode;
    public final InsetDrawable mIconDrawable;

    public DoubleShadowStatusBarIconDrawable(Drawable drawable, Context context, int i, int i2) {
        this.iconWidth = i;
        this.iconHeight = i2;
        float dimension = context.getResources().getDimension(R.dimen.status_bar_icon_shadow_radius);
        DoubleShadowTextHelper.ShadowInfo shadowInfo = new DoubleShadowTextHelper.ShadowInfo(dimension, 0.0f, 0.0f, 0.2f);
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = new DoubleShadowTextHelper.ShadowInfo(dimension, 0.0f, 0.0f, 0.0f);
        setBounds(0, 0, i, i2);
        InsetDrawable insetDrawable = new InsetDrawable(drawable, 0);
        this.mIconDrawable = insetDrawable;
        insetDrawable.setBounds(0, 0, i, i2);
        RenderNode renderNode = new RenderNode("DoubleShadowNode");
        renderNode.setPosition(0, 0, i, i2);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.argb(shadowInfo2.alpha, 0.0f, 0.0f, 0.0f), PorterDuff.Mode.MULTIPLY);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        float f = shadowInfo2.blur;
        RenderEffect createColorFilterEffect = RenderEffect.createColorFilterEffect(porterDuffColorFilter, RenderEffect.createOffsetEffect(shadowInfo2.offsetX, shadowInfo2.offsetY, RenderEffect.createBlurEffect(f, f, tileMode)));
        PorterDuffColorFilter porterDuffColorFilter2 = new PorterDuffColorFilter(Color.argb(shadowInfo.alpha, 0.0f, 0.0f, 0.0f), PorterDuff.Mode.MULTIPLY);
        Shader.TileMode tileMode2 = Shader.TileMode.CLAMP;
        float f2 = shadowInfo.blur;
        renderNode.setRenderEffect(RenderEffect.createBlendModeEffect(createColorFilterEffect, RenderEffect.createColorFilterEffect(porterDuffColorFilter2, RenderEffect.createOffsetEffect(shadowInfo.offsetX, shadowInfo.offsetY, RenderEffect.createBlurEffect(f2, f2, tileMode2))), BlendMode.DST_ATOP));
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
        if (this.drawShadowOnly) {
            return;
        }
        this.mIconDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.iconHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.iconWidth;
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
