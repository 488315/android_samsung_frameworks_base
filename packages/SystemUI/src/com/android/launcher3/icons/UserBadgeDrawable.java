package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UserBadgeDrawable extends DrawableWrapper {
    static final int SHADOW_COLOR = 285212672;
    public final int mBaseColor;
    public final int mBgColor;
    public final boolean mIsThemed;
    public final Paint mPaint;
    public final Path mShape;
    public final boolean mShouldDrawBackground;

    public /* synthetic */ UserBadgeDrawable(Drawable drawable, int i, int i2, boolean z, int i3) {
        this(drawable, i, i2, z);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mShouldDrawBackground) {
            Rect bounds = getBounds();
            int save = canvas.save();
            canvas.translate(bounds.left, bounds.top);
            canvas.scale(bounds.width() / 24.0f, bounds.height() / 24.0f);
            this.mPaint.setColor(ColorUtils.setAlphaComponent(SHADOW_COLOR, (int) (Color.valueOf(SHADOW_COLOR).alpha() * getAlpha())));
            Path path = this.mShape;
            if (path != null) {
                canvas.drawPath(path, this.mPaint);
            } else {
                canvas.drawCircle(12.0f, 12.25f, 11.5f, this.mPaint);
            }
            Paint paint = this.mPaint;
            int i = this.mBgColor;
            paint.setColor(ColorUtils.setAlphaComponent(i, (int) (Color.valueOf(i).alpha() * getAlpha())));
            Path path2 = this.mShape;
            if (path2 != null) {
                canvas.drawPath(path2, this.mPaint);
            } else {
                canvas.drawCircle(12.0f, 12.0f, 11.0f, this.mPaint);
            }
            canvas.restoreToCount(save);
        }
        super.draw(canvas);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return new MyConstantState(getDrawable().getConstantState(), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter == null) {
            super.setTint(this.mBaseColor);
            return;
        }
        if (!(colorFilter instanceof ColorMatrixColorFilter)) {
            Paint paint = new Paint();
            paint.setColorFilter(colorFilter);
            Bitmap createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawPaint(paint);
            super.setTint(createBitmap.getPixel(0, 0));
            return;
        }
        ColorMatrix colorMatrix = new ColorMatrix();
        ((ColorMatrixColorFilter) colorFilter).getColorMatrix(colorMatrix);
        ColorMatrix colorMatrix2 = new ColorMatrix();
        float[] array = colorMatrix2.getArray();
        array[0] = Color.red(this.mBaseColor) / 255.0f;
        array[6] = Color.green(this.mBaseColor) / 255.0f;
        array[12] = Color.blue(this.mBaseColor) / 255.0f;
        array[18] = Color.alpha(this.mBaseColor) / 255.0f;
        colorMatrix2.postConcat(colorMatrix);
        super.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MyConstantState extends Drawable.ConstantState {
        public final Drawable.ConstantState mBase;
        public final int mBaseColor;
        public final int mBgColor;
        public final boolean mShouldDrawBackground;

        public MyConstantState(Drawable.ConstantState constantState, int i, int i2, boolean z) {
            this.mBase = constantState;
            this.mBgColor = i;
            this.mBaseColor = i2;
            this.mShouldDrawBackground = z;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return this.mBase.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return new UserBadgeDrawable(this.mBase.newDrawable(), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground, 0);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources) {
            return new UserBadgeDrawable(this.mBase.newDrawable(resources), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground, 0);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new UserBadgeDrawable(this.mBase.newDrawable(resources, theme), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground, 0);
        }
    }

    public UserBadgeDrawable(Context context, int i, int i2, boolean z, Path path) {
        super(context.getDrawable(i));
        this.mPaint = new Paint(1);
        this.mShouldDrawBackground = true;
        new Matrix();
        this.mShape = path;
        Matrix matrix = new Matrix();
        if (path != null) {
            matrix.setRectToRect(new RectF(0.0f, 0.0f, 100.0f, 100.0f), new RectF(0.0f, 0.0f, 24.0f, 24.0f), Matrix.ScaleToFit.CENTER);
            path.transform(matrix);
        }
        this.mIsThemed = z;
        if (z) {
            mutate();
            this.mBaseColor = context.getColor(R.color.themed_badge_icon_color);
            this.mBgColor = context.getColor(R.color.themed_badge_icon_background_color);
        } else {
            this.mBaseColor = context.getColor(i2);
            this.mBgColor = -1;
        }
        setTint(this.mBaseColor);
    }

    private UserBadgeDrawable(Drawable drawable, int i, int i2, boolean z) {
        super(drawable);
        this.mPaint = new Paint(1);
        this.mShouldDrawBackground = true;
        new Matrix();
        this.mIsThemed = false;
        this.mBgColor = i;
        this.mBaseColor = i2;
        this.mShouldDrawBackground = z;
    }
}
