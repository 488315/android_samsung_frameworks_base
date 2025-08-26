package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes4.dex */
public class Rotate extends Transition {
    private static final String PROPNAME_ROTATION = "android:rotate:rotation";

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        View view = transitionValues2.view;
        float fFloatValue = ((Float) transitionValues.values.get(PROPNAME_ROTATION)).floatValue();
        float fFloatValue2 = ((Float) transitionValues2.values.get(PROPNAME_ROTATION)).floatValue();
        if (fFloatValue == fFloatValue2) {
            return null;
        }
        view.setRotation(fFloatValue);
        return ObjectAnimator.ofFloat(view, View.ROTATION, fFloatValue, fFloatValue2);
    }
}
