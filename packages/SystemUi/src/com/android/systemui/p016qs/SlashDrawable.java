package com.android.systemui.p016qs;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SlashDrawable extends Drawable {
    public float mCurrentSlashLength;
    public Drawable mDrawable;
    public float mRotation;
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;
    public final Path mPath = new Path();
    public final Paint mPaint = new Paint(1);
    public final RectF mSlashRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    public boolean mAnimationEnabled = true;
    public final C20761 mSlashLengthProp = new FloatProperty(this, "slashLength") { // from class: com.android.systemui.qs.SlashDrawable.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((SlashDrawable) obj).mCurrentSlashLength);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((SlashDrawable) obj).mCurrentSlashLength = f;
        }
    };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.SlashDrawable$1] */
    public SlashDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.save();
        Matrix matrix = new Matrix();
        int width = getBounds().width();
        int height = getBounds().height();
        float f = width;
        float f2 = 1.0f * f;
        float f3 = height;
        float f4 = 1.0f * f3;
        float f5 = 0.40544835f * f;
        float f6 = (-0.088781714f) * f3;
        float f7 = 0.4820516f * f;
        float f8 = (this.mCurrentSlashLength - 0.088781714f) * f3;
        RectF rectF = this.mSlashRect;
        rectF.left = f5;
        rectF.top = f6;
        rectF.right = f7;
        rectF.bottom = f8;
        this.mPath.reset();
        this.mPath.addRoundRect(this.mSlashRect, f2, f4, Path.Direction.CW);
        float f9 = width / 2;
        float f10 = height / 2;
        matrix.setRotate(this.mRotation - 45.0f, f9, f10);
        this.mPath.transform(matrix);
        canvas.drawPath(this.mPath, this.mPaint);
        matrix.setRotate((-this.mRotation) - (-45.0f), f9, f10);
        this.mPath.transform(matrix);
        matrix.setTranslate(this.mSlashRect.width(), 0.0f);
        this.mPath.transform(matrix);
        this.mPath.addRoundRect(this.mSlashRect, f2, f4, Path.Direction.CW);
        matrix.setRotate(this.mRotation - 45.0f, f9, f10);
        this.mPath.transform(matrix);
        canvas.clipOutPath(this.mPath);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 255;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mDrawable.setAlpha(i);
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        super.setTint(i);
        this.mDrawable.setTint(i);
        this.mPaint.setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        super.setTintList(colorStateList);
        this.mDrawable.setTintList(colorStateList);
        this.mPaint.setColor(colorStateList.getDefaultColor());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        super.setTintMode(mode);
        this.mDrawable.setTintMode(mode);
    }
}
