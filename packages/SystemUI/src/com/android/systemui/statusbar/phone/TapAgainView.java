package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.wm.shell.animation.Interpolators;

public class TapAgainView extends TextView {
    public TapAgainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void animateIn() {
        int dimensionPixelSize = ((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.ALPHA, 1.0f);
        ofFloat.setStartDelay(150L);
        ofFloat.setDuration(317L);
        ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.TRANSLATION_Y, dimensionPixelSize, 0.0f);
        ofFloat2.setDuration(600L);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.TapAgainView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                TapAgainView.this.setTranslationY(0.0f);
            }
        });
        animatorSet.playTogether(ofFloat2, ofFloat);
        animatorSet.start();
        setVisibility(0);
    }

    public final void animateOut() {
        int dimensionPixelSize = ((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.ALPHA, 0.0f);
        ofFloat.setDuration(167L);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, (Property<TapAgainView, Float>) View.TRANSLATION_Y, 0.0f, -dimensionPixelSize);
        ofFloat2.setDuration(167L);
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
        animatorSet.playTogether(ofFloat2, ofFloat);
        animatorSet.start();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateColor();
    }

    public final void updateColor() {
        setTextColor(Utils.getColorAttrDefaultColor(((TextView) this).mContext, android.R.^attr-private.materialColorOnTertiary, 0));
        setBackground(getResources().getDrawable(R.drawable.rounded_bg_full, ((TextView) this).mContext.getTheme()));
    }
}
