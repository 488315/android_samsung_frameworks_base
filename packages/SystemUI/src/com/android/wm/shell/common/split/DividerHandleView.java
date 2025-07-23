package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import com.android.wm.shell.R;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DividerHandleView extends View {
    public static final AnonymousClass2 HEIGHT_PROPERTY;
    public static final AnonymousClass1 WIDTH_PROPERTY;
    public AnimatorSet mAnimator;
    public final int mCircleDiameter;
    public final int mCircleMargin;
    public int mCurrentHeight;
    public int mCurrentWidth;
    public final int mHandleType;
    public int mHeight;
    public final int mHorizontalHandlerTopMargin;
    public boolean mHovering;
    public int mHoveringHeight;
    public int mHoveringWidth;
    public final boolean mIsHorizontalDivision;
    public boolean mIsLeftRightSplit;
    public final int mOuterCircleOffset;
    public final Paint mOutsideCirclePaint;
    public final Paint mPaint;
    public int mWidth;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.common.split.DividerHandleView$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.split.DividerHandleView$2] */
    static {
        Class<Integer> cls = Integer.class;
        WIDTH_PROPERTY = new Property(cls, "width") { // from class: com.android.wm.shell.common.split.DividerHandleView.1
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Integer.valueOf(((DividerHandleView) obj).mCurrentWidth);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerHandleView dividerHandleView = (DividerHandleView) obj;
                dividerHandleView.mCurrentWidth = ((Integer) obj2).intValue();
                dividerHandleView.invalidate();
            }
        };
        HEIGHT_PROPERTY = new Property(cls, "height") { // from class: com.android.wm.shell.common.split.DividerHandleView.2
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Integer.valueOf(((DividerHandleView) obj).mCurrentHeight);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerHandleView dividerHandleView = (DividerHandleView) obj;
                dividerHandleView.mCurrentHeight = ((Integer) obj2).intValue();
                dividerHandleView.invalidate();
            }
        };
    }

    public DividerHandleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mOutsideCirclePaint = paint;
        Paint paint2 = new Paint();
        this.mPaint = paint2;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.DividerHandleView, 0, 0);
        try {
            int i = obtainStyledAttributes.getInt(0, 0);
            this.mHandleType = i;
            boolean z = true;
            if (i != 0) {
                z = obtainStyledAttributes.getBoolean(1, true);
            } else if (getResources().getConfiguration().orientation != 1) {
                z = false;
            }
            this.mIsHorizontalDivision = z;
            obtainStyledAttributes.recycle();
            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || i != 2) {
                this.mWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_handle_width);
                this.mHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_handle_height);
            } else if (z) {
                this.mWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.cell_divider_handle_width);
                this.mHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.cell_divider_handle_height);
            } else {
                this.mWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.cell_divider_handle_height);
                this.mHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.cell_divider_handle_width);
            }
            int displayId = context.getDisplayId();
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                paint2.setColor(getResources().getColor(com.android.systemui.R.color.mw_tile_divider_handle_inner_circle));
                paint.setColor(getResources().getColor(com.android.systemui.R.color.mw_tile_divider_handle_outer_circle));
                this.mOuterCircleOffset = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.dw_tile_handle_outer_circle_offset);
            } else {
                paint2.setColor(getResources().getColor(com.android.systemui.R.color.split_divider_handle_circle));
            }
            this.mCircleMargin = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_handle_circle_margin);
            this.mCircleDiameter = Math.min(this.mWidth, this.mHeight);
            if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
                this.mHorizontalHandlerTopMargin = z ? getResources().getDimensionPixelSize(com.android.systemui.R.dimen.multi_split_docked_divider_horizontal_handler_margin_top) : 0;
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        float f = (width - r1) / 2.0f;
        float f2 = f + this.mCircleDiameter;
        int height = getHeight();
        int i = this.mCircleDiameter;
        float f3 = ((height - i) / 2.0f) + this.mHorizontalHandlerTopMargin;
        int i2 = this.mHandleType;
        if (i2 != 0 && i2 != 1) {
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && i2 == 2) {
                float width2 = getWidth() / 2;
                float height2 = getHeight() / 2;
                if (!this.mIsHorizontalDivision) {
                    float f4 = height2 - (this.mCircleMargin / 2);
                    canvas.drawOval(f, f4 - this.mCircleDiameter, f2, f4, this.mPaint);
                    float f5 = height2 + (this.mCircleMargin / 2);
                    canvas.drawOval(f, f5, f2, f5 + this.mCircleDiameter, this.mPaint);
                    return;
                }
                float f6 = width2 - (this.mCircleMargin / 2);
                float f7 = this.mCircleDiameter;
                canvas.drawOval(f6 - f7, f3, f6, f3 + f7, this.mPaint);
                float f8 = width2 + (this.mCircleMargin / 2);
                float f9 = this.mCircleDiameter;
                canvas.drawOval(f8, f3, f8 + f9, f3 + f9, this.mPaint);
                return;
            }
            return;
        }
        Canvas canvas2 = canvas;
        float f10 = i;
        float f11 = f3 + f10;
        if (this.mIsHorizontalDivision) {
            float f12 = f - this.mCircleMargin;
            canvas2.drawOval(f12 - f10, f3, f12, f11, this.mPaint);
            canvas2.drawOval(f, f3, f2, f11, this.mPaint);
            float f13 = f2 + this.mCircleMargin;
            canvas2.drawOval(f13, f3, f13 + this.mCircleDiameter, f11, this.mPaint);
            return;
        }
        Paint paint = this.mOutsideCirclePaint;
        if (paint != null) {
            float f14 = this.mOuterCircleOffset;
            float f15 = f3 - this.mCircleMargin;
            canvas2.drawOval(f - f14, (f15 - f10) - f14, f2 + f14, f15 + f14, paint);
            float f16 = this.mOuterCircleOffset;
            canvas2.drawOval(f - f16, f3 - f16, f2 + f16, f11 + f16, this.mOutsideCirclePaint);
            float f17 = this.mOuterCircleOffset;
            float f18 = f11 + this.mCircleMargin;
            canvas2.drawOval(f - f17, f18 - f17, f2 + f17, f18 + this.mCircleDiameter + f17, this.mOutsideCirclePaint);
            canvas2 = canvas2;
        }
        float f19 = f3 - this.mCircleMargin;
        canvas2.drawOval(f, f19 - this.mCircleDiameter, f2, f19, this.mPaint);
        canvas2.drawOval(f, f3, f2, f11, this.mPaint);
        float f20 = f11 + this.mCircleMargin;
        canvas2.drawOval(f, f20, f2, f20 + this.mCircleDiameter, this.mPaint);
    }

    public final void setHovering(boolean z) {
        if (z == this.mHovering) {
            return;
        }
        int i = this.mHoveringWidth;
        int i2 = this.mHoveringHeight;
        AnimatorSet animatorSet = this.mAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimator = null;
        }
        if (!z) {
            i = this.mWidth;
        }
        if (!z) {
            i2 = this.mHeight;
        }
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, WIDTH_PROPERTY, this.mCurrentWidth, i);
        ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this, HEIGHT_PROPERTY, this.mCurrentHeight, i2);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimator = animatorSet2;
        animatorSet2.playTogether(ofInt, ofInt2);
        this.mAnimator.setDuration(z ? 150L : 200L);
        this.mAnimator.setInterpolator(z ? Interpolators.TOUCH_RESPONSE : Interpolators.FAST_OUT_SLOW_IN);
        this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerHandleView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DividerHandleView.this.mAnimator = null;
            }
        });
        this.mAnimator.start();
        this.mHovering = z;
    }

    public final void setIsLeftRightSplit(boolean z) {
        this.mIsLeftRightSplit = z;
        Resources resources = getResources();
        boolean z2 = this.mIsLeftRightSplit;
        int i = com.android.systemui.R.dimen.split_divider_handle_width;
        this.mWidth = resources.getDimensionPixelSize(z2 ? com.android.systemui.R.dimen.split_divider_handle_height : com.android.systemui.R.dimen.split_divider_handle_width);
        Resources resources2 = getResources();
        if (!this.mIsLeftRightSplit) {
            i = com.android.systemui.R.dimen.split_divider_handle_height;
        }
        int dimensionPixelSize = resources2.getDimensionPixelSize(i);
        this.mHeight = dimensionPixelSize;
        int i2 = this.mWidth;
        this.mCurrentWidth = i2;
        this.mCurrentHeight = dimensionPixelSize;
        this.mHoveringWidth = i2 > dimensionPixelSize ? (int) (i2 * 1.5f) : i2;
        if (dimensionPixelSize > i2) {
            dimensionPixelSize = (int) (dimensionPixelSize * 1.5f);
        }
        this.mHoveringHeight = dimensionPixelSize;
    }
}
