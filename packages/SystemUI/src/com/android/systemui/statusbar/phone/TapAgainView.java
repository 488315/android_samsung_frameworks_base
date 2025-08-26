package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class TapAgainView extends TextView {
    public TapAgainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void animateIn() throws Resources.NotFoundException {
        int dimensionPixelSize = ((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.ALPHA, 1.0f);
        objectAnimatorOfFloat.setStartDelay(150L);
        objectAnimatorOfFloat.setDuration(317L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.TRANSLATION_Y, dimensionPixelSize, 0.0f);
        objectAnimatorOfFloat2.setDuration(600L);
        objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.TapAgainView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                TapAgainView.this.setTranslationY(0.0f);
            }
        });
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
        setVisibility(0);
    }

    public final void animateOut() throws Resources.NotFoundException {
        int dimensionPixelSize = ((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.ALPHA, 0.0f);
        objectAnimatorOfFloat.setDuration(167L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.TRANSLATION_Y, 0.0f, -dimensionPixelSize);
        objectAnimatorOfFloat2.setDuration(167L);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.TapAgainView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                TapAgainView.this.setVisibility(8);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TapAgainView.this.setVisibility(8);
            }
        });
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateColor();
    }

    public final void updateColor() {
        setTextColor(((TextView) this).mContext.getColor(android.R.color.search_url_text_material_light));
        setBackground(getResources().getDrawable(R.drawable.rounded_bg_full, ((TextView) this).mContext.getTheme()));
    }
}
