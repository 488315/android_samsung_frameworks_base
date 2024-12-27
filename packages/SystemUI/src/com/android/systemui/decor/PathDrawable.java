package com.android.systemui.decor;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PathDrawable extends Drawable {
    public final int height;
    public final Paint paint;
    public final Path path;
    public final float scaleX;
    public final float scaleY;
    public final int width;

    public /* synthetic */ PathDrawable(Path path, int i, int i2, float f, float f2, Paint paint, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(path, i, i2, (i3 & 8) != 0 ? 1.0f : f, (i3 & 16) != 0 ? 1.0f : f2, paint);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        float f = this.scaleX;
        if (f != 1.0f || this.scaleY != 1.0f) {
            canvas.scale(f, this.scaleY);
        }
        canvas.drawPath(this.path, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.height;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.width;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -1;
    }

    public PathDrawable(Path path, int i, int i2, float f, float f2, Paint paint) {
        this.path = path;
        this.width = i;
        this.height = i2;
        this.scaleX = f;
        this.scaleY = f2;
        this.paint = paint;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
