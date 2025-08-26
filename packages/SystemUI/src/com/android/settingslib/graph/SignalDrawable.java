package com.android.settingslib.graph;

import android.R;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.os.Handler;
import android.telephony.CellSignalStrength;
import android.util.PathParser;
import com.android.settingslib.Utils;
import java.util.Objects;

/* loaded from: classes.dex */
public class SignalDrawable extends DrawableWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimating;
    public final Path mAttributionPath;
    public final Matrix mAttributionScaleMatrix;
    public final AnonymousClass1 mChangeDot;
    public int mCurrentDot;
    public final float mCutoutHeightFraction;
    public final Path mCutoutPath;
    public final float mCutoutWidthFraction;
    public final Paint mForegroundPaint;
    public final Path mForegroundPath;
    public final Handler mHandler;
    public final int mIntrinsicSize;
    public final Path mScaledAttributionPath;
    public final Paint mTransparentPaint;

    /* renamed from: com.android.settingslib.graph.SignalDrawable$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SignalDrawable signalDrawable = SignalDrawable.this;
            int i = signalDrawable.mCurrentDot + 1;
            signalDrawable.mCurrentDot = i;
            if (i == 3) {
                signalDrawable.mCurrentDot = 0;
            }
            signalDrawable.invalidateSelf();
            SignalDrawable signalDrawable2 = SignalDrawable.this;
            signalDrawable2.mHandler.postDelayed(signalDrawable2.mChangeDot, 1000L);
        }
    }

    public SignalDrawable(Context context) {
        this(context, new Handler());
    }

    public static int getState(int i, int i2, boolean z) {
        return i | (i2 << 8) | ((z ? 2 : 0) << 16);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.saveLayer(null, null);
        float fWidth = getBounds().width();
        float fHeight = getBounds().height();
        boolean z = getLayoutDirection() == 1;
        if (z) {
            canvas.save();
            canvas.translate(fWidth, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.draw(canvas);
        this.mCutoutPath.reset();
        this.mCutoutPath.setFillType(Path.FillType.WINDING);
        float fRound = Math.round(0.083333336f * fWidth);
        if (isInState(3)) {
            float f = fHeight * 0.125f;
            float f2 = fHeight * 0.0625f;
            float f3 = f2 + f;
            float f4 = (fWidth - fRound) - f;
            float f5 = (fHeight - fRound) - f;
            this.mForegroundPath.reset();
            drawDotAndPadding(f4, f5, f2, f, 2);
            drawDotAndPadding(f4 - f3, f5, f2, f, 1);
            drawDotAndPadding(f4 - (f3 * 2.0f), f5, f2, f, 0);
            canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
            canvas.drawPath(this.mForegroundPath, this.mForegroundPaint);
        } else if (isInState(2)) {
            float f6 = (this.mCutoutWidthFraction * fWidth) / 24.0f;
            float f7 = (this.mCutoutHeightFraction * fHeight) / 24.0f;
            this.mCutoutPath.moveTo(fWidth, fHeight);
            this.mCutoutPath.rLineTo(-f6, 0.0f);
            this.mCutoutPath.rLineTo(0.0f, -f7);
            this.mCutoutPath.rLineTo(f6, 0.0f);
            this.mCutoutPath.rLineTo(0.0f, f7);
            canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
            canvas.drawPath(this.mScaledAttributionPath, this.mForegroundPaint);
        }
        if (z) {
            canvas.restore();
        }
        canvas.restore();
    }

    public final void drawDotAndPadding(float f, float f2, float f3, float f4, int i) {
        if (i == this.mCurrentDot) {
            Path path = this.mForegroundPath;
            float f5 = f + f4;
            float f6 = f2 + f4;
            Path.Direction direction = Path.Direction.CW;
            path.addRect(f, f2, f5, f6, direction);
            this.mCutoutPath.addRect(f - f3, f2 - f3, f5 + f3, f6 + f3, direction);
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof SignalDrawable) && ((SignalDrawable) obj).getLevel() == getLevel();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mIntrinsicSize;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mIntrinsicSize;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(getLevel()));
    }

    public final boolean isInState(int i) {
        return ((getLevel() & 16711680) >> 16) == i;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateScaledAttributionPath();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        super.onLevelChange((i & 255) + (((65280 & i) >> 8) == CellSignalStrength.getNumSignalStrengthLevels() + 1 ? 10 : 0));
        updateAnimation();
        setTintList(ColorStateList.valueOf(this.mForegroundPaint.getColor()));
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        super.setAlpha(i);
        this.mForegroundPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        this.mForegroundPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        int color = this.mForegroundPaint.getColor();
        this.mForegroundPaint.setColor(colorStateList.getDefaultColor());
        if (color != this.mForegroundPaint.getColor()) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        updateAnimation();
        return visible;
    }

    public final void updateAnimation() {
        boolean z = isInState(3) && isVisible();
        if (z == this.mAnimating) {
            return;
        }
        this.mAnimating = z;
        if (z) {
            this.mChangeDot.run();
        } else {
            this.mHandler.removeCallbacks(this.mChangeDot);
        }
    }

    public final void updateScaledAttributionPath() {
        if (getBounds().isEmpty()) {
            this.mAttributionScaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.mAttributionScaleMatrix.setScale(getBounds().width() / 24.0f, getBounds().height() / 24.0f);
        }
        this.mAttributionPath.transform(this.mAttributionScaleMatrix, this.mScaledAttributionPath);
    }

    public SignalDrawable(Context context, Handler handler) {
        super(context.getDrawable(R.drawable.jog_tab_bar_right_generic));
        this.mForegroundPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.mTransparentPaint = paint;
        this.mCutoutPath = new Path();
        this.mForegroundPath = new Path();
        Path path = new Path();
        this.mAttributionPath = path;
        this.mAttributionScaleMatrix = new Matrix();
        this.mScaledAttributionPath = new Path();
        this.mChangeDot = new AnonymousClass1();
        path.set(PathParser.createPathFromPathData(context.getString(R.string.face_error_vendor_unknown)));
        updateScaledAttributionPath();
        this.mCutoutWidthFraction = context.getResources().getFloat(R.dimen.datepicker_list_year_label_size);
        this.mCutoutHeightFraction = context.getResources().getFloat(R.dimen.datepicker_list_year_activated_label_size);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(com.android.systemui.R.color.dark_mode_icon_color_single_tone, context);
        int colorStateListDefaultColor2 = Utils.getColorStateListDefaultColor(com.android.systemui.R.color.light_mode_icon_color_single_tone, context);
        this.mIntrinsicSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.signal_icon_size);
        paint.setColor(context.getColor(R.color.transparent));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.mHandler = handler;
        setTintList(ColorStateList.valueOf(((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(colorStateListDefaultColor2), Integer.valueOf(colorStateListDefaultColor))).intValue()));
    }
}
