package com.android.wm.shell.startingsurface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;
import java.io.Closeable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplashscreenIconDrawableFactory$ImmobileIconDrawable extends Drawable implements Closeable {
    public static final /* synthetic */ int $r8$clinit = 0;
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
                            SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable = this.f$0;
                            Drawable drawable2 = drawable;
                            int i4 = i2;
                            int i5 = SplashscreenIconDrawableFactory$ImmobileIconDrawable.$r8$clinit;
                            splashscreenIconDrawableFactory$ImmobileIconDrawable.preDrawIcon(drawable2, i4);
                            break;
                        default:
                            SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable2 = this.f$0;
                            Drawable drawable3 = drawable;
                            int i6 = i2;
                            int i7 = SplashscreenIconDrawableFactory$ImmobileIconDrawable.$r8$clinit;
                            splashscreenIconDrawableFactory$ImmobileIconDrawable2.preDrawIcon(drawable3, i6);
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
                            SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable = this.f$0;
                            Drawable drawable2 = drawable;
                            int i42 = i;
                            int i5 = SplashscreenIconDrawableFactory$ImmobileIconDrawable.$r8$clinit;
                            splashscreenIconDrawableFactory$ImmobileIconDrawable.preDrawIcon(drawable2, i42);
                            break;
                        default:
                            SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable2 = this.f$0;
                            Drawable drawable3 = drawable;
                            int i6 = i;
                            int i7 = SplashscreenIconDrawableFactory$ImmobileIconDrawable.$r8$clinit;
                            splashscreenIconDrawableFactory$ImmobileIconDrawable2.preDrawIcon(drawable3, i6);
                            break;
                    }
                }
            });
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        synchronized (this.mPaint) {
            try {
                Bitmap bitmap = this.mIconBitmap;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.mIconBitmap = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        synchronized (this.mPaint) {
            try {
                Bitmap bitmap = this.mIconBitmap;
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, this.mMatrix, this.mPaint);
                } else {
                    invalidateSelf();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 1;
    }

    public final void preDrawIcon(Drawable drawable, int i) {
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
