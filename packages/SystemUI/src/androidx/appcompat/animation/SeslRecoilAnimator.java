package androidx.appcompat.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SeslRecoilAnimator {
    public static TimeInterpolator sPressInterpolator;
    public static TimeInterpolator sReleaseInterpolator;
    public final ValueAnimator mAnimator;
    public final Context mContext;
    public boolean mIsPressed = false;
    public boolean mIsScaleOnlyChildren;
    public float mScaleRatio;
    public View mTarget;

    public class Holder {
        public final ArrayList mAnimators = new ArrayList();
        public final Context mContext;

        public Holder(Context context) {
            this.mContext = context;
        }

        public final void setPress(View view) {
            SeslRecoilAnimator seslRecoilAnimator;
            if (view.isClickable()) {
                ArrayList arrayList = this.mAnimators;
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        ArrayList arrayList2 = this.mAnimators;
                        int size2 = arrayList2.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= size2) {
                                seslRecoilAnimator = new SeslRecoilAnimator(view, this.mContext);
                                this.mAnimators.add(seslRecoilAnimator);
                                break;
                            }
                            Object obj = arrayList2.get(i2);
                            i2++;
                            seslRecoilAnimator = (SeslRecoilAnimator) obj;
                            if (!seslRecoilAnimator.mIsPressed && !seslRecoilAnimator.mAnimator.isRunning()) {
                                seslRecoilAnimator.mTarget = view;
                                break;
                            }
                        }
                    } else {
                        Object obj2 = arrayList.get(i);
                        i++;
                        seslRecoilAnimator = (SeslRecoilAnimator) obj2;
                        if (seslRecoilAnimator.mTarget == view) {
                            break;
                        }
                    }
                }
                View view2 = seslRecoilAnimator.mTarget;
                if (view2 instanceof ViewGroup) {
                    seslRecoilAnimator.mIsScaleOnlyChildren = true;
                } else {
                    seslRecoilAnimator.mIsScaleOnlyChildren = false;
                }
                float width = view2.getWidth();
                seslRecoilAnimator.mScaleRatio = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(seslRecoilAnimator.mContext.getResources().getDisplayMetrics().density, 3.0f, width, width);
                if (seslRecoilAnimator.mIsPressed) {
                    return;
                }
                seslRecoilAnimator.mIsPressed = true;
                if (seslRecoilAnimator.mAnimator.isRunning()) {
                    seslRecoilAnimator.mAnimator.cancel();
                }
                float fFloatValue = ((Float) seslRecoilAnimator.mAnimator.getAnimatedValue()).floatValue();
                ValueAnimator valueAnimator = seslRecoilAnimator.mAnimator;
                if (fFloatValue == 0.0f) {
                    fFloatValue = 1.0f;
                }
                valueAnimator.setFloatValues(fFloatValue, seslRecoilAnimator.mScaleRatio);
                seslRecoilAnimator.mAnimator.setDuration(100L);
                seslRecoilAnimator.mAnimator.setInterpolator(SeslRecoilAnimator.sPressInterpolator);
                seslRecoilAnimator.mAnimator.start();
            }
        }

        public final void setRelease() {
            this.mAnimators.forEach(new SeslRecoilAnimator$Holder$$ExternalSyntheticLambda1());
        }
    }

    public SeslRecoilAnimator(View view, Context context) {
        this.mIsScaleOnlyChildren = false;
        this.mTarget = view;
        this.mContext = context;
        if (view instanceof ViewGroup) {
            this.mIsScaleOnlyChildren = true;
        } else {
            this.mIsScaleOnlyChildren = false;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f);
        this.mAnimator = valueAnimatorOfFloat;
        if (sPressInterpolator == null) {
            sPressInterpolator = AnimationUtils.loadInterpolator(context, R.anim.sesl_recoil_pressed);
        }
        if (sReleaseInterpolator == null) {
            sReleaseInterpolator = AnimationUtils.loadInterpolator(context, R.anim.sesl_recoil_released);
        }
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.animation.SeslRecoilAnimator$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslRecoilAnimator seslRecoilAnimator = this.f$0;
                seslRecoilAnimator.getClass();
                seslRecoilAnimator.animateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: androidx.appcompat.animation.SeslRecoilAnimator.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SeslRecoilAnimator.this.animateValue(((Float) ((ValueAnimator) animator).getAnimatedValue()).floatValue());
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
    }

    public final void animateValue(float f) {
        if (this.mIsScaleOnlyChildren) {
            View view = this.mTarget;
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Matrix matrix = new Matrix();
                    float width = (this.mTarget.getWidth() / 2.0f) - childAt.getLeft();
                    float height = (this.mTarget.getHeight() / 2.0f) - childAt.getTop();
                    matrix.setTranslate(-width, -height);
                    matrix.postScale(f, f);
                    matrix.postTranslate(width, height);
                    childAt.setAnimationMatrix(matrix);
                }
                return;
            }
        }
        this.mTarget.setScaleX(f);
        this.mTarget.setScaleY(f);
    }
}
