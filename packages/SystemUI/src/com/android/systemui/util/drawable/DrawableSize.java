package com.android.systemui.util.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DrawableSize {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "SysUiDrawableSize";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean hasComplicatedLayers(Drawable drawable) {
            if (!(drawable instanceof LayerDrawable)) {
                return false;
            }
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            if (layerDrawable.getNumberOfLayers() == 1) {
                return false;
            }
            Class<?> cls = layerDrawable.getDrawable(0).getClass();
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 1; i < numberOfLayers; i++) {
                Drawable drawable2 = layerDrawable.getDrawable(i);
                if (!drawable2.getClass().equals(cls) || isComplicatedBitmap(drawable2)) {
                    return true;
                }
            }
            return false;
        }

        private final boolean isAnimated(Drawable drawable) {
            return (drawable instanceof Animatable) || (drawable instanceof Animatable2) || (drawable instanceof AnimatedImageDrawable) || (drawable instanceof AnimatedRotateDrawable) || (drawable instanceof AnimatedStateListDrawable) || (drawable instanceof AnimatedVectorDrawable);
        }

        private final boolean isComplicatedBitmap(Drawable drawable) {
            return drawable.isStateful() || isAnimated(drawable) || hasComplicatedLayers(drawable);
        }

        public final Drawable downscaleToSize(Resources resources, Drawable drawable, int i, int i2) {
            Bitmap.Config config;
            Bitmap bitmap;
            Bitmap bitmap2;
            Bitmap bitmap3;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("DrawableSize#downscaleToSize");
            }
            try {
                BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                int intrinsicWidth = (bitmapDrawable == null || (bitmap3 = bitmapDrawable.getBitmap()) == null) ? drawable.getIntrinsicWidth() : bitmap3.getWidth();
                BitmapDrawable bitmapDrawable2 = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                int intrinsicHeight = (bitmapDrawable2 == null || (bitmap2 = bitmapDrawable2.getBitmap()) == null) ? drawable.getIntrinsicHeight() : bitmap2.getHeight();
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } else if (intrinsicWidth < i && intrinsicHeight < i2) {
                    if (Log.isLoggable(DrawableSize.TAG, 3)) {
                        Log.d(DrawableSize.TAG, "Not resizing " + intrinsicWidth + " x " + intrinsicHeight + " to " + i + " x " + i2);
                    }
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                        return drawable;
                    }
                } else {
                    if (!DrawableSize.Companion.isComplicatedBitmap(drawable)) {
                        float f = intrinsicWidth;
                        float f2 = intrinsicHeight;
                        float min = Math.min(i2 / f2, i / f);
                        int i3 = (int) (f * min);
                        int i4 = (int) (f2 * min);
                        if (i3 > 0 && i4 > 0) {
                            if (Log.isLoggable(DrawableSize.TAG, 3)) {
                                Log.d(DrawableSize.TAG, "Resizing large drawable (" + drawable.getClass().getSimpleName() + ") from " + intrinsicWidth + " x " + intrinsicHeight + " to " + i3 + " x " + i4);
                            }
                            BitmapDrawable bitmapDrawable3 = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                            if (bitmapDrawable3 == null || (bitmap = bitmapDrawable3.getBitmap()) == null || (config = bitmap.getConfig()) == null) {
                                config = Bitmap.Config.ARGB_8888;
                            }
                            Bitmap createBitmap = Bitmap.createBitmap(i3, i4, config);
                            Canvas canvas = new Canvas(createBitmap);
                            Rect bounds = drawable.getBounds();
                            drawable.setBounds(0, 0, i3, i4);
                            drawable.draw(canvas);
                            drawable.setBounds(bounds);
                            BitmapDrawable bitmapDrawable4 = new BitmapDrawable(resources, createBitmap);
                            if (isEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                            return bitmapDrawable4;
                        }
                        Log.w(DrawableSize.TAG, "Attempted to resize " + drawable.getClass().getSimpleName() + " from " + intrinsicWidth + " x " + intrinsicHeight + " to invalid " + i3 + " x " + i4 + ".");
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        return drawable;
                    }
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                        return drawable;
                    }
                }
                return drawable;
            } catch (Throwable th) {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }

        private Companion() {
        }
    }

    public static final Drawable downscaleToSize(Resources resources, Drawable drawable, int i, int i2) {
        return Companion.downscaleToSize(resources, drawable, i, i2);
    }
}
