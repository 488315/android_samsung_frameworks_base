package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.widget.SystemUITextView;

public class KeyguardIndicationTextView extends SystemUITextView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAlwaysAnnounceText;
    public boolean mAnimationsEnabled;
    public KeyguardIndication mKeyguardIndicationInfo;
    public Animator mLastAnimator;
    public CharSequence mMessage;

    public KeyguardIndicationTextView(Context context) {
        super(context);
        this.mAnimationsEnabled = false;
    }

    public final void clearMessages() {
        Animator animator = this.mLastAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.mMessage = "";
        setText("");
    }

    public final AnimatorSet getOutAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<KeyguardIndicationTextView, Float>) View.ALPHA, 0.0f);
        ofFloat.setDuration(!this.mAnimationsEnabled ? 0L : 167L);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardIndicationTextView.3
            public boolean mCancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.mCancelled = true;
                KeyguardIndicationTextView.this.setAlpha(0.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (this.mCancelled) {
                    return;
                }
                KeyguardIndicationTextView keyguardIndicationTextView = KeyguardIndicationTextView.this;
                int i = KeyguardIndicationTextView.$r8$clinit;
                keyguardIndicationTextView.setNextIndication();
            }
        });
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, (Property<KeyguardIndicationTextView, Float>) View.TRANSLATION_Y, 0.0f, -((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation));
        ofFloat2.setDuration(this.mAnimationsEnabled ? 167L : 0L);
        animatorSet.playTogether(ofFloat, ofFloat2);
        return animatorSet;
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Configuration configuration = getResources().getConfiguration();
        this.mOriginalFontSizeDp /= configuration.fontScale;
        updateFontSizeInKeyguardBoundary(true, configuration);
    }

    public void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
    }

    public final void setNextIndication() {
        boolean z;
        KeyguardIndication keyguardIndication = this.mKeyguardIndicationInfo;
        if (keyguardIndication != null) {
            setOnClickListener(keyguardIndication.mOnClickListener);
            setClickable(this.mKeyguardIndicationInfo.mOnClickListener != null);
            Drawable drawable = this.mKeyguardIndicationInfo.mIcon;
            if (drawable != null) {
                drawable.setTint(getCurrentTextColor());
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).start();
                }
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            z = this.mKeyguardIndicationInfo.mForceAccessibilityLiveRegionAssertive;
        } else {
            z = false;
        }
        if (!z) {
            setAccessibilityLiveRegion(0);
        }
        setText(this.mMessage);
        if (z) {
            setAccessibilityLiveRegion(2);
        }
        if (this.mAlwaysAnnounceText) {
            announceForAccessibility(this.mMessage);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void switchIndication(java.lang.CharSequence r12, com.android.systemui.keyguard.KeyguardIndication r13) {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardIndicationTextView.switchIndication(java.lang.CharSequence, com.android.systemui.keyguard.KeyguardIndication):void");
    }

    public KeyguardIndicationTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimationsEnabled = false;
    }

    public KeyguardIndicationTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimationsEnabled = false;
    }

    public KeyguardIndicationTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAnimationsEnabled = false;
    }
}
