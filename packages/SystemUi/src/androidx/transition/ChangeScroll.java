package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ChangeScroll extends Transition {
    public static final String[] PROPERTIES = {"android:changeScroll:x", "android:changeScroll:y"};

    public ChangeScroll() {
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureValues(TransitionValues transitionValues) {
        Map map = transitionValues.values;
        View view = transitionValues.view;
        ((HashMap) map).put("android:changeScroll:x", Integer.valueOf(view.getScrollX()));
        ((HashMap) map).put("android:changeScroll:y", Integer.valueOf(view.getScrollY()));
    }

    @Override // androidx.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2 = null;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        HashMap hashMap = (HashMap) transitionValues.values;
        int intValue = ((Integer) hashMap.get("android:changeScroll:x")).intValue();
        HashMap hashMap2 = (HashMap) transitionValues2.values;
        int intValue2 = ((Integer) hashMap2.get("android:changeScroll:x")).intValue();
        int intValue3 = ((Integer) hashMap.get("android:changeScroll:y")).intValue();
        int intValue4 = ((Integer) hashMap2.get("android:changeScroll:y")).intValue();
        View view = transitionValues2.view;
        if (intValue != intValue2) {
            view.setScrollX(intValue);
            objectAnimator = ObjectAnimator.ofInt(view, "scrollX", intValue, intValue2);
        } else {
            objectAnimator = null;
        }
        if (intValue3 != intValue4) {
            view.setScrollY(intValue3);
            objectAnimator2 = ObjectAnimator.ofInt(view, "scrollY", intValue3, intValue4);
        }
        if (objectAnimator == null) {
            return objectAnimator2;
        }
        if (objectAnimator2 == null) {
            return objectAnimator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, objectAnimator2);
        return animatorSet;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return PROPERTIES;
    }

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
