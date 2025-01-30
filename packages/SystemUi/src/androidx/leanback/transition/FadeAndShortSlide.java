package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.leanback.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class FadeAndShortSlide extends Visibility {
    public final float mDistance;
    public Visibility mFade;
    public CalculateSlide mSlideCalculator;
    public final C02636 sCalculateTopBottom;
    public static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    public static final C02581 sCalculateStart = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.1
        @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
        public final float getGoneX(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            if (viewGroup.getLayoutDirection() == 1) {
                return fadeAndShortSlide.getHorizontalDistance(viewGroup) + view.getTranslationX();
            }
            return view.getTranslationX() - fadeAndShortSlide.getHorizontalDistance(viewGroup);
        }
    };
    public static final C02592 sCalculateEnd = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.2
        @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
        public final float getGoneX(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            return viewGroup.getLayoutDirection() == 1 ? view.getTranslationX() - fadeAndShortSlide.getHorizontalDistance(viewGroup) : view.getTranslationX() + fadeAndShortSlide.getHorizontalDistance(viewGroup);
        }
    };
    public static final C02603 sCalculateStartEnd = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.3
        @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
        public final float getGoneX(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            int centerX;
            int width = (view.getWidth() / 2) + iArr[0];
            viewGroup.getLocationOnScreen(iArr);
            Rect epicenter = fadeAndShortSlide.getEpicenter();
            if (epicenter == null) {
                centerX = (viewGroup.getWidth() / 2) + iArr[0];
            } else {
                centerX = epicenter.centerX();
            }
            if (width < centerX) {
                return view.getTranslationX() - fadeAndShortSlide.getHorizontalDistance(viewGroup);
            }
            return fadeAndShortSlide.getHorizontalDistance(viewGroup) + view.getTranslationX();
        }
    };
    public static final C02614 sCalculateBottom = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.4
        @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
        public final float getGoneY(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            return fadeAndShortSlide.getVerticalDistance(viewGroup) + view.getTranslationY();
        }
    };
    public static final C02625 sCalculateTop = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.5
        @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
        public final float getGoneY(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            return view.getTranslationY() - fadeAndShortSlide.getVerticalDistance(viewGroup);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CalculateSlide {
        public float getGoneX(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            return view.getTranslationX();
        }

        public float getGoneY(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
            return view.getTranslationY();
        }
    }

    public FadeAndShortSlide() {
        this(8388611);
    }

    @Override // android.transition.Transition
    public final Transition addListener(Transition.TransitionListener transitionListener) {
        this.mFade.addListener(transitionListener);
        return super.addListener(transitionListener);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        this.mFade.captureEndValues(transitionValues);
        super.captureEndValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:fadeAndShortSlideTransition:screenPosition", iArr);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        this.mFade.captureStartValues(transitionValues);
        super.captureStartValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:fadeAndShortSlideTransition:screenPosition", iArr);
    }

    public final float getHorizontalDistance(ViewGroup viewGroup) {
        float f = this.mDistance;
        return f >= 0.0f ? f : viewGroup.getWidth() / 4;
    }

    public final float getVerticalDistance(ViewGroup viewGroup) {
        float f = this.mDistance;
        return f >= 0.0f ? f : viewGroup.getHeight() / 4;
    }

    @Override // android.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null || viewGroup == view) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get("android:fadeAndShortSlideTransition:screenPosition");
        int i = iArr[0];
        int i2 = iArr[1];
        float translationX = view.getTranslationX();
        Animator createAnimation = TranslationAnimationCreator.createAnimation(view, transitionValues2, i, i2, this.mSlideCalculator.getGoneX(this, viewGroup, view, iArr), this.mSlideCalculator.getGoneY(this, viewGroup, view, iArr), translationX, view.getTranslationY(), sDecelerate, this);
        Animator onAppear = this.mFade.onAppear(viewGroup, view, transitionValues, transitionValues2);
        if (createAnimation == null) {
            return onAppear;
        }
        if (onAppear == null) {
            return createAnimation;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(createAnimation).with(onAppear);
        return animatorSet;
    }

    @Override // android.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || viewGroup == view) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get("android:fadeAndShortSlideTransition:screenPosition");
        Animator createAnimation = TranslationAnimationCreator.createAnimation(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.mSlideCalculator.getGoneX(this, viewGroup, view, iArr), this.mSlideCalculator.getGoneY(this, viewGroup, view, iArr), sDecelerate, this);
        Animator onDisappear = this.mFade.onDisappear(viewGroup, view, transitionValues, transitionValues2);
        if (createAnimation == null) {
            return onDisappear;
        }
        if (onDisappear == null) {
            return createAnimation;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(createAnimation).with(onDisappear);
        return animatorSet;
    }

    @Override // android.transition.Transition
    public final Transition removeListener(Transition.TransitionListener transitionListener) {
        this.mFade.removeListener(transitionListener);
        return super.removeListener(transitionListener);
    }

    @Override // android.transition.Transition
    public final void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        this.mFade.setEpicenterCallback(epicenterCallback);
        super.setEpicenterCallback(epicenterCallback);
    }

    public final void setSlideEdge(int i) {
        if (i == 48) {
            this.mSlideCalculator = sCalculateTop;
            return;
        }
        if (i == 80) {
            this.mSlideCalculator = sCalculateBottom;
            return;
        }
        if (i == 112) {
            this.mSlideCalculator = this.sCalculateTopBottom;
            return;
        }
        if (i == 8388611) {
            this.mSlideCalculator = sCalculateStart;
        } else if (i == 8388613) {
            this.mSlideCalculator = sCalculateEnd;
        } else {
            if (i != 8388615) {
                throw new IllegalArgumentException("Invalid slide direction");
            }
            this.mSlideCalculator = sCalculateStartEnd;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.leanback.transition.FadeAndShortSlide$6] */
    public FadeAndShortSlide(int i) {
        this.mFade = new Fade();
        this.mDistance = -1.0f;
        this.sCalculateTopBottom = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.6
            @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
            public final float getGoneY(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
                int centerY;
                int height = (view.getHeight() / 2) + iArr[1];
                viewGroup.getLocationOnScreen(iArr);
                Rect epicenter = FadeAndShortSlide.this.getEpicenter();
                if (epicenter == null) {
                    centerY = (viewGroup.getHeight() / 2) + iArr[1];
                } else {
                    centerY = epicenter.centerY();
                }
                if (height < centerY) {
                    return view.getTranslationY() - fadeAndShortSlide.getVerticalDistance(viewGroup);
                }
                return fadeAndShortSlide.getVerticalDistance(viewGroup) + view.getTranslationY();
            }
        };
        setSlideEdge(i);
    }

    @Override // android.transition.Transition
    public final Transition clone() {
        FadeAndShortSlide fadeAndShortSlide = (FadeAndShortSlide) super.clone();
        fadeAndShortSlide.mFade = (Visibility) this.mFade.clone();
        return fadeAndShortSlide;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.leanback.transition.FadeAndShortSlide$6] */
    public FadeAndShortSlide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFade = new Fade();
        this.mDistance = -1.0f;
        this.sCalculateTopBottom = new CalculateSlide() { // from class: androidx.leanback.transition.FadeAndShortSlide.6
            @Override // androidx.leanback.transition.FadeAndShortSlide.CalculateSlide
            public final float getGoneY(FadeAndShortSlide fadeAndShortSlide, ViewGroup viewGroup, View view, int[] iArr) {
                int centerY;
                int height = (view.getHeight() / 2) + iArr[1];
                viewGroup.getLocationOnScreen(iArr);
                Rect epicenter = FadeAndShortSlide.this.getEpicenter();
                if (epicenter == null) {
                    centerY = (viewGroup.getHeight() / 2) + iArr[1];
                } else {
                    centerY = epicenter.centerY();
                }
                if (height < centerY) {
                    return view.getTranslationY() - fadeAndShortSlide.getVerticalDistance(viewGroup);
                }
                return fadeAndShortSlide.getVerticalDistance(viewGroup) + view.getTranslationY();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbSlide);
        setSlideEdge(obtainStyledAttributes.getInt(3, 8388611));
        obtainStyledAttributes.recycle();
    }
}
