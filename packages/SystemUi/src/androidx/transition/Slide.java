package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.HashMap;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Slide extends Visibility {
    public CalculateSlide mSlideCalculator;
    public static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    public static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    public static final C05421 sCalculateLeft = new CalculateSlideHorizontal() { // from class: androidx.transition.Slide.1
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneX(View view, ViewGroup viewGroup) {
            return view.getTranslationX() - viewGroup.getWidth();
        }
    };
    public static final C05432 sCalculateStart = new CalculateSlideHorizontal() { // from class: androidx.transition.Slide.2
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneX(View view, ViewGroup viewGroup) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api17Impl.getLayoutDirection(viewGroup) == 1 ? view.getTranslationX() + viewGroup.getWidth() : view.getTranslationX() - viewGroup.getWidth();
        }
    };
    public static final C05443 sCalculateTop = new CalculateSlideVertical() { // from class: androidx.transition.Slide.3
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneY(View view, ViewGroup viewGroup) {
            return view.getTranslationY() - viewGroup.getHeight();
        }
    };
    public static final C05454 sCalculateRight = new CalculateSlideHorizontal() { // from class: androidx.transition.Slide.4
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneX(View view, ViewGroup viewGroup) {
            return view.getTranslationX() + viewGroup.getWidth();
        }
    };
    public static final C05465 sCalculateEnd = new CalculateSlideHorizontal() { // from class: androidx.transition.Slide.5
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneX(View view, ViewGroup viewGroup) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api17Impl.getLayoutDirection(viewGroup) == 1 ? view.getTranslationX() - viewGroup.getWidth() : view.getTranslationX() + viewGroup.getWidth();
        }
    };
    public static final C05476 sCalculateBottom = new CalculateSlideVertical() { // from class: androidx.transition.Slide.6
        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneY(View view, ViewGroup viewGroup) {
            return view.getTranslationY() + viewGroup.getHeight();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CalculateSlide {
        float getGoneX(View view, ViewGroup viewGroup);

        float getGoneY(View view, ViewGroup viewGroup);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneY(View view, ViewGroup viewGroup) {
            return view.getTranslationY();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        @Override // androidx.transition.Slide.CalculateSlide
        public final float getGoneX(View view, ViewGroup viewGroup) {
            return view.getTranslationX();
        }
    }

    public Slide() {
        this.mSlideCalculator = sCalculateBottom;
        setSlideEdge(80);
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        ((HashMap) transitionValues.values).put("android:slide:screenPosition", iArr);
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        ((HashMap) transitionValues.values).put("android:slide:screenPosition", iArr);
    }

    @Override // androidx.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) ((HashMap) transitionValues2.values).get("android:slide:screenPosition");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        return TranslationAnimationCreator.createAnimation(view, transitionValues2, iArr[0], iArr[1], this.mSlideCalculator.getGoneX(view, viewGroup), this.mSlideCalculator.getGoneY(view, viewGroup), translationX, translationY, sDecelerate, this);
    }

    @Override // androidx.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) ((HashMap) transitionValues.values).get("android:slide:screenPosition");
        return TranslationAnimationCreator.createAnimation(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.mSlideCalculator.getGoneX(view, viewGroup), this.mSlideCalculator.getGoneY(view, viewGroup), sAccelerate, this);
    }

    public final void setSlideEdge(int i) {
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
        } else {
            if (i != 8388613) {
                throw new IllegalArgumentException("Invalid slide direction");
            }
            this.mSlideCalculator = sCalculateEnd;
        }
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.mSide = i;
        this.mPropagation = sidePropagation;
    }

    public Slide(int i) {
        this.mSlideCalculator = sCalculateBottom;
        setSlideEdge(i);
    }

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSlideCalculator = sCalculateBottom;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.SLIDE);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlPullParser) attributeSet, "slideEdge", 0, 80);
        obtainStyledAttributes.recycle();
        setSlideEdge(namedInt);
    }
}
