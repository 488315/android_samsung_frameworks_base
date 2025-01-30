package com.android.keyguard;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final ColorStateList getColorInStyle() {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mStyleResId, new int[]{R.attr.textColor});
        ColorStateList colorStateList = obtainStyledAttributes != null ? obtainStyledAttributes.getColorStateList(0) : null;
        obtainStyledAttributes.recycle();
        return colorStateList;
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        getColorInStyle();
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public final void onThemeChanged() {
        if (getColorInStyle() == null) {
            Utils.getColorAttr(R.^attr-private.magnifierHeight, getContext());
        }
        update();
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
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<BouncerKeyguardMessageArea, Float>) View.ALPHA, 1.0f, 0.0f);
            ofFloat.setDuration(this.HIDE_DURATION_MILLIS);
            ofFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea$setMessage$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super/*com.android.keyguard.KeyguardMessageArea*/.setMessage(charSequence, z);
                }
            });
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, (Property<BouncerKeyguardMessageArea, Float>) View.ALPHA, 0.0f, 1.0f);
            ofFloat2.setDuration(this.SHOW_DURATION_MILLIS);
            ofFloat2.setInterpolator(Interpolators.STANDARD_DECELERATE);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea$setMessage$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    BouncerKeyguardMessageArea.this.textAboutToShow = null;
                }
            });
            this.animatorSet.playSequentially(ofFloat, ofFloat2);
            this.animatorSet.start();
        }
    }

    @Override // com.android.keyguard.SecurityMessageDisplay
    public final void setNextMessageColor(ColorStateList colorStateList) {
    }
}
