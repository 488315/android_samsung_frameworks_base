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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SignalDrawable extends DrawableWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimating;
    public final Path mAttributionPath;
    public final Matrix mAttributionScaleMatrix;
    public final RunnableC09261 mChangeDot;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.graph.SignalDrawable$1 */
    public final class RunnableC09261 implements Runnable {
        public RunnableC09261() {
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
        super(context.getDrawable(R.drawable.ic_qs_one_handed_mode));
        this.mForegroundPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.mTransparentPaint = paint;
        this.mCutoutPath = new Path();
        this.mForegroundPath = new Path();
        Path path = new Path();
        this.mAttributionPath = path;
        this.mAttributionScaleMatrix = new Matrix();
        this.mScaledAttributionPath = new Path();
        this.mChangeDot = new RunnableC09261();
        path.set(PathParser.createPathFromPathData(context.getString(R.string.ext_media_new_notification_title)));
        updateScaledAttributionPath();
        this.mCutoutWidthFraction = context.getResources().getFloat(R.dimen.config_resActivitySnapshotScale);
        this.mCutoutHeightFraction = context.getResources().getFloat(R.dimen.config_qsTileStrokeWidthInactive);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(com.android.systemui.R.color.dark_mode_icon_color_single_tone, context);
        int colorStateListDefaultColor2 = Utils.getColorStateListDefaultColor(com.android.systemui.R.color.light_mode_icon_color_single_tone, context);
        this.mIntrinsicSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.signal_icon_size);
        paint.setColor(context.getColor(R.color.transparent));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.mHandler = new Handler();
        setTintList(ColorStateList.valueOf(((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(colorStateListDefaultColor2), Integer.valueOf(colorStateListDefaultColor))).intValue()));
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.saveLayer(null, null);
        float width = getBounds().width();
        float height = getBounds().height();
        boolean z = getLayoutDirection() == 1;
        if (z) {
            canvas.save();
            canvas.translate(width, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.draw(canvas);
        this.mCutoutPath.reset();
        this.mCutoutPath.setFillType(Path.FillType.WINDING);
        float round = Math.round(0.083333336f * width);
        if (((getLevel() & 16711680) >> 16) == 3) {
            float f = 0.125f * height;
            float f2 = 0.0625f * height;
            float f3 = f2 + f;
            float f4 = (width - round) - f;
            float f5 = (height - round) - f;
            this.mForegroundPath.reset();
            drawDotAndPadding(2, f4, f5, f2, f);
            drawDotAndPadding(1, f4 - f3, f5, f2, f);
            drawDotAndPadding(0, f4 - (f3 * 2.0f), f5, f2, f);
            canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
            canvas.drawPath(this.mForegroundPath, this.mForegroundPaint);
        } else {
            if (((getLevel() & 16711680) >> 16) == 2) {
                float f6 = (this.mCutoutWidthFraction * width) / 24.0f;
                float f7 = (this.mCutoutHeightFraction * height) / 24.0f;
                this.mCutoutPath.moveTo(width, height);
                this.mCutoutPath.rLineTo(-f6, 0.0f);
                this.mCutoutPath.rLineTo(0.0f, -f7);
                this.mCutoutPath.rLineTo(f6, 0.0f);
                this.mCutoutPath.rLineTo(0.0f, f7);
                canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
                canvas.drawPath(this.mScaledAttributionPath, this.mForegroundPaint);
            }
        }
        if (z) {
            canvas.restore();
        }
        canvas.restore();
    }

    public final void drawDotAndPadding(int i, float f, float f2, float f3, float f4) {
        if (i == this.mCurrentDot) {
            float f5 = f + f4;
            float f6 = f2 + f4;
            this.mForegroundPath.addRect(f, f2, f5, f6, Path.Direction.CW);
            this.mCutoutPath.addRect(f - f3, f2 - f3, f5 + f3, f6 + f3, Path.Direction.CW);
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mIntrinsicSize;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mIntrinsicSize;
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
        boolean z = (((getLevel() & 16711680) >> 16) == 3) && isVisible();
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
}
