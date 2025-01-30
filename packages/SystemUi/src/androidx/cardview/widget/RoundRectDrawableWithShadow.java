package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RoundRectDrawableWithShadow extends Drawable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    public boolean mAddPaddingForCorners = true;
    public ColorStateList mBackground;
    public final RectF mCardBounds;
    public float mCornerRadius;
    public final Paint mCornerShadowPaint;
    public Path mCornerShadowPath;
    public boolean mDirty;
    public final Paint mEdgeShadowPaint;
    public final int mInsetShadow;
    public final Paint mPaint;
    public boolean mPrintedShadowClipWarning;
    public float mRawMaxShadowSize;
    public float mRawShadowSize;
    public final int mShadowEndColor;
    public float mShadowSize;
    public final int mShadowStartColor;

    public RoundRectDrawableWithShadow(Resources resources, ColorStateList colorStateList, float f, float f2, float f3) {
        this.mDirty = true;
        this.mPrintedShadowClipWarning = false;
        this.mShadowStartColor = resources.getColor(R.color.cardview_shadow_start_color);
        this.mShadowEndColor = resources.getColor(R.color.cardview_shadow_end_color);
        this.mInsetShadow = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
        Paint paint = new Paint(5);
        this.mPaint = paint;
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        this.mBackground = colorStateList;
        paint.setColor(colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor()));
        Paint paint2 = new Paint(5);
        this.mCornerShadowPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mCornerRadius = (int) (f + 0.5f);
        this.mCardBounds = new RectF();
        Paint paint3 = new Paint(paint2);
        this.mEdgeShadowPaint = paint3;
        paint3.setAntiAlias(false);
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f2 + ". Must be >= 0");
        }
        if (f3 < 0.0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + f3 + ". Must be >= 0");
        }
        int i = (int) (f2 + 0.5f);
        float f4 = i % 2 == 1 ? i - 1 : i;
        int i2 = (int) (f3 + 0.5f);
        float f5 = i2 % 2 == 1 ? i2 - 1 : i2;
        if (f4 > f5) {
            if (!this.mPrintedShadowClipWarning) {
                this.mPrintedShadowClipWarning = true;
            }
            f4 = f5;
        }
        if (this.mRawShadowSize == f4 && this.mRawMaxShadowSize == f5) {
            return;
        }
        this.mRawShadowSize = f4;
        this.mRawMaxShadowSize = f5;
        this.mShadowSize = (int) ((f4 * 1.5f) + r7 + 0.5f);
        this.mDirty = true;
        invalidateSelf();
    }

    public static float calculateVerticalPadding(float f, float f2, boolean z) {
        if (!z) {
            return f * 1.5f;
        }
        return (float) (((1.0d - COS_45) * f2) + (f * 1.5f));
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        if (this.mDirty) {
            Rect bounds = getBounds();
            float f = this.mRawMaxShadowSize;
            float f2 = 1.5f * f;
            this.mCardBounds.set(bounds.left + f, bounds.top + f2, bounds.right - f, bounds.bottom - f2);
            float f3 = this.mCornerRadius;
            float f4 = -f3;
            RectF rectF = new RectF(f4, f4, f3, f3);
            RectF rectF2 = new RectF(rectF);
            float f5 = -this.mShadowSize;
            rectF2.inset(f5, f5);
            Path path = this.mCornerShadowPath;
            if (path == null) {
                this.mCornerShadowPath = new Path();
            } else {
                path.reset();
            }
            this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
            this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f);
            this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
            this.mCornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
            this.mCornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
            this.mCornerShadowPath.close();
            float f6 = this.mCornerRadius;
            float f7 = f6 / (this.mShadowSize + f6);
            Paint paint = this.mCornerShadowPaint;
            float f8 = this.mCornerRadius + this.mShadowSize;
            int i2 = this.mShadowStartColor;
            paint.setShader(new RadialGradient(0.0f, 0.0f, f8, new int[]{i2, i2, this.mShadowEndColor}, new float[]{0.0f, f7, 1.0f}, Shader.TileMode.CLAMP));
            Paint paint2 = this.mEdgeShadowPaint;
            float f9 = -this.mCornerRadius;
            float f10 = this.mShadowSize;
            float f11 = f9 + f10;
            float f12 = f9 - f10;
            int i3 = this.mShadowStartColor;
            paint2.setShader(new LinearGradient(0.0f, f11, 0.0f, f12, new int[]{i3, i3, this.mShadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
            this.mEdgeShadowPaint.setAntiAlias(false);
            this.mDirty = false;
        }
        canvas.translate(0.0f, this.mRawShadowSize / 2.0f);
        float f13 = this.mCornerRadius;
        float f14 = (-f13) - this.mShadowSize;
        float f15 = (this.mRawShadowSize / 2.0f) + f13 + this.mInsetShadow;
        float f16 = f15 * 2.0f;
        boolean z = this.mCardBounds.width() - f16 > 0.0f;
        boolean z2 = this.mCardBounds.height() - f16 > 0.0f;
        int save = canvas.save();
        RectF rectF3 = this.mCardBounds;
        canvas.translate(rectF3.left + f15, rectF3.top + f15);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z) {
            i = save;
            canvas.drawRect(0.0f, f14, this.mCardBounds.width() - f16, -this.mCornerRadius, this.mEdgeShadowPaint);
        } else {
            i = save;
        }
        canvas.restoreToCount(i);
        int save2 = canvas.save();
        RectF rectF4 = this.mCardBounds;
        canvas.translate(rectF4.right - f15, rectF4.bottom - f15);
        canvas.rotate(180.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z) {
            canvas.drawRect(0.0f, f14, this.mCardBounds.width() - f16, (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save2);
        int save3 = canvas.save();
        RectF rectF5 = this.mCardBounds;
        canvas.translate(rectF5.left + f15, rectF5.bottom - f15);
        canvas.rotate(270.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z2) {
            canvas.drawRect(0.0f, f14, this.mCardBounds.height() - f16, -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save3);
        int save4 = canvas.save();
        RectF rectF6 = this.mCardBounds;
        canvas.translate(rectF6.right - f15, rectF6.top + f15);
        canvas.rotate(90.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z2) {
            canvas.drawRect(0.0f, f14, this.mCardBounds.height() - f16, -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save4);
        canvas.translate(0.0f, (-this.mRawShadowSize) / 2.0f);
        throw null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil(calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        float f = this.mRawMaxShadowSize;
        float f2 = this.mCornerRadius;
        if (this.mAddPaddingForCorners) {
            f = (float) (((1.0d - COS_45) * f2) + f);
        }
        int ceil2 = (int) Math.ceil(f);
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList = this.mBackground;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.mBackground;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        if (this.mPaint.getColor() == colorForState) {
            return false;
        }
        this.mPaint.setColor(colorForState);
        this.mDirty = true;
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        this.mCornerShadowPaint.setAlpha(i);
        this.mEdgeShadowPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
