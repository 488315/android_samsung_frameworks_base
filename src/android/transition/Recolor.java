package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class Recolor extends Transition {
    private static final String PROPNAME_BACKGROUND = "android:recolor:background";
    private static final String PROPNAME_TEXT_COLOR = "android:recolor:textColor";

    public Recolor() {
    }

    public Recolor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.getBackground());
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR, Integer.valueOf(((TextView) transitionValues.view).getCurrentTextColor()));
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        View view = transitionValues2.view;
        Drawable drawable = (Drawable) transitionValues.values.get(PROPNAME_BACKGROUND);
        Drawable drawable2 = (Drawable) transitionValues2.values.get(PROPNAME_BACKGROUND);
        if ((drawable instanceof ColorDrawable) && (drawable2 instanceof ColorDrawable)) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            ColorDrawable colorDrawable2 = (ColorDrawable) drawable2;
            if (colorDrawable.getColor() != colorDrawable2.getColor()) {
                colorDrawable2.setColor(colorDrawable.getColor());
                return ObjectAnimator.ofArgb(drawable2, "color", colorDrawable.getColor(), colorDrawable2.getColor());
            }
        }
        if (!(view instanceof TextView)) {
            return null;
        }
        TextView textView = (TextView) view;
        int iIntValue = ((Integer) transitionValues.values.get(PROPNAME_TEXT_COLOR)).intValue();
        int iIntValue2 = ((Integer) transitionValues2.values.get(PROPNAME_TEXT_COLOR)).intValue();
        if (iIntValue == iIntValue2) {
            return null;
        }
        textView.setTextColor(iIntValue2);
        return ObjectAnimator.ofArgb(textView, "textColor", iIntValue, iIntValue2);
    }
}
