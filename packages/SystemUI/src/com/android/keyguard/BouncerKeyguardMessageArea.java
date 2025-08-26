package com.android.keyguard;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import com.android.app.animation.Interpolators;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public class BouncerKeyguardMessageArea extends KeyguardMessageArea {
    public final long HIDE_DURATION_MILLIS;
    public final long SHOW_DURATION_MILLIS;
    public final AnimatorSet animatorSet;
    public CharSequence textAboutToShow;

    public BouncerKeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ColorStateList.valueOf(-1);
        this.animatorSet = new AnimatorSet();
        this.SHOW_DURATION_MILLIS = 150L;
        this.HIDE_DURATION_MILLIS = 200L;
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.mStyleResId, new int[]{R.attr.textColor});
        typedArrayObtainStyledAttributes.getColorStateList(0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public final void onThemeChanged() throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.mStyleResId, new int[]{R.attr.textColor});
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(0);
        typedArrayObtainStyledAttributes.recycle();
        if (colorStateList == null) {
            ColorStateList.valueOf(getContext().getColor(R.color.search_url_text_material_light));
        }
        update$1$1();
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public final void setMessage(final CharSequence charSequence, final boolean z) {
        if ((!Intrinsics.areEqual(charSequence, this.textAboutToShow) || charSequence == null) && !Intrinsics.areEqual(charSequence, getText())) {
            if (!z) {
                super.setMessage(charSequence, z);
                return;
            }
            this.textAboutToShow = charSequence;
            if (this.animatorSet.isRunning()) {
                this.animatorSet.cancel();
                this.textAboutToShow = null;
            }
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, (Property<BouncerKeyguardMessageArea, Float>) property, 1.0f, 0.0f);
            objectAnimatorOfFloat.setDuration(this.HIDE_DURATION_MILLIS);
            objectAnimatorOfFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea.setMessage.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    BouncerKeyguardMessageArea.super.setMessage(charSequence, z);
                }
            });
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, (Property<BouncerKeyguardMessageArea, Float>) property, 0.0f, 1.0f);
            objectAnimatorOfFloat2.setDuration(this.SHOW_DURATION_MILLIS);
            objectAnimatorOfFloat2.setInterpolator(Interpolators.STANDARD_DECELERATE);
            objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea.setMessage.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    BouncerKeyguardMessageArea.this.textAboutToShow = null;
                }
            });
            this.animatorSet.playSequentially(objectAnimatorOfFloat, objectAnimatorOfFloat2);
            this.animatorSet.start();
        }
    }
}
