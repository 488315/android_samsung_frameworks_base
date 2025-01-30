package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import androidx.leanback.R$styleable;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class SlideKitkat extends Visibility {
    public CalculateSlide mSlideCalculator;
    public static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    public static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    public static final C02651 sCalculateLeft = new CalculateSlideHorizontal() { // from class: androidx.leanback.transition.SlideKitkat.1
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getTranslationX() - view.getWidth();
        }
    };
    public static final C02662 sCalculateTop = new CalculateSlideVertical() { // from class: androidx.leanback.transition.SlideKitkat.2
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getTranslationY() - view.getHeight();
        }
    };
    public static final C02673 sCalculateRight = new CalculateSlideHorizontal() { // from class: androidx.leanback.transition.SlideKitkat.3
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getTranslationX() + view.getWidth();
        }
    };
    public static final C02684 sCalculateBottom = new CalculateSlideVertical() { // from class: androidx.leanback.transition.SlideKitkat.4
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getTranslationY() + view.getHeight();
        }
    };
    public static final C02695 sCalculateStart = new CalculateSlideHorizontal() { // from class: androidx.leanback.transition.SlideKitkat.5
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getLayoutDirection() == 1 ? view.getTranslationX() + view.getWidth() : view.getTranslationX() - view.getWidth();
        }
    };
    public static final C02706 sCalculateEnd = new CalculateSlideHorizontal() { // from class: androidx.leanback.transition.SlideKitkat.6
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getGone(View view) {
            return view.getLayoutDirection() == 1 ? view.getTranslationX() - view.getWidth() : view.getTranslationX() + view.getWidth();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CalculateSlide {
        float getGone(View view);

        float getHere(View view);

        Property getProperty();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CalculateSlideHorizontal implements CalculateSlide {
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getHere(View view) {
            return view.getTranslationX();
        }

        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final Property getProperty() {
            return View.TRANSLATION_X;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CalculateSlideVertical implements CalculateSlide {
        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final float getHere(View view) {
            return view.getTranslationY();
        }

        @Override // androidx.leanback.transition.SlideKitkat.CalculateSlide
        public final Property getProperty() {
            return View.TRANSLATION_Y;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SlideAnimatorListener extends AnimatorListenerAdapter {
        public boolean mCanceled = false;
        public final float mEndValue;
        public final int mFinalVisibility;
        public float mPausedValue;
        public final Property mProp;
        public final float mTerminalValue;
        public final View mView;

        public SlideAnimatorListener(View view, Property<View, Float> property, float f, float f2, int i) {
            this.mProp = property;
            this.mView = view;
            this.mTerminalValue = f;
            this.mEndValue = f2;
            this.mFinalVisibility = i;
            view.setVisibility(0);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mView.setTag(R.id.lb_slide_transition_value, new float[]{this.mView.getTranslationX(), this.mView.getTranslationY()});
            this.mProp.set(this.mView, Float.valueOf(this.mTerminalValue));
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (!this.mCanceled) {
                this.mProp.set(this.mView, Float.valueOf(this.mTerminalValue));
            }
            this.mView.setVisibility(this.mFinalVisibility);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationPause(Animator animator) {
            this.mPausedValue = ((Float) this.mProp.get(this.mView)).floatValue();
            this.mProp.set(this.mView, Float.valueOf(this.mEndValue));
            this.mView.setVisibility(this.mFinalVisibility);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationResume(Animator animator) {
            this.mProp.set(this.mView, Float.valueOf(this.mPausedValue));
            this.mView.setVisibility(0);
        }
    }

    public SlideKitkat() {
        setSlideEdge(80);
    }

    public static Animator createAnimation(View view, Property property, float f, float f2, float f3, TimeInterpolator timeInterpolator, int i) {
        float[] fArr = (float[]) view.getTag(R.id.lb_slide_transition_value);
        if (fArr != null) {
            f = View.TRANSLATION_Y == property ? fArr[1] : fArr[0];
            view.setTag(R.id.lb_slide_transition_value, null);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f, f2);
        SlideAnimatorListener slideAnimatorListener = new SlideAnimatorListener(view, property, f3, f2, i);
        ofFloat.addListener(slideAnimatorListener);
        ofFloat.addPauseListener(slideAnimatorListener);
        ofFloat.setInterpolator(timeInterpolator);
        return ofFloat;
    }

    @Override // android.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        View view = transitionValues2 != null ? transitionValues2.view : null;
        if (view == null) {
            return null;
        }
        float here = this.mSlideCalculator.getHere(view);
        return createAnimation(view, this.mSlideCalculator.getProperty(), this.mSlideCalculator.getGone(view), here, here, sDecelerate, 0);
    }

    @Override // android.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        View view = transitionValues != null ? transitionValues.view : null;
        if (view == null) {
            return null;
        }
        float here = this.mSlideCalculator.getHere(view);
        return createAnimation(view, this.mSlideCalculator.getProperty(), here, this.mSlideCalculator.getGone(view), here, sAccelerate, 4);
    }

    public final void setSlideEdge(int i) {
        if (i == 3) {
            this.mSlideCalculator = sCalculateLeft;
            return;
        }
        if (i == 5) {
            this.mSlideCalculator = sCalculateRight;
            return;
        }
        if (i == 48) {
            this.mSlideCalculator = sCalculateTop;
            return;
        }
        if (i == 80) {
            this.mSlideCalculator = sCalculateBottom;
        } else if (i == 8388611) {
            this.mSlideCalculator = sCalculateStart;
        } else {
            if (i != 8388613) {
                throw new IllegalArgumentException("Invalid slide direction");
            }
            this.mSlideCalculator = sCalculateEnd;
        }
    }

    public SlideKitkat(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbSlide);
        setSlideEdge(obtainStyledAttributes.getInt(3, 80));
        long j = obtainStyledAttributes.getInt(1, -1);
        if (j >= 0) {
            setDuration(j);
        }
        long j2 = obtainStyledAttributes.getInt(2, -1);
        if (j2 > 0) {
            setStartDelay(j2);
        }
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, resourceId));
        }
        obtainStyledAttributes.recycle();
    }
}
