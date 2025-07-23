package android.transition;

import android.graphics.Rect;
import android.view.ViewGroup;

/* loaded from: classes4.dex */
public class CircularPropagation extends VisibilityPropagation {
    private static final String TAG = "CircularPropagation";
    private float mPropagationSpeed = 3.0f;

    public void setPropagationSpeed(float f) {
        if (f == 0.0f) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.mPropagationSpeed = f;
    }

    @Override // android.transition.TransitionPropagation
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int round;
        int i2;
        if (transitionValues == null && transitionValues2 == null) {
            return 0L;
        }
        if (transitionValues2 == null || getViewVisibility(transitionValues) == 0) {
            i = -1;
        } else {
            transitionValues = transitionValues2;
            i = 1;
        }
        int viewX = getViewX(transitionValues);
        int viewY = getViewY(transitionValues);
        Rect epicenter = transition.getEpicenter();
        if (epicenter != null) {
            i2 = epicenter.centerX();
            round = epicenter.centerY();
        } else {
            viewGroup.getLocationOnScreen(new int[2]);
            int round2 = Math.round(r5[0] + (viewGroup.getWidth() / 2) + viewGroup.getTranslationX());
            round = Math.round(r5[1] + (viewGroup.getHeight() / 2) + viewGroup.getTranslationY());
            i2 = round2;
        }
        double distance = distance(viewX, viewY, i2, round) / distance(0.0f, 0.0f, viewGroup.getWidth(), viewGroup.getHeight());
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return Math.round(((duration * i) / this.mPropagationSpeed) * distance);
    }

    private static double distance(float f, float f2, float f3, float f4) {
        return Math.hypot(f3 - f, f4 - f2);
    }
}
