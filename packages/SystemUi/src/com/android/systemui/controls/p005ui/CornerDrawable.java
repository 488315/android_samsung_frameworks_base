package com.android.systemui.controls.p005ui;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CornerDrawable extends DrawableWrapper {
    public final float cornerRadius;
    public final Path path;

    public CornerDrawable(Drawable drawable, float f) {
        super(drawable);
        this.cornerRadius = f;
        Path path = new Path();
        this.path = path;
        RectF rectF = new RectF(getBounds());
        path.reset();
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.clipPath(this.path);
        super.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setBounds(int i, int i2, int i3, int i4) {
        RectF rectF = new RectF(i, i2, i3, i4);
        this.path.reset();
        Path path = this.path;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        super.setBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setBounds(Rect rect) {
        RectF rectF = new RectF(rect);
        this.path.reset();
        Path path = this.path;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        super.setBounds(rect);
    }
}
