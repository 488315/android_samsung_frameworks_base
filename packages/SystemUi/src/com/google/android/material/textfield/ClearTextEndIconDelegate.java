package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ClearTextEndIconDelegate extends EndIconDelegate {
    public EditText editText;
    public AnimatorSet iconInAnim;
    public ValueAnimator iconOutAnim;
    public final ClearTextEndIconDelegate$$ExternalSyntheticLambda3 onFocusChangeListener;
    public final ClearTextEndIconDelegate$$ExternalSyntheticLambda2 onIconClickListener;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda3] */
    public ClearTextEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
        this.onIconClickListener = new View.OnClickListener() { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
                EditText editText = clearTextEndIconDelegate.editText;
                if (editText == null) {
                    return;
                }
                Editable text = editText.getText();
                if (text != null) {
                    text.clear();
                }
                clearTextEndIconDelegate.refreshIconState();
            }
        };
        this.onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda3
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
                clearTextEndIconDelegate.animateIcon(clearTextEndIconDelegate.shouldBeVisible());
            }
        };
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void afterEditTextChanged() {
        if (this.endLayout.suffixText != null) {
            return;
        }
        animateIcon(shouldBeVisible());
    }

    public final void animateIcon(boolean z) {
        boolean z2 = this.endLayout.isEndIconVisible() == z;
        if (z && !this.iconInAnim.isRunning()) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (z2) {
                this.iconInAnim.end();
                return;
            }
            return;
        }
        if (z) {
            return;
        }
        this.iconInAnim.cancel();
        this.iconOutAnim.start();
        if (z2) {
            this.iconOutAnim.end();
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconContentDescriptionResId() {
        return R.string.clear_text_end_icon_content_description;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconDrawableResId() {
        return R.drawable.mtrl_ic_cancel;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnFocusChangeListener getOnIconViewFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onEditTextAttached(EditText editText) {
        this.editText = editText;
        this.textInputLayout.endLayout.setEndIconVisible(shouldBeVisible());
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onSuffixVisibilityChanged(boolean z) {
        if (this.endLayout.suffixText == null) {
            return;
        }
        animateIcon(z);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.8f, 1.0f);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        ofFloat.setDuration(150L);
        final int i = 0;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda1
            public final /* synthetic */ ClearTextEndIconDelegate f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i) {
                    case 0:
                        ClearTextEndIconDelegate clearTextEndIconDelegate = this.f$0;
                        clearTextEndIconDelegate.getClass();
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = clearTextEndIconDelegate.endIconView;
                        checkableImageButton.setScaleX(floatValue);
                        checkableImageButton.setScaleY(floatValue);
                        break;
                    default:
                        ClearTextEndIconDelegate clearTextEndIconDelegate2 = this.f$0;
                        clearTextEndIconDelegate2.getClass();
                        clearTextEndIconDelegate2.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                }
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        ofFloat2.setInterpolator(timeInterpolator);
        ofFloat2.setDuration(100L);
        final int i2 = 1;
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda1
            public final /* synthetic */ ClearTextEndIconDelegate f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        ClearTextEndIconDelegate clearTextEndIconDelegate = this.f$0;
                        clearTextEndIconDelegate.getClass();
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = clearTextEndIconDelegate.endIconView;
                        checkableImageButton.setScaleX(floatValue);
                        checkableImageButton.setScaleY(floatValue);
                        break;
                    default:
                        ClearTextEndIconDelegate clearTextEndIconDelegate2 = this.f$0;
                        clearTextEndIconDelegate2.getClass();
                        clearTextEndIconDelegate2.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        this.iconInAnim = animatorSet;
        animatorSet.playTogether(ofFloat, ofFloat2);
        this.iconInAnim.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(true);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat3.setInterpolator(timeInterpolator);
        ofFloat3.setDuration(100L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda1
            public final /* synthetic */ ClearTextEndIconDelegate f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        ClearTextEndIconDelegate clearTextEndIconDelegate = this.f$0;
                        clearTextEndIconDelegate.getClass();
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = clearTextEndIconDelegate.endIconView;
                        checkableImageButton.setScaleX(floatValue);
                        checkableImageButton.setScaleY(floatValue);
                        break;
                    default:
                        ClearTextEndIconDelegate clearTextEndIconDelegate2 = this.f$0;
                        clearTextEndIconDelegate2.getClass();
                        clearTextEndIconDelegate2.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                }
            }
        });
        this.iconOutAnim = ofFloat3;
        ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(false);
            }
        });
    }

    public final boolean shouldBeVisible() {
        EditText editText = this.editText;
        return editText != null && (editText.hasFocus() || this.endIconView.hasFocus()) && this.editText.getText().length() > 0;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void tearDown() {
        EditText editText = this.editText;
        if (editText != null) {
            editText.post(new Runnable() { // from class: com.google.android.material.textfield.ClearTextEndIconDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ClearTextEndIconDelegate.this.animateIcon(true);
                }
            });
        }
    }
}
