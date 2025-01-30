package com.android.systemui.privacy.television;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;
import android.view.Gravity;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PrivacyChipDrawable extends Drawable {
    public final int mBgHeight;
    public final Paint mBgPaint;
    public final int mBgRadius;
    public final int mBgWidth;
    public final Paint mChipPaint;
    public final Animator mCollapse;
    public final boolean mCollapseToDot;
    public final float mCollapsedChipRadius;
    public final int mDotSize;
    public final Animator mExpand;
    public final float mExpandedChipRadius;
    public final int mLayoutDirection;
    public final Rect mTmpRect = new Rect();
    public final Rect mBgRect = new Rect();
    public final RectF mTmpRectF = new RectF();
    public final Path mPath = new Path();
    public boolean mIsExpanded = true;
    public float mCollapseProgress = 0.0f;

    public PrivacyChipDrawable(Context context, int i, boolean z) {
        this.mCollapseToDot = z;
        Paint paint = new Paint();
        this.mChipPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getColor(i));
        paint.setFlags(1);
        Paint paint2 = new Paint();
        this.mBgPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(context.getColor(R.color.privacy_chip_dot_bg_tint));
        paint2.setFlags(1);
        Resources resources = context.getResources();
        this.mLayoutDirection = resources.getConfiguration().getLayoutDirection();
        this.mBgWidth = resources.getDimensionPixelSize(R.dimen.privacy_chip_dot_bg_width);
        this.mBgHeight = resources.getDimensionPixelSize(R.dimen.privacy_chip_dot_bg_height);
        this.mBgRadius = resources.getDimensionPixelSize(R.dimen.privacy_chip_dot_bg_radius);
        this.mDotSize = resources.getDimensionPixelSize(R.dimen.privacy_chip_dot_size);
        this.mExpandedChipRadius = resources.getDimensionPixelSize(R.dimen.privacy_chip_radius);
        this.mCollapsedChipRadius = resources.getDimensionPixelSize(R.dimen.privacy_chip_dot_radius);
        Animator loadAnimator = AnimatorInflater.loadAnimator(context, R.anim.tv_privacy_chip_expand);
        this.mExpand = loadAnimator;
        loadAnimator.setTarget(this);
        Animator loadAnimator2 = AnimatorInflater.loadAnimator(context, R.anim.tv_privacy_chip_collapse);
        this.mCollapse = loadAnimator2;
        loadAnimator2.setTarget(this);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mCollapseProgress > 0.0f) {
            Gravity.apply(8388613, this.mBgWidth, this.mBgHeight, getBounds(), this.mBgRect, this.mLayoutDirection);
            this.mTmpRectF.set(this.mBgRect);
            RectF rectF = this.mTmpRectF;
            float f = this.mBgRadius;
            canvas.drawRoundRect(rectF, f, f, this.mBgPaint);
        }
        getForegroundBounds(this.mTmpRectF);
        float lerp = MathUtils.lerp(this.mExpandedChipRadius, this.mCollapseToDot ? this.mCollapsedChipRadius : this.mBgRadius, this.mCollapseProgress);
        canvas.drawRoundRect(this.mTmpRectF, lerp, lerp, this.mChipPaint);
    }

    public float getCollapseProgress() {
        return this.mCollapseProgress;
    }

    public final void getForegroundBounds(RectF rectF) {
        Rect bounds = getBounds();
        Rect rect = this.mTmpRect;
        Rect bounds2 = getBounds();
        Rect rect2 = this.mBgRect;
        Gravity.apply(8388613, this.mBgWidth, this.mBgHeight, getBounds(), rect2, this.mLayoutDirection);
        if (this.mCollapseToDot) {
            int i = this.mDotSize;
            Gravity.apply(17, i, i, this.mBgRect, rect);
        } else {
            int i2 = bounds2.left;
            Rect rect3 = this.mBgRect;
            rect.set(i2, rect3.top, bounds2.right, rect3.bottom);
        }
        Rect rect4 = this.mTmpRect;
        float f = this.mCollapseProgress;
        rectF.set(MathUtils.lerp(bounds.left, rect4.left, f), MathUtils.lerp(bounds.top, rect4.top, f), MathUtils.lerp(bounds.right, rect4.right, f), MathUtils.lerp(bounds.bottom, rect4.bottom, f));
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mChipPaint.setAlpha(i);
        this.mBgPaint.setAlpha(i);
    }

    public void setCollapseProgress(float f) {
        this.mCollapseProgress = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
