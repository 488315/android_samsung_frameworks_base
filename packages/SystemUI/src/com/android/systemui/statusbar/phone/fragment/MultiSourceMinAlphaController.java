package com.android.systemui.statusbar.phone.fragment;

import android.view.View;
import androidx.core.animation.Animator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class MultiSourceMinAlphaController {
    public final Map alphas;
    public final Map animators;
    public final float initialAlpha;
    public final View view;

    public MultiSourceMinAlphaController(View view) {
        this(view, 0.0f, 2, null);
    }

    public final void animateToAlpha(float f, long j, PathInterpolator pathInterpolator, long j2) {
        final int i = 2;
        ValueAnimator valueAnimator = (ValueAnimator) ((LinkedHashMap) this.animators).get(2);
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(getMinAlpha(), f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setStartDelay(j2);
        valueAnimatorOfFloat.setInterpolator(pathInterpolator);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.MultiSourceMinAlphaController.animateToAlpha.1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                MultiSourceMinAlphaController.this.updateAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue(), i);
            }
        });
        valueAnimatorOfFloat.start(false);
        this.animators.put(2, valueAnimatorOfFloat);
    }

    public final float getMinAlpha() {
        Float fValueOf;
        Iterator it = ((LinkedHashMap) this.alphas).entrySet().iterator();
        if (it.hasNext()) {
            float fFloatValue = ((Number) ((Map.Entry) it.next()).getValue()).floatValue();
            while (it.hasNext()) {
                fFloatValue = Math.min(fFloatValue, ((Number) ((Map.Entry) it.next()).getValue()).floatValue());
            }
            fValueOf = Float.valueOf(fFloatValue);
        } else {
            fValueOf = null;
        }
        return fValueOf != null ? fValueOf.floatValue() : this.initialAlpha;
    }

    public final void updateAlpha(float f, int i) {
        this.alphas.put(Integer.valueOf(i), Float.valueOf(f));
        float minAlpha = getMinAlpha();
        this.view.setVisibility(minAlpha == 0.0f ? 4 : 0);
        this.view.setAlpha(minAlpha);
    }

    public MultiSourceMinAlphaController(View view, float f) {
        this.view = view;
        this.initialAlpha = f;
        this.alphas = new LinkedHashMap();
        this.animators = new LinkedHashMap();
    }

    public /* synthetic */ MultiSourceMinAlphaController(View view, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i & 2) != 0 ? 1.0f : f);
    }
}
