package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.leanback.widget.Parallax;
import androidx.leanback.widget.ParallaxEffect;
import androidx.leanback.widget.ParallaxTarget;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ParallaxTransition extends Visibility {
    public static final Interpolator sInterpolator = new LinearInterpolator();

    public ParallaxTransition() {
    }

    public final Animator createAnimator(View view) {
        final Parallax parallax = (Parallax) view.getTag(R.id.lb_parallax_source);
        if (parallax == null) {
            return null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(sInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: androidx.leanback.transition.ParallaxTransition.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Parallax parallax2 = parallax;
                for (int i = 0; i < ((ArrayList) parallax2.mEffects).size(); i++) {
                    ParallaxEffect parallaxEffect = (ParallaxEffect) ((ArrayList) parallax2.mEffects).get(i);
                    if (((ArrayList) parallaxEffect.mMarkerValues).size() >= 2) {
                        if (parallaxEffect instanceof ParallaxEffect.IntEffect) {
                            if (((ArrayList) parallax2.mProperties).size() >= 2) {
                                int[] iArr = parallax2.mValues;
                                int i2 = iArr[0];
                                int i3 = 1;
                                while (i3 < ((ArrayList) parallax2.mProperties).size()) {
                                    int i4 = iArr[i3];
                                    if (i4 < i2) {
                                        Integer valueOf = Integer.valueOf(i3);
                                        String name = ((Property) ((ArrayList) parallax2.mProperties).get(i3)).getName();
                                        int i5 = i3 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", valueOf, name, Integer.valueOf(i5), ((Property) ((ArrayList) parallax2.mProperties).get(i5)).getName()));
                                    }
                                    if (i2 == Integer.MIN_VALUE && i4 == Integer.MAX_VALUE) {
                                        int i6 = i3 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i6), ((Property) ((ArrayList) parallax2.mProperties).get(i6)).getName(), Integer.valueOf(i3), ((Property) ((ArrayList) parallax2.mProperties).get(i3)).getName()));
                                    }
                                    i3++;
                                    i2 = i4;
                                }
                            }
                        } else if (((ArrayList) parallax2.mProperties).size() >= 2) {
                            float[] fArr = parallax2.mFloatValues;
                            float f = fArr[0];
                            int i7 = 1;
                            while (i7 < ((ArrayList) parallax2.mProperties).size()) {
                                float f2 = fArr[i7];
                                if (f2 < f) {
                                    Integer valueOf2 = Integer.valueOf(i7);
                                    String name2 = ((Property) ((ArrayList) parallax2.mProperties).get(i7)).getName();
                                    int i8 = i7 - 1;
                                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", valueOf2, name2, Integer.valueOf(i8), ((Property) ((ArrayList) parallax2.mProperties).get(i8)).getName()));
                                }
                                if (f == -3.4028235E38f && f2 == Float.MAX_VALUE) {
                                    int i9 = i7 - 1;
                                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i9), ((Property) ((ArrayList) parallax2.mProperties).get(i9)).getName(), Integer.valueOf(i7), ((Property) ((ArrayList) parallax2.mProperties).get(i7)).getName()));
                                }
                                i7++;
                                f = f2;
                            }
                        }
                        boolean z = false;
                        for (int i10 = 0; i10 < ((ArrayList) parallaxEffect.mTargets).size(); i10++) {
                            ((ParallaxTarget) ((ArrayList) parallaxEffect.mTargets).get(i10)).getClass();
                            if (!z) {
                                parallaxEffect.calculateFraction(parallax2);
                                z = true;
                            }
                        }
                    }
                }
            }
        });
        return ofFloat;
    }

    @Override // android.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        return createAnimator(view);
    }

    @Override // android.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        return createAnimator(view);
    }

    public ParallaxTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
