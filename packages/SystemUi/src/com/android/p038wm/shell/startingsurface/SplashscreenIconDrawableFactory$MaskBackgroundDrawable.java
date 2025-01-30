package com.android.p038wm.shell.startingsurface;

import android.R;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.PathParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SplashscreenIconDrawableFactory$MaskBackgroundDrawable extends Drawable {
    public static Path sMask;
    public final Paint mBackgroundPaint;
    public final Matrix mMaskMatrix;
    public final Path mMaskScaleOnly;

    public SplashscreenIconDrawableFactory$MaskBackgroundDrawable(int i) {
        sMask = PathParser.createPathFromPathData(Resources.getSystem().getString(R.string.dump_heap_ready_text));
        this.mMaskScaleOnly = new Path(new Path(sMask));
        this.mMaskMatrix = new Matrix();
        if (i == 0) {
            this.mBackgroundPaint = null;
            return;
        }
        Paint paint = new Paint(7);
        this.mBackgroundPaint = paint;
        paint.setColor(i);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.clipPath(this.mMaskScaleOnly);
        Paint paint = this.mBackgroundPaint;
        if (paint != null) {
            canvas.drawPath(this.mMaskScaleOnly, paint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 1;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        updateLayerBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Paint paint = this.mBackgroundPaint;
        if (paint != null) {
            paint.setAlpha(i);
        }
    }

    public void updateLayerBounds(Rect rect) {
        this.mMaskMatrix.setScale(rect.width() / 100.0f, rect.height() / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskScaleOnly);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
