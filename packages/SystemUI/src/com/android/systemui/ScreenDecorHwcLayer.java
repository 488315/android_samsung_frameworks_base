package com.android.systemui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;
import android.view.DisplayCutout;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ScreenDecorHwcLayer extends DisplayCutoutBaseView {
    public final int bgColor;
    public final Paint clearPaint;
    public int color;
    public final ColorFilter cornerBgFilter;
    public ColorFilter cornerFilter;
    public final boolean debug;
    public boolean debugTransparentRegion;
    public final Paint debugTransparentRegionPaint;
    public boolean hasBottomRoundedCorner;
    public boolean hasTopRoundedCorner;
    public int roundedCornerBottomSize;
    public Drawable roundedCornerDrawableBottom;
    public Drawable roundedCornerDrawableTop;
    public int roundedCornerTopSize;
    public final Rect tempRect;
    public final Rect transparentRect;
    public final boolean useInvertedAlphaColor;

    public ScreenDecorHwcLayer(Context context, DisplayDecorationSupport displayDecorationSupport, boolean z) {
        super(context);
        this.debug = z;
        this.color = -16777216;
        this.transparentRect = new Rect();
        this.tempRect = new Rect();
        this.debugTransparentRegion = true;
        if (displayDecorationSupport.format != 56) {
            throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Attempting to use unsupported mode ", PixelFormat.formatToString(displayDecorationSupport.format)));
        }
        if (z) {
            this.color = -16711936;
            this.paint.setColor(-16711936);
            this.bgColor = 0;
            this.useInvertedAlphaColor = false;
            Paint paint = new Paint();
            paint.setColor(788594432);
            paint.setStyle(Paint.Style.FILL);
            this.debugTransparentRegionPaint = paint;
        } else {
            boolean z2 = displayDecorationSupport.alphaInterpretation == 0;
            this.useInvertedAlphaColor = z2;
            if (z2) {
                this.color = 0;
                this.paint.setColor(0);
                this.bgColor = -16777216;
            } else {
                this.color = -16777216;
                this.paint.setColor(-16777216);
                this.bgColor = 0;
            }
            this.debugTransparentRegionPaint = null;
        }
        this.cornerFilter = new PorterDuffColorFilter(this.color, PorterDuff.Mode.SRC_IN);
        this.cornerBgFilter = new PorterDuffColorFilter(this.bgColor, PorterDuff.Mode.SRC_OUT);
        Paint paint2 = new Paint();
        this.clearPaint = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public final void calculateTransparentRect() {
        boolean z;
        boolean z2;
        this.transparentRect.set(0, 0, getWidth(), getHeight());
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            if (!displayCutout.getBoundingRectLeft().isEmpty()) {
                Rect rect = this.transparentRect;
                int i = displayCutout.getBoundingRectLeft().right;
                int i2 = this.transparentRect.left;
                if (i < i2) {
                    i = i2;
                }
                rect.left = i;
            }
            if (!displayCutout.getBoundingRectTop().isEmpty()) {
                Rect rect2 = this.transparentRect;
                int i3 = displayCutout.getBoundingRectTop().bottom;
                int i4 = this.transparentRect.top;
                if (i3 < i4) {
                    i3 = i4;
                }
                rect2.top = i3;
            }
            if (!displayCutout.getBoundingRectRight().isEmpty()) {
                Rect rect3 = this.transparentRect;
                int i5 = displayCutout.getBoundingRectRight().left;
                int i6 = this.transparentRect.right;
                if (i5 > i6) {
                    i5 = i6;
                }
                rect3.right = i5;
            }
            if (!displayCutout.getBoundingRectBottom().isEmpty()) {
                Rect rect4 = this.transparentRect;
                int i7 = displayCutout.getBoundingRectBottom().top;
                int i8 = this.transparentRect.bottom;
                if (i7 > i8) {
                    i7 = i8;
                }
                rect4.bottom = i7;
            }
        }
        if (!this.protectionRect.isEmpty()) {
            float centerX = this.protectionRect.centerX();
            float centerY = this.protectionRect.centerY();
            RectF rectF = this.protectionRect;
            float f = centerX - rectF.left;
            float f2 = this.cameraProtectionProgress;
            float f3 = f * f2;
            float f4 = (centerY - rectF.top) * f2;
            this.tempRect.set((int) Math.floor(centerX - f3), (int) Math.floor(centerY - f4), (int) Math.ceil(centerX + f3), (int) Math.ceil(centerY + f4));
            Rect rect5 = this.tempRect;
            int i9 = rect5.left;
            int i10 = rect5.top;
            int width = getWidth() - this.tempRect.right;
            int height = getHeight() - this.tempRect.bottom;
            int[] iArr = {i10, width, height};
            int i11 = i9;
            for (int i12 = 0; i12 < 3; i12++) {
                i11 = Math.min(i11, iArr[i12]);
            }
            if (i11 == i9) {
                Rect rect6 = this.transparentRect;
                int i13 = this.tempRect.right;
                int i14 = rect6.left;
                if (i13 < i14) {
                    i13 = i14;
                }
                rect6.left = i13;
            } else if (i11 == i10) {
                Rect rect7 = this.transparentRect;
                int i15 = this.tempRect.bottom;
                int i16 = rect7.top;
                if (i15 < i16) {
                    i15 = i16;
                }
                rect7.top = i15;
            } else if (i11 == width) {
                Rect rect8 = this.transparentRect;
                int i17 = this.tempRect.left;
                int i18 = rect8.right;
                if (i17 > i18) {
                    i17 = i18;
                }
                rect8.right = i17;
            } else if (i11 == height) {
                Rect rect9 = this.transparentRect;
                int i19 = this.tempRect.top;
                int i20 = rect9.bottom;
                if (i19 > i20) {
                    i19 = i20;
                }
                rect9.bottom = i19;
            }
        }
        DisplayCutout displayCutout2 = this.displayInfo.displayCutout;
        if (displayCutout2 != null) {
            z2 = (displayCutout2.getBoundingRectTop().isEmpty() && displayCutout2.getBoundingRectBottom().isEmpty()) ? false : true;
            z = (displayCutout2.getBoundingRectLeft().isEmpty() && displayCutout2.getBoundingRectRight().isEmpty()) ? false : true;
        } else {
            z = false;
            z2 = false;
        }
        if (getWidth() < getHeight()) {
            if (z2 || !z) {
                Rect rect10 = this.transparentRect;
                int roundedCornerSizeByPosition = getRoundedCornerSizeByPosition(1);
                Rect rect11 = this.transparentRect;
                int i21 = rect11.top;
                if (roundedCornerSizeByPosition < i21) {
                    roundedCornerSizeByPosition = i21;
                }
                rect10.top = roundedCornerSizeByPosition;
                int height2 = getHeight() - getRoundedCornerSizeByPosition(3);
                int i22 = this.transparentRect.bottom;
                if (height2 > i22) {
                    height2 = i22;
                }
                rect11.bottom = height2;
                return;
            }
            Rect rect12 = this.transparentRect;
            int roundedCornerSizeByPosition2 = getRoundedCornerSizeByPosition(0);
            Rect rect13 = this.transparentRect;
            int i23 = rect13.left;
            if (roundedCornerSizeByPosition2 < i23) {
                roundedCornerSizeByPosition2 = i23;
            }
            rect12.left = roundedCornerSizeByPosition2;
            int width2 = getWidth() - getRoundedCornerSizeByPosition(2);
            int i24 = this.transparentRect.right;
            if (width2 > i24) {
                width2 = i24;
            }
            rect13.right = width2;
            return;
        }
        if (!z2 || z) {
            Rect rect14 = this.transparentRect;
            int roundedCornerSizeByPosition3 = getRoundedCornerSizeByPosition(0);
            Rect rect15 = this.transparentRect;
            int i25 = rect15.left;
            if (roundedCornerSizeByPosition3 < i25) {
                roundedCornerSizeByPosition3 = i25;
            }
            rect14.left = roundedCornerSizeByPosition3;
            int width3 = getWidth() - getRoundedCornerSizeByPosition(2);
            int i26 = this.transparentRect.right;
            if (width3 > i26) {
                width3 = i26;
            }
            rect15.right = width3;
            return;
        }
        Rect rect16 = this.transparentRect;
        int roundedCornerSizeByPosition4 = getRoundedCornerSizeByPosition(1);
        Rect rect17 = this.transparentRect;
        int i27 = rect17.top;
        if (roundedCornerSizeByPosition4 < i27) {
            roundedCornerSizeByPosition4 = i27;
        }
        rect16.top = roundedCornerSizeByPosition4;
        int height3 = getHeight() - getRoundedCornerSizeByPosition(3);
        int i28 = this.transparentRect.bottom;
        if (height3 > i28) {
            height3 = i28;
        }
        rect17.bottom = height3;
    }

    public final void drawRoundedCorner(Canvas canvas, Drawable drawable, int i) {
        Canvas canvas2;
        if (this.useInvertedAlphaColor) {
            float f = i;
            canvas2 = canvas;
            canvas2.drawRect(0.0f, 0.0f, f, f, this.clearPaint);
            if (drawable != null) {
                drawable.setColorFilter(this.cornerBgFilter);
            }
        } else {
            canvas2 = canvas;
            if (drawable != null) {
                drawable.setColorFilter(this.cornerFilter);
            }
        }
        if (drawable != null) {
            drawable.draw(canvas2);
        }
        if (drawable != null) {
            drawable.clearColorFilter();
        }
    }

    public void drawRoundedCorners(Canvas canvas) {
        if (this.hasTopRoundedCorner || this.hasBottomRoundedCorner) {
            for (int i = 0; i < 4; i++) {
                canvas.save();
                canvas.rotate((((i * 90) - (this.displayRotation * 90)) + 360) % 360);
                canvas.translate(getRoundedCornerTranslationX(r1), getRoundedCornerTranslationY(r1));
                if (this.hasTopRoundedCorner && (i == 0 || i == 1)) {
                    drawRoundedCorner(canvas, this.roundedCornerDrawableTop, this.roundedCornerTopSize);
                } else if (this.hasBottomRoundedCorner && (i == 3 || i == 2)) {
                    drawRoundedCorner(canvas, this.roundedCornerDrawableBottom, this.roundedCornerBottomSize);
                }
                canvas.restore();
            }
        }
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("ScreenDecorHwcLayer:");
        super.dump(printWriter);
        asIndenting.println("this=" + this);
        asIndenting.println("transparentRect=" + this.transparentRect);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("hasTopRoundedCorner=", this.hasTopRoundedCorner, asIndenting);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("hasBottomRoundedCorner=", this.hasBottomRoundedCorner, asIndenting);
        asIndenting.println("roundedCornerTopSize=" + this.roundedCornerTopSize);
        asIndenting.println("roundedCornerBottomSize=" + this.roundedCornerBottomSize);
        asIndenting.decreaseIndent();
    }

    @Override // android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        if (region == null) {
            return false;
        }
        calculateTransparentRect();
        if (this.debug) {
            region.setEmpty();
            return false;
        }
        region.op(this.transparentRect, Region.Op.INTERSECT);
        return false;
    }

    public final int getRoundedCornerSizeByPosition(int i) {
        int i2 = (this.displayRotation + i) % 4;
        if (i2 == 0) {
            int i3 = this.roundedCornerTopSize;
            int i4 = this.roundedCornerBottomSize;
            return i3 < i4 ? i4 : i3;
        }
        if (i2 == 1) {
            return this.roundedCornerTopSize;
        }
        if (i2 != 2) {
            if (i2 == 3) {
                return this.roundedCornerBottomSize;
            }
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Incorrect position: "));
        }
        int i5 = this.roundedCornerTopSize;
        int i6 = this.roundedCornerBottomSize;
        return i5 < i6 ? i6 : i5;
    }

    public final int getRoundedCornerTranslationX(int i) {
        int width;
        if (i == 0 || i == 90) {
            return 0;
        }
        if (i == 180) {
            width = getWidth();
        } else {
            if (i != 270) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Incorrect degree: "));
            }
            width = getHeight();
        }
        return -width;
    }

    public final int getRoundedCornerTranslationY(int i) {
        int width;
        if (i == 0) {
            return 0;
        }
        if (i == 90) {
            width = getWidth();
        } else {
            if (i != 180) {
                if (i == 270) {
                    return 0;
                }
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Incorrect degree: "));
            }
            width = getHeight();
        }
        return -width;
    }

    @Override // com.android.systemui.DisplayCutoutBaseView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getParent().requestTransparentRegion(this);
        updateColors();
    }

    @Override // com.android.systemui.DisplayCutoutBaseView, android.view.View
    public final void onDraw(Canvas canvas) {
        Paint paint;
        if (this.useInvertedAlphaColor) {
            canvas.drawColor(this.bgColor);
        }
        drawRoundedCorners(canvas);
        super.onDraw(canvas);
        if (!this.debugTransparentRegion || (paint = this.debugTransparentRegionPaint) == null) {
            return;
        }
        canvas.drawRect(this.transparentRect, paint);
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public final void onUpdate() {
        getParent().requestTransparentRegion(this);
    }

    public final void updateColors() {
        if (!this.debug) {
            getViewRootImpl().setDisplayDecoration(true);
        }
        this.cornerFilter = new PorterDuffColorFilter(this.color, PorterDuff.Mode.SRC_IN);
        if (this.useInvertedAlphaColor) {
            this.paint.set(this.clearPaint);
            this.paintForCameraProtection.set(this.clearPaint);
            this.paintForCameraProtection.setStyle(Paint.Style.FILL_AND_STROKE);
            this.paintForCameraProtection.setStrokeWidth(this.cameraProtectionStrokeWidth);
            return;
        }
        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.FILL);
        this.paintForCameraProtection.setColor(this.color);
        this.paintForCameraProtection.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintForCameraProtection.setStrokeWidth(this.cameraProtectionStrokeWidth);
    }

    public void updateRoundedCornerDrawableBounds() {
        Drawable drawable = this.roundedCornerDrawableTop;
        if (drawable != null && drawable != null) {
            int i = this.roundedCornerTopSize;
            drawable.setBounds(0, 0, i, i);
        }
        Drawable drawable2 = this.roundedCornerDrawableBottom;
        if (drawable2 != null && drawable2 != null) {
            int i2 = this.roundedCornerBottomSize;
            drawable2.setBounds(0, 0, i2, i2);
        }
        invalidate();
    }

    public void updateRoundedCornerExistenceAndSize(int i, int i2, boolean z, boolean z2) {
        if (this.hasTopRoundedCorner == z && this.hasBottomRoundedCorner == z2 && this.roundedCornerTopSize == i && this.roundedCornerBottomSize == i2) {
            return;
        }
        this.hasTopRoundedCorner = z;
        this.hasBottomRoundedCorner = z2;
        this.roundedCornerTopSize = i;
        this.roundedCornerBottomSize = i2;
        updateRoundedCornerDrawableBounds();
        requestLayout();
    }
}
