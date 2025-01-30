package com.android.wm.shell.startingsurface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplashscreenIconDrawableFactory$ImmobileIconDrawable extends Drawable {
    public Bitmap mIconBitmap;
    public final Matrix mMatrix;
    public final Paint mPaint = new Paint(7);

    public SplashscreenIconDrawableFactory$ImmobileIconDrawable(final Drawable drawable, final int i, final int i2, boolean z, Handler handler) {
        Matrix matrix = new Matrix();
        this.mMatrix = matrix;
        if (z) {
            final int i3 = 0;
            handler.post(new Runnable(this) { // from class: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$ImmobileIconDrawable$$ExternalSyntheticLambda0
                public final /* synthetic */ SplashscreenIconDrawableFactory$ImmobileIconDrawable f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            this.f$0.preDrawIcon(i2, drawable);
                            break;
                        default:
                            this.f$0.preDrawIcon(i2, drawable);
                            break;
                    }
                }
            });
        } else {
            float f = i2 / i;
            matrix.setScale(f, f);
            final int i4 = 1;
            handler.post(new Runnable(this) { // from class: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$ImmobileIconDrawable$$ExternalSyntheticLambda0
                public final /* synthetic */ SplashscreenIconDrawableFactory$ImmobileIconDrawable f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            this.f$0.preDrawIcon(i, drawable);
                            break;
                        default:
                            this.f$0.preDrawIcon(i, drawable);
                            break;
                    }
                }
            });
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        synchronized (this.mPaint) {
            Bitmap bitmap = this.mIconBitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, this.mMatrix, this.mPaint);
            } else {
                invalidateSelf();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 1;
    }

    public final void preDrawIcon(int i, Drawable drawable) {
        synchronized (this.mPaint) {
            Trace.traceBegin(32L, "preDrawIcon");
            this.mIconBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.mIconBitmap);
            drawable.setBounds(0, 0, i, i);
            drawable.draw(canvas);
            Trace.traceEnd(32L);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
