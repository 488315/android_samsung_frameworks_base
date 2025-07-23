package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Fade extends Visibility {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FadeAnimatorListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mLayerTypeChanged = false;
        public final View mView;

        public FadeAnimatorListener(View view) {
            this.mView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            ViewUtils.setTransitionAlpha(1.0f, this.mView);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            onAnimationEnd(animator, false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            if (this.mView.hasOverlappingRendering() && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(2, null);
            }
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            float f;
            if (this.mView.getVisibility() == 0) {
                View view = this.mView;
                ViewUtils.IMPL.getClass();
                f = view.getTransitionAlpha();
            } else {
                f = 0.0f;
            }
            this.mView.setTag(R.id.transition_pause_alpha, Float.valueOf(f));
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            this.mView.setTag(R.id.transition_pause_alpha, null);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, null);
            }
            if (z) {
                return;
            }
            ViewUtils.setTransitionAlpha(1.0f, this.mView);
            ViewUtils.IMPL.getClass();
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart$1(Transition transition) {
        }
    }

    public Fade(int i) {
        setMode(i);
    }

    public static float getStartAlpha(TransitionValues transitionValues, float f) {
        Float f2;
        return (transitionValues == null || (f2 = (Float) transitionValues.values.get("android:fade:transitionAlpha")) == null) ? f : f2.floatValue();
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        Visibility.captureValues$1(transitionValues);
        Float f = (Float) transitionValues.view.getTag(R.id.transition_pause_alpha);
        if (f == null) {
            if (transitionValues.view.getVisibility() == 0) {
                View view = transitionValues.view;
                ViewUtils.IMPL.getClass();
                f = Float.valueOf(view.getTransitionAlpha());
            } else {
                f = Float.valueOf(0.0f);
            }
        }
        ((HashMap) transitionValues.values).put("android:fade:transitionAlpha", f);
    }

    public final Animator createAnimation(View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        ViewUtils.setTransitionAlpha(f, view);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, ViewUtils.TRANSITION_ALPHA, f2);
        FadeAnimatorListener fadeAnimatorListener = new FadeAnimatorListener(view);
        ofFloat.addListener(fadeAnimatorListener);
        getRootTransition().addListener(fadeAnimatorListener);
        return ofFloat;
    }

    @Override // androidx.transition.Transition
    public final boolean isSeekingSupported() {
        return true;
    }

    @Override // androidx.transition.Visibility
    public final Animator onAppear(View view, TransitionValues transitionValues) {
        ViewUtils.IMPL.getClass();
        return createAnimation(view, getStartAlpha(transitionValues, 0.0f), 1.0f);
    }

    @Override // androidx.transition.Visibility
    public final Animator onDisappear(View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ViewUtils.IMPL.getClass();
        Animator createAnimation = createAnimation(view, getStartAlpha(transitionValues, 1.0f), 0.0f);
        if (createAnimation == null) {
            ViewUtils.setTransitionAlpha(getStartAlpha(transitionValues2, 1.0f), view);
        }
        return createAnimation;
    }

    public Fade() {
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.FADE);
        setMode(TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "fadingMode", 0, this.mMode));
        obtainStyledAttributes.recycle();
    }
}
