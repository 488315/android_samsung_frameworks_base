package com.android.server.wm;

import android.graphics.BLASTBufferQueue;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Watermark {
    public final BLASTBufferQueue mBlastBufferQueue;
    public final int mDeltaX;
    public final int mDeltaY;
    public boolean mDrawNeeded;
    public int mLastDH;
    public int mLastDW;
    public final Surface mSurface;
    public final SurfaceControl mSurfaceControl;
    public final String mText;
    public final int mTextHeight;
    public final Paint mTextPaint;
    public final int mTextWidth;

    public Watermark(DisplayContent displayContent, DisplayMetrics displayMetrics, String[] strArr, SurfaceControl.Transaction transaction) {
        StringBuilder sb = new StringBuilder(32);
        int length = strArr[0].length() & (-2);
        for (int i = 0; i < length; i += 2) {
            char charAt = strArr[0].charAt(i);
            char charAt2 = strArr[0].charAt(i + 1);
            sb.append((char) (255 - ((((charAt < 'a' || charAt > 'f') ? (charAt < 'A' || charAt > 'F') ? charAt - '0' : charAt - '7' : charAt - 'W') * 16) + ((charAt2 < 'a' || charAt2 > 'f') ? (charAt2 < 'A' || charAt2 > 'F') ? charAt2 - '0' : charAt2 - '7' : charAt2 - 'W'))));
        }
        String sb2 = sb.toString();
        this.mText = sb2;
        int propertyInt = WindowManagerService.getPropertyInt(strArr, 1, 1, 20, displayMetrics);
        Paint paint = new Paint(1);
        this.mTextPaint = paint;
        paint.setTextSize(propertyInt);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 1));
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int measureText = (int) paint.measureText(sb2);
        this.mTextWidth = measureText;
        int i2 = fontMetricsInt.descent - fontMetricsInt.ascent;
        this.mTextHeight = i2;
        this.mDeltaX = WindowManagerService.getPropertyInt(strArr, 2, 0, measureText * 2, displayMetrics);
        this.mDeltaY = WindowManagerService.getPropertyInt(strArr, 3, 0, i2 * 3, displayMetrics);
        int propertyInt2 = WindowManagerService.getPropertyInt(strArr, 4, 0, -1342177280, displayMetrics);
        int propertyInt3 = WindowManagerService.getPropertyInt(strArr, 5, 0, 1627389951, displayMetrics);
        int propertyInt4 = WindowManagerService.getPropertyInt(strArr, 6, 0, 7, displayMetrics);
        int propertyInt5 = WindowManagerService.getPropertyInt(strArr, 8, 0, 0, displayMetrics);
        int propertyInt6 = WindowManagerService.getPropertyInt(strArr, 9, 0, 0, displayMetrics);
        paint.setColor(propertyInt3);
        paint.setShadowLayer(propertyInt4, propertyInt5, propertyInt6, propertyInt2);
        SurfaceControl surfaceControl = null;
        try {
            surfaceControl = displayContent.makeOverlay().setName("WatermarkSurface").setBLASTLayer().setFormat(-3).setCallsite("WatermarkSurface").build();
            transaction.setLayer(surfaceControl, 1000000).setPosition(surfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE).show(surfaceControl);
            InputMonitor.setTrustedOverlayInputInfo(surfaceControl, transaction, displayContent.mDisplayId, "WatermarkSurface");
        } catch (Surface.OutOfResourcesException unused) {
        }
        SurfaceControl surfaceControl2 = surfaceControl;
        this.mSurfaceControl = surfaceControl2;
        BLASTBufferQueue bLASTBufferQueue = new BLASTBufferQueue("WatermarkSurface", surfaceControl2, 1, 1, 1);
        this.mBlastBufferQueue = bLASTBufferQueue;
        this.mSurface = bLASTBufferQueue.createSurface();
    }

    public final void drawIfNeeded() {
        if (this.mDrawNeeded) {
            int i = this.mLastDW;
            int i2 = this.mLastDH;
            this.mDrawNeeded = false;
            this.mBlastBufferQueue.update(this.mSurfaceControl, i, i2, 1);
            Canvas canvas = null;
            try {
                canvas = this.mSurface.lockCanvas(null);
            } catch (Surface.OutOfResourcesException | IllegalArgumentException unused) {
            }
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                int i3 = this.mTextWidth;
                int i4 = this.mDeltaX;
                int i5 = (i + i3) - (((i + i3) / i4) * i4);
                int i6 = i4 / 4;
                if (i5 < i6 || i5 > i4 - i6) {
                    i4 += i4 / 3;
                }
                int i7 = this.mTextHeight;
                int i8 = -i7;
                int i9 = -i3;
                while (i8 < i2 + i7) {
                    canvas.drawText(this.mText, i9, i8, this.mTextPaint);
                    i9 += i4;
                    if (i9 >= i) {
                        i9 -= i + i3;
                        i8 += this.mDeltaY;
                    }
                }
                this.mSurface.unlockCanvasAndPost(canvas);
            }
        }
    }
}
