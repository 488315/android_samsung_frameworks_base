package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.PasswordTextView;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.vibrate.VibrationUtil;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardPinBasedInputViewController extends KeyguardSecAbsKeyInputViewController {
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 mActionButtonTouchListener;
    public final FalsingCollector mFalsingCollector;
    public final LiftToActivateListener mLiftToActivateListener;
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0 mOnKeyListener;
    public final PasswordTextView mPasswordEntry;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0] */
    public KeyguardPinBasedInputViewController(KeyguardPinBasedInputView keyguardPinBasedInputView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager) {
        super(keyguardPinBasedInputView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = KeyguardPinBasedInputViewController.this;
                keyguardPinBasedInputViewController.getClass();
                if (keyEvent.getAction() == 0) {
                    return ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).onKeyDown(i, keyEvent);
                }
                return false;
            }
        };
        this.mActionButtonTouchListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this, 0);
        this.mLiftToActivateListener = liftToActivateListener;
        this.mFalsingCollector = falsingCollector;
        KeyguardPinBasedInputView keyguardPinBasedInputView2 = (KeyguardPinBasedInputView) this.mView;
        this.mPasswordEntry = (PasswordTextView) keyguardPinBasedInputView2.findViewById(keyguardPinBasedInputView2.getPasswordTextViewId());
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R.string.keyguard_enter_your_pin;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        PasswordTextView passwordTextView = this.mPasswordEntry;
        passwordTextView.clearFocus();
        passwordTextView.requestFocus();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        boolean isPinEnhancedPrivacyEnabled = this.mLockPatternUtils.isPinEnhancedPrivacyEnabled(KeyguardUpdateMonitor.getCurrentUser());
        final int i = 1;
        PasswordTextView passwordTextView = this.mPasswordEntry;
        passwordTextView.mShowPassword = !isPinEnhancedPrivacyEnabled;
        final int i2 = 0;
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) this.mView).mButtons) {
            numPadKey.setOnTouchListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this, i));
        }
        passwordTextView.setOnKeyListener(this.mOnKeyListener);
        passwordTextView.mUserActivityListener = new PasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda2
            @Override // com.android.keyguard.PasswordTextView.UserActivityListener
            public final void onUserActivity() {
                KeyguardPinBasedInputViewController.this.onUserInput();
            }
        };
        View findViewById = ((KeyguardPinBasedInputView) this.mView).findViewById(R.id.delete_button);
        KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 keyguardPinBasedInputViewController$$ExternalSyntheticLambda1 = this.mActionButtonTouchListener;
        findViewById.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
        findViewById.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3
            public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        PasswordTextView passwordTextView2 = this.f$0.mPasswordEntry;
                        if (passwordTextView2.isEnabled()) {
                            passwordTextView2.deleteLastChar();
                            break;
                        }
                        break;
                    default:
                        KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
                        if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                            keyguardPinBasedInputViewController.verifyPasswordAndUnlock();
                            break;
                        }
                        break;
                }
            }
        });
        findViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = KeyguardPinBasedInputViewController.this;
                if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                    ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).resetPasswordText(true, true);
                }
                ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).doHapticKeyClick();
                return true;
            }
        });
        View findViewById2 = ((KeyguardPinBasedInputView) this.mView).findViewById(R.id.key_enter);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
            findViewById2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i) {
                        case 0:
                            PasswordTextView passwordTextView2 = this.f$0.mPasswordEntry;
                            if (passwordTextView2.isEnabled()) {
                                passwordTextView2.deleteLastChar();
                                break;
                            }
                            break;
                        default:
                            KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
                            if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                                keyguardPinBasedInputViewController.verifyPasswordAndUnlock();
                                break;
                            }
                            break;
                    }
                }
            });
            findViewById2.setOnHoverListener(this.mLiftToActivateListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) this.mView).mButtons) {
            numPadKey.setOnTouchListener(null);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        ((KeyguardPinBasedInputView) this.mView).setPasswordEntryEnabled(true);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void startErrorAnimation() {
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) this.mView;
        keyguardPinBasedInputView.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final int i = 1;
        for (int i2 = 1; i2 <= 9; i2++) {
            arrayList2.add(keyguardPinBasedInputView.mButtons[i2]);
        }
        arrayList2.add(keyguardPinBasedInputView.mDeleteButton);
        final int i3 = 0;
        arrayList2.add(keyguardPinBasedInputView.mButtons[0]);
        arrayList2.add(keyguardPinBasedInputView.mOkButton);
        int i4 = 0;
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            final View view = (View) arrayList2.get(i5);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setStartDelay(i4);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.8f);
            Interpolator interpolator = Interpolators.STANDARD;
            ofFloat.setInterpolator(interpolator);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i3) {
                        case 0:
                            View view2 = view;
                            int i6 = KeyguardPinBasedInputView.$r8$clinit;
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            View view3 = view;
                            int i7 = KeyguardPinBasedInputView.$r8$clinit;
                            view3.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view3.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            ofFloat.setDuration(50L);
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i) {
                        case 0:
                            View view2 = view;
                            int i6 = KeyguardPinBasedInputView.$r8$clinit;
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            View view3 = view;
                            int i7 = KeyguardPinBasedInputView.$r8$clinit;
                            view3.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view3.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            ofFloat2.setDuration(617L);
            animatorSet2.playSequentially(ofFloat, ofFloat2);
            arrayList.add(animatorSet2);
            i4 += 33;
        }
        animatorSet.playTogether(arrayList);
        animatorSet.start();
    }
}
