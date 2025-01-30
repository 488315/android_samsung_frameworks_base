package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        }
        setText(this.mMessage);
        if (this.mAlwaysAnnounceText) {
            announceForAccessibility(this.mMessage);
        }
    }

    public final void switchIndication(CharSequence charSequence, KeyguardIndication keyguardIndication) {
        boolean z = (LsRune.AOD_FULLSCREEN && ((SecUnlockedScreenOffAnimationHelper) Dependency.get(SecUnlockedScreenOffAnimationHelper.class)).shouldSkipAnimation()) ? false : true;
        this.mMessage = charSequence;
        this.mKeyguardIndicationInfo = keyguardIndication;
        final Runnable runnable = null;
        if (!z) {
            setAlpha(1.0f);
            setTranslationY(0.0f);
            setNextIndication();
            Animator animator = this.mLastAnimator;
            if (animator != null) {
                animator.cancel();
                this.mLastAnimator = null;
                return;
            }
            return;
        }
        boolean z2 = (keyguardIndication == null || keyguardIndication.mIcon == null) ? false : true;
        AnimatorSet animatorSet = new AnimatorSet();
        if (!TextUtils.isEmpty(this.mMessage) || z2) {
            AnimatorSet animatorSet2 = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<KeyguardIndicationTextView, Float>) View.ALPHA, 1.0f);
            ofFloat.setStartDelay(!this.mAnimationsEnabled ? 0L : 150L);
            ofFloat.setDuration(!this.mAnimationsEnabled ? 0L : 317L);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, (Property<KeyguardIndicationTextView, Float>) View.TRANSLATION_Y, ((TextView) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_y_translation), 0.0f);
            ofFloat2.setDuration(this.mAnimationsEnabled ? 600L : 0L);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardIndicationTextView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    super.onAnimationCancel(animator2);
                    KeyguardIndicationTextView.this.setTranslationY(0.0f);
                    KeyguardIndicationTextView.this.setAlpha(1.0f);
                }
            });
            animatorSet2.playTogether(ofFloat2, ofFloat);
            animatorSet2.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.phone.KeyguardIndicationTextView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            animatorSet.playSequentially(getOutAnimator(), animatorSet2);
        } else {
            AnimatorSet outAnimator = getOutAnimator();
            outAnimator.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.phone.KeyguardIndicationTextView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            animatorSet.play(outAnimator);
        }
        Animator animator2 = this.mLastAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        this.mLastAnimator = animatorSet;
        animatorSet.start();
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
