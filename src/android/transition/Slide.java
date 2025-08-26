package android.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class Slide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final String TAG = "Slide";
    private CalculateSlide mSlideCalculator;
    private int mSlideEdge;
    private float mSlideFraction;
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    private static final CalculateSlide sCalculateLeft = new CalculateSlideHorizontal() { // from class: android.transition.Slide.1
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationX() - (viewGroup.getWidth() * f);
        }
    };
    private static final CalculateSlide sCalculateStart = new CalculateSlideHorizontal() { // from class: android.transition.Slide.2
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() + (viewGroup.getWidth() * f);
            }
            return view.getTranslationX() - (viewGroup.getWidth() * f);
        }
    };
    private static final CalculateSlide sCalculateTop = new CalculateSlideVertical() { // from class: android.transition.Slide.3
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationY() - (viewGroup.getHeight() * f);
        }
    };
    private static final CalculateSlide sCalculateRight = new CalculateSlideHorizontal() { // from class: android.transition.Slide.4
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationX() + (viewGroup.getWidth() * f);
        }
    };
    private static final CalculateSlide sCalculateEnd = new CalculateSlideHorizontal() { // from class: android.transition.Slide.5
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() - (viewGroup.getWidth() * f);
            }
            return view.getTranslationX() + (viewGroup.getWidth() * f);
        }
    };
    private static final CalculateSlide sCalculateBottom = new CalculateSlideVertical() { // from class: android.transition.Slide.6
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationY() + (viewGroup.getHeight() * f);
        }
    };

    private interface CalculateSlide {
        float getGoneX(ViewGroup viewGroup, View view, float f);

        float getGoneY(ViewGroup viewGroup, View view, float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationX();
        }
    }

    public Slide() {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        setSlideEdge(80);
    }

    public Slide(int i) {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        setSlideEdge(i);
    }

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Slide);
        int i = typedArrayObtainStyledAttributes.getInt(0, 80);
        typedArrayObtainStyledAttributes.recycle();
        setSlideEdge(i);
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, iArr);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int i) {
        if (i == 3) {
            this.mSlideCalculator = sCalculateLeft;
        } else if (i == 5) {
            this.mSlideCalculator = sCalculateRight;
        } else if (i == 48) {
            this.mSlideCalculator = sCalculateTop;
        } else if (i == 80) {
            this.mSlideCalculator = sCalculateBottom;
        } else if (i == 8388611) {
            this.mSlideCalculator = sCalculateStart;
        } else if (i == 8388613) {
            this.mSlideCalculator = sCalculateEnd;
        } else {
            throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = i;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(i);
        setPropagation(sidePropagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    @Override // android.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get(PROPNAME_SCREEN_POSITION);
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        return TranslationAnimationCreator.createAnimation(view, transitionValues2, iArr[0], iArr[1], this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction), this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction), translationX, translationY, sDecelerate, this);
    }

    @Override // android.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction), this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction), sAccelerate, this);
    }

    public void setSlideFraction(float f) {
        this.mSlideFraction = f;
    }
}
