package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.nio.ByteBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IconNormalizer {
    public final RectF mAdaptiveIconBounds;
    public float mAdaptiveIconScale;
    public final Bitmap mBitmap;
    public final Rect mBounds;
    public final Canvas mCanvas;
    public final boolean mEnableShapeDetection;
    public final float[] mLeftBorder;
    public final Matrix mMatrix;
    public final int mMaxSize;
    public final Paint mPaintMaskShape;
    public final Paint mPaintMaskShapeOutline;
    public final byte[] mPixels;
    public final float[] mRightBorder;
    public final Path mShapePath;

    public IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[i2 * i2];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new RectF();
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(EmergencyPhoneWidget.BG_COLOR);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = z;
    }

    public static void convertToConvexArray(int i, int i2, int i3, float[] fArr) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            float f2 = fArr[i5];
            if (f2 > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f3 = ((f2 - fArr[i4]) / (i5 - i4)) - f;
                    float f4 = i;
                    if (f3 * f4 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f4 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = ((i6 - i4) * f) + fArr[i4];
                }
                i4 = i5;
            }
        }
    }

    public static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        if (f / f3 > (f4 < 0.7853982f ? 0.6597222f : DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f4, 0.040449437f, 0.6510417f))) {
            return (float) Math.sqrt(r4 / r3);
        }
        return 1.0f;
    }

    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        int i2 = 0;
        drawable.setBounds(0, 0, i, i);
        Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect2 = new Rect();
        while (regionIterator.next(rect2)) {
            i2 += rect2.height() * rect2.width();
        }
        if (rectF != null) {
            float f = i;
            rectF.set(bounds.left / f, bounds.top / f, 1.0f - (bounds.right / f), 1.0f - (bounds.bottom / f));
        }
        drawable.setBounds(rect);
        float f2 = i2;
        return getScale(f2, f2, i * i);
    }

    public final boolean isShape(Path path) {
        Rect rect = this.mBounds;
        if (Math.abs((rect.width() / rect.height()) - 1.0f) > 0.05f) {
            return false;
        }
        Matrix matrix = this.mMatrix;
        matrix.reset();
        matrix.setScale(rect.width(), rect.height());
        matrix.postTranslate(rect.left, rect.top);
        Path path2 = this.mShapePath;
        path.transform(matrix, path2);
        Canvas canvas = this.mCanvas;
        canvas.drawPath(path2, this.mPaintMaskShape);
        canvas.drawPath(path2, this.mPaintMaskShapeOutline);
        byte[] bArr = this.mPixels;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        int i = rect.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - rect.right;
        int i5 = 0;
        while (i < rect.bottom) {
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < rect.right) {
                if ((bArr[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        return ((float) i5) / ((float) (rect.height() * rect.width())) < 0.005f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x004c, code lost:
    
        if (r4 <= r16.mMaxSize) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d4 A[Catch: all -> 0x012a, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0014, B:10:0x0020, B:11:0x0025, B:15:0x0029, B:19:0x0036, B:22:0x0058, B:26:0x0085, B:33:0x0094, B:36:0x009b, B:40:0x00ac, B:42:0x00b6, B:49:0x00c5, B:51:0x00d4, B:55:0x00e7, B:56:0x00df, B:59:0x00ea, B:61:0x00f6, B:64:0x010a, B:66:0x010e, B:68:0x0111, B:69:0x011a, B:74:0x003c, B:76:0x004a, B:79:0x0052, B:81:0x0056, B:82:0x004e), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f6 A[Catch: all -> 0x012a, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0014, B:10:0x0020, B:11:0x0025, B:15:0x0029, B:19:0x0036, B:22:0x0058, B:26:0x0085, B:33:0x0094, B:36:0x009b, B:40:0x00ac, B:42:0x00b6, B:49:0x00c5, B:51:0x00d4, B:55:0x00e7, B:56:0x00df, B:59:0x00ea, B:61:0x00f6, B:64:0x010a, B:66:0x010e, B:68:0x0111, B:69:0x011a, B:74:0x003c, B:76:0x004a, B:79:0x0052, B:81:0x0056, B:82:0x004e), top: B:3:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized float getScale(Drawable drawable, RectF rectF, Path path, boolean[] zArr) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (drawable instanceof AdaptiveIconDrawable) {
            if (this.mAdaptiveIconScale == 0.0f) {
                this.mAdaptiveIconScale = normalizeAdaptiveIcon(drawable, this.mMaxSize, this.mAdaptiveIconBounds);
            }
            if (rectF != null) {
                rectF.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            int i7 = this.mMaxSize;
            if (intrinsicWidth > i7 || intrinsicHeight > i7) {
                int max = Math.max(intrinsicWidth, intrinsicHeight);
                int i8 = this.mMaxSize;
                intrinsicWidth = (intrinsicWidth * i8) / max;
                intrinsicHeight = (i8 * intrinsicHeight) / max;
            }
            int i9 = 0;
            this.mBitmap.eraseColor(0);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(this.mCanvas);
            ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
            wrap.rewind();
            this.mBitmap.copyPixelsToBuffer(wrap);
            int i10 = this.mMaxSize;
            i = i10 + 1;
            int i11 = i10 - intrinsicWidth;
            i2 = 0;
            int i12 = 0;
            i3 = -1;
            i4 = -1;
            i5 = -1;
            while (i2 < intrinsicHeight) {
                int i13 = -1;
                int i14 = -1;
                for (int i15 = i9; i15 < intrinsicWidth; i15++) {
                    if ((this.mPixels[i12] & 255) > 40) {
                        if (i13 == -1) {
                            i13 = i15;
                        }
                        i14 = i15;
                    }
                    i12++;
                }
                i12 += i11;
                this.mLeftBorder[i2] = i13;
                this.mRightBorder[i2] = i14;
                if (i13 != -1) {
                    if (i3 == -1) {
                        i3 = i2;
                    }
                    int min = Math.min(i, i13);
                    i4 = Math.max(i4, i14);
                    i = min;
                    i5 = i2;
                }
                i2++;
                i9 = 0;
            }
            if (i3 != -1 && i4 != -1) {
                convertToConvexArray(1, i3, i5, this.mLeftBorder);
                convertToConvexArray(-1, i3, i5, this.mRightBorder);
                float f = 0.0f;
                for (i6 = 0; i6 < intrinsicHeight; i6++) {
                    float f2 = this.mLeftBorder[i6];
                    if (f2 > -1.0f) {
                        f = (this.mRightBorder[i6] - f2) + 1.0f + f;
                    }
                }
                Rect rect = this.mBounds;
                rect.left = i;
                rect.right = i4;
                rect.top = i3;
                rect.bottom = i5;
                if (rectF != null) {
                    float f3 = intrinsicWidth;
                    float f4 = intrinsicHeight;
                    rectF.set(i / f3, i3 / f4, 1.0f - (i4 / f3), 1.0f - (i5 / f4));
                }
                if (zArr != null && this.mEnableShapeDetection && zArr.length > 0) {
                    zArr[0] = isShape(path);
                }
                return getScale(f, ((i4 + 1) - i) * ((i5 + 1) - i3), intrinsicWidth * intrinsicHeight);
            }
            return 1.0f;
        }
        intrinsicWidth = this.mMaxSize;
        if (intrinsicHeight <= 0 || intrinsicHeight > this.mMaxSize) {
            intrinsicHeight = this.mMaxSize;
        }
        int i92 = 0;
        this.mBitmap.eraseColor(0);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(this.mCanvas);
        ByteBuffer wrap2 = ByteBuffer.wrap(this.mPixels);
        wrap2.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap2);
        int i102 = this.mMaxSize;
        i = i102 + 1;
        int i112 = i102 - intrinsicWidth;
        i2 = 0;
        int i122 = 0;
        i3 = -1;
        i4 = -1;
        i5 = -1;
        while (i2 < intrinsicHeight) {
        }
        if (i3 != -1) {
            convertToConvexArray(1, i3, i5, this.mLeftBorder);
            convertToConvexArray(-1, i3, i5, this.mRightBorder);
            float f5 = 0.0f;
            while (i6 < intrinsicHeight) {
            }
            Rect rect2 = this.mBounds;
            rect2.left = i;
            rect2.right = i4;
            rect2.top = i3;
            rect2.bottom = i5;
            if (rectF != null) {
            }
            if (zArr != null) {
                zArr[0] = isShape(path);
            }
            return getScale(f5, ((i4 + 1) - i) * ((i5 + 1) - i3), intrinsicWidth * intrinsicHeight);
        }
        return 1.0f;
    }
}
