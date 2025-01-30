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
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                int i = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) parallax2.mEffects;
                    if (i >= arrayList.size()) {
                        return;
                    }
                    ParallaxEffect parallaxEffect = (ParallaxEffect) arrayList.get(i);
                    if (((ArrayList) parallaxEffect.mMarkerValues).size() >= 2) {
                        boolean z = parallaxEffect instanceof ParallaxEffect.IntEffect;
                        List list = parallax2.mProperties;
                        if (z) {
                            ArrayList arrayList2 = (ArrayList) list;
                            if (arrayList2.size() >= 2) {
                                int[] iArr = parallax2.mValues;
                                int i2 = iArr[0];
                                int i3 = 1;
                                while (i3 < arrayList2.size()) {
                                    int i4 = iArr[i3];
                                    if (i4 < i2) {
                                        Integer valueOf = Integer.valueOf(i3);
                                        String name = ((Property) arrayList2.get(i3)).getName();
                                        int i5 = i3 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", valueOf, name, Integer.valueOf(i5), ((Property) arrayList2.get(i5)).getName()));
                                    }
                                    if (i2 == Integer.MIN_VALUE && i4 == Integer.MAX_VALUE) {
                                        int i6 = i3 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i6), ((Property) arrayList2.get(i6)).getName(), Integer.valueOf(i3), ((Property) arrayList2.get(i3)).getName()));
                                    }
                                    i3++;
                                    i2 = i4;
                                }
                            }
                        } else {
                            ArrayList arrayList3 = (ArrayList) list;
                            if (arrayList3.size() >= 2) {
                                float[] fArr = parallax2.mFloatValues;
                                float f = fArr[0];
                                int i7 = 1;
                                while (i7 < arrayList3.size()) {
                                    float f2 = fArr[i7];
                                    if (f2 < f) {
                                        Integer valueOf2 = Integer.valueOf(i7);
                                        String name2 = ((Property) arrayList3.get(i7)).getName();
                                        int i8 = i7 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", valueOf2, name2, Integer.valueOf(i8), ((Property) arrayList3.get(i8)).getName()));
                                    }
                                    if (f == -3.4028235E38f && f2 == Float.MAX_VALUE) {
                                        int i9 = i7 - 1;
                                        throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i9), ((Property) arrayList3.get(i9)).getName(), Integer.valueOf(i7), ((Property) arrayList3.get(i7)).getName()));
                                    }
                                    i7++;
                                    f = f2;
                                }
                            }
                        }
                        int i10 = 0;
                        boolean z2 = false;
                        while (true) {
                            ArrayList arrayList4 = (ArrayList) parallaxEffect.mTargets;
                            if (i10 < arrayList4.size()) {
                                ((ParallaxTarget) arrayList4.get(i10)).getClass();
                                if (!z2) {
                                    parallaxEffect.calculateFraction(parallax2);
                                    z2 = true;
                                }
                                i10++;
                            }
                        }
                    }
                    i++;
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
