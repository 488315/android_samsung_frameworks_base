package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Fade extends Visibility {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FadeAnimatorListener extends AnimatorListenerAdapter {
        public boolean mLayerTypeChanged = false;
        public final View mView;

        public FadeAnimatorListener(View view) {
            this.mView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            ViewUtils.setTransitionAlpha(this.mView, 1.0f);
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, null);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            View view = this.mView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.hasOverlappingRendering(view) && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(2, null);
            }
        }
    }

    public Fade(int i) {
        if ((i & (-4)) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        Map map = transitionValues.values;
        ViewUtils.IMPL.getClass();
        ((HashMap) map).put("android:fade:transitionAlpha", Float.valueOf(transitionValues.view.getTransitionAlpha()));
    }

    public final Animator createAnimation(final View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        ViewUtils.setTransitionAlpha(view, f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, ViewUtils.TRANSITION_ALPHA, f2);
        ofFloat.addListener(new FadeAnimatorListener(view));
        addListener(new TransitionListenerAdapter(this) { // from class: androidx.transition.Fade.1
            @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                ViewUtils.setTransitionAlpha(view, 1.0f);
                ViewUtils.IMPL.getClass();
                transition.removeListener(this);
            }
        });
        return ofFloat;
    }

    @Override // androidx.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Float f;
        float floatValue = (transitionValues == null || (f = (Float) ((HashMap) transitionValues.values).get("android:fade:transitionAlpha")) == null) ? 0.0f : f.floatValue();
        return createAnimation(view, floatValue != 1.0f ? floatValue : 0.0f, 1.0f);
    }

    @Override // androidx.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        Float f;
        ViewUtils.IMPL.getClass();
        return createAnimation(view, (transitionValues == null || (f = (Float) ((HashMap) transitionValues.values).get("android:fade:transitionAlpha")) == null) ? 1.0f : f.floatValue(), 0.0f);
    }

    public Fade() {
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.FADE);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "fadingMode", 0, this.mMode);
        if ((namedInt & (-4)) == 0) {
            this.mMode = namedInt;
            obtainStyledAttributes.recycle();
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }
}
