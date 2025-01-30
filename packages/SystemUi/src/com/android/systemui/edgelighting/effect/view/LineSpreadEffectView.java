package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.utils.SineInOut80;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LineSpreadEffectView extends View {
    public final String TAG;
    public AnimatorSet mAnimatorSet;
    public int mColor;
    public final Rect mCurRect;
    public float mFinalRadius;
    public float mLineWidth;
    public float mMoveHeight;
    public float mMoveWidth;
    public final float[] mPercentArr;
    public float mRadius;
    public Paint mSquarePaint1;
    public Paint mSquarePaint2;
    public Paint mSquarePaint3;
    public final int[][] mTimeTable;

    public LineSpreadEffectView(Context context) {
        super(context);
        this.TAG = "LineSpreadEffectView";
        this.mCurRect = new Rect();
        this.mPercentArr = new float[3];
        this.mTimeTable = new int[][]{new int[]{117, 317, 2200}, new int[]{192, 468, 3200}, new int[]{IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut, 610, 4200}};
        initialize();
    }

    public final void initialize() {
        this.mSquarePaint1 = new Paint();
        this.mSquarePaint2 = new Paint();
        this.mSquarePaint3 = new Paint();
        this.mMoveHeight = getResources().getDimension(R.dimen.line_effect_move_height);
        this.mMoveWidth = getResources().getDimension(R.dimen.line_effect_move_width);
        this.mLineWidth = getResources().getDimension(R.dimen.noti_stroke_width);
        this.mRadius = getResources().getDimension(R.dimen.line_effect_radius_first);
        this.mFinalRadius = getResources().getDimension(R.dimen.line_effect_radius);
        this.mSquarePaint1.setStrokeWidth(this.mLineWidth);
        this.mSquarePaint1.setStyle(Paint.Style.STROKE);
        this.mSquarePaint1.setAntiAlias(true);
        this.mSquarePaint2.setStrokeWidth(this.mLineWidth);
        this.mSquarePaint2.setStyle(Paint.Style.STROKE);
        this.mSquarePaint2.setAntiAlias(true);
        this.mSquarePaint3.setStrokeWidth(this.mLineWidth);
        this.mSquarePaint3.setStyle(Paint.Style.STROKE);
        this.mSquarePaint3.setAntiAlias(true);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mSquarePaint1.setAlpha((int) ((1.0f - this.mPercentArr[0]) * 255.0f));
        this.mSquarePaint2.setAlpha((int) ((1.0f - this.mPercentArr[1]) * 255.0f));
        this.mSquarePaint3.setAlpha((int) ((1.0f - this.mPercentArr[2]) * 255.0f));
        float f = this.mRadius;
        float[] fArr = this.mPercentArr;
        float f2 = fArr[0];
        float f3 = this.mFinalRadius;
        float f4 = (f3 * f2) + ((1.0f - f2) * f);
        float f5 = fArr[1];
        float f6 = (f5 * f3) + ((1.0f - f5) * f);
        float f7 = fArr[2];
        float f8 = (f3 * f7) + ((1.0f - f7) * f);
        Rect rect = this.mCurRect;
        float f9 = rect.left;
        float f10 = this.mMoveWidth;
        float f11 = f9 - (f10 * f2);
        float f12 = rect.top;
        float f13 = this.mMoveHeight;
        canvas.drawRoundRect(f11, f12 - (f13 * f2), (f10 * f2) + rect.right, (f13 * f2) + rect.bottom, f4, f4, this.mSquarePaint1);
        float f14 = this.mPercentArr[1];
        if (f14 > 0.0f) {
            Rect rect2 = this.mCurRect;
            float f15 = rect2.left;
            float f16 = this.mMoveWidth;
            float f17 = f15 - (f16 * f14);
            float f18 = rect2.top;
            float f19 = this.mMoveHeight;
            canvas.drawRoundRect(f17, f18 - (f19 * f14), (f16 * f14) + rect2.right, (f19 * f14) + rect2.bottom, f6, f6, this.mSquarePaint2);
        }
        float f20 = this.mPercentArr[2];
        if (f20 > 0.0f) {
            Rect rect3 = this.mCurRect;
            float f21 = rect3.left;
            float f22 = this.mMoveWidth;
            float f23 = f21 - (f22 * f20);
            float f24 = rect3.top;
            float f25 = this.mMoveHeight;
            canvas.drawRoundRect(f23, f24 - (f25 * f20), (f22 * f20) + rect3.right, (f25 * f20) + rect3.bottom, f8, f8, this.mSquarePaint3);
        }
    }

    public final void startLineAnimation(long j) {
        Log.i(this.TAG, " start Animation");
        if (this.mAnimatorSet == null) {
            this.mAnimatorSet = new AnimatorSet();
        }
        this.mAnimatorSet.cancel();
        for (final int i = 0; i < 3; i++) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.LineSpreadEffectView.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LineSpreadEffectView.this.mPercentArr[i] = valueAnimator.getAnimatedFraction();
                    LineSpreadEffectView.this.invalidate();
                }
            });
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.LineSpreadEffectView.2
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (i == 3) {
                        LineSpreadEffectView.this.setVisibility(8);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            ofFloat.setInterpolator(new SineInOut80());
            int i2 = ((int) (j - 4000)) / 1000;
            ofFloat.setStartDelay(this.mTimeTable[i2][1] * i);
            ofFloat.setDuration(this.mTimeTable[i2][2]);
            ofFloat.setRepeatMode(1);
            this.mAnimatorSet.playTogether(ofFloat);
        }
        this.mAnimatorSet.start();
    }

    public final void stopLineAnimation() {
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimatorSet = null;
        }
        for (int i = 0; i < 3; i++) {
            this.mPercentArr[i] = 0.0f;
        }
        invalidate();
    }

    public LineSpreadEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "LineSpreadEffectView";
        this.mCurRect = new Rect();
        this.mPercentArr = new float[3];
        this.mTimeTable = new int[][]{new int[]{117, 317, 2200}, new int[]{192, 468, 3200}, new int[]{IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut, 610, 4200}};
        initialize();
    }

    public LineSpreadEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "LineSpreadEffectView";
        this.mCurRect = new Rect();
        this.mPercentArr = new float[3];
        this.mTimeTable = new int[][]{new int[]{117, 317, 2200}, new int[]{192, 468, 3200}, new int[]{IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut, 610, 4200}};
        initialize();
    }

    public LineSpreadEffectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "LineSpreadEffectView";
        this.mCurRect = new Rect();
        this.mPercentArr = new float[3];
        this.mTimeTable = new int[][]{new int[]{117, 317, 2200}, new int[]{192, 468, 3200}, new int[]{IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut, 610, 4200}};
    }
}
