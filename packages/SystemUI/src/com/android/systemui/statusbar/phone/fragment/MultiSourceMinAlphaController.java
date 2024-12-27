package com.android.systemui.statusbar.phone.fragment;

import android.view.View;
import androidx.core.animation.Animator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(getMinAlpha(), f);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        ofFloat.mInterpolator = pathInterpolator;
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.MultiSourceMinAlphaController$animateToAlpha$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                MultiSourceMinAlphaController.this.updateAlpha(((Float) ofFloat.getAnimatedValue()).floatValue(), i);
            }
        });
        ofFloat.start(false);
        this.animators.put(2, ofFloat);
    }

    public final float getMinAlpha() {
        Float valueOf;
        Iterator it = ((LinkedHashMap) this.alphas).entrySet().iterator();
        if (it.hasNext()) {
            float floatValue = ((Number) ((Map.Entry) it.next()).getValue()).floatValue();
            while (it.hasNext()) {
                floatValue = Math.min(floatValue, ((Number) ((Map.Entry) it.next()).getValue()).floatValue());
            }
            valueOf = Float.valueOf(floatValue);
        } else {
            valueOf = null;
        }
        return valueOf != null ? valueOf.floatValue() : this.initialAlpha;
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
