package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.BaseSecPasswordTextView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.vibrate.VibrationUtil;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class KeyguardPinBasedInputViewController extends KeyguardSecAbsKeyInputViewController {
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 mActionButtonTouchListener;
    public final FalsingCollector mFalsingCollector;
    public final KeyguardKeyboardInteractor mKeyguardKeyboardInteractor;
    public final LiftToActivateListener mLiftToActivateListener;
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0 mOnKeyListener;
    public final PasswordTextView mPasswordEntry;

    public static /* synthetic */ void $r8$lambda$1BZ0HKp0hpgY_5GG7usgwHBArEI(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, MotionEvent motionEvent) {
        keyguardPinBasedInputViewController.getClass();
        if (motionEvent.getActionMasked() == 0) {
            ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).doHapticKeyClick();
        }
    }

    public static /* synthetic */ void $r8$lambda$feolW5yIFM195Ab6XdKKhqCfSos(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController) {
        if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
            ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).resetPasswordText(true, true);
        }
        ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).doHapticKeyClick();
    }

    public static /* synthetic */ boolean $r8$lambda$oP84JnP5pIKR9G_QPByy_vW6qno(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, int i, KeyEvent keyEvent) {
        keyguardPinBasedInputViewController.getClass();
        if (keyEvent.getAction() == 0) {
            return ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).onKeyDown(i, keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            return ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).onKeyUp(i, keyEvent);
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0] */
    public KeyguardPinBasedInputViewController(KeyguardPinBasedInputView keyguardPinBasedInputView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardPinBasedInputView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardPinBasedInputViewController.$r8$lambda$oP84JnP5pIKR9G_QPByy_vW6qno(KeyguardPinBasedInputViewController.this, i, keyEvent);
            }
        };
        this.mActionButtonTouchListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this, 0);
        this.mLiftToActivateListener = liftToActivateListener;
        this.mFalsingCollector = falsingCollector;
        this.mKeyguardKeyboardInteractor = keyguardKeyboardInteractor;
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
        boolean z = !this.mLockPatternUtils.isPinEnhancedPrivacyEnabled(this.mSelectedUserInteractor.getSelectedUserId(false));
        PasswordTextView passwordTextView = this.mPasswordEntry;
        passwordTextView.mShowPassword = z;
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) this.mView).mButtons) {
            numPadKey.setOnTouchListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this, 1));
        }
        passwordTextView.setOnKeyListener(this.mOnKeyListener);
        passwordTextView.mUserActivityListener = new BaseSecPasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3
            @Override // com.android.keyguard.BaseSecPasswordTextView.UserActivityListener
            public final void onUserActivity() {
                KeyguardPinBasedInputViewController.this.onUserInput();
            }
        };
        View findViewById = ((KeyguardPinBasedInputView) this.mView).findViewById(R.id.delete_button);
        KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 keyguardPinBasedInputViewController$$ExternalSyntheticLambda1 = this.mActionButtonTouchListener;
        findViewById.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
        final int i = 0;
        findViewById.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4
            public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
                switch (i2) {
                    case 0:
                        PasswordTextView passwordTextView2 = keyguardPinBasedInputViewController.mPasswordEntry;
                        if (passwordTextView2.isEnabled()) {
                            passwordTextView2.deleteLastChar();
                            break;
                        }
                        break;
                    default:
                        if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                            keyguardPinBasedInputViewController.verifyPasswordAndUnlock();
                            break;
                        }
                        break;
                }
            }
        });
        findViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                KeyguardPinBasedInputViewController.$r8$lambda$feolW5yIFM195Ab6XdKKhqCfSos(KeyguardPinBasedInputViewController.this);
                return true;
            }
        });
        View findViewById2 = ((KeyguardPinBasedInputView) this.mView).findViewById(R.id.key_enter);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(keyguardPinBasedInputViewController$$ExternalSyntheticLambda1);
            final int i2 = 1;
            findViewById2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4
                public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i2;
                    KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
                    switch (i22) {
                        case 0:
                            PasswordTextView passwordTextView2 = keyguardPinBasedInputViewController.mPasswordEntry;
                            if (passwordTextView2.isEnabled()) {
                                passwordTextView2.deleteLastChar();
                                break;
                            }
                            break;
                        default:
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
        Flags.FEATURE_FLAGS.getClass();
        JavaAdapterKt.collectFlow(passwordTextView, this.mKeyguardKeyboardInteractor.isAnyKeyboardConnected, new Consumer() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = KeyguardPinBasedInputViewController.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                Drawable background = keyguardPinBasedInputViewController.mPasswordEntry.getBackground();
                if (background instanceof StateListDrawable) {
                    Drawable stateDrawable = ((StateListDrawable) background).getStateDrawable(0);
                    if (stateDrawable instanceof GradientDrawable) {
                        GradientDrawable gradientDrawable = (GradientDrawable) stateDrawable;
                        int color = keyguardPinBasedInputViewController.getResources().getColor(R.color.bouncer_password_focus_color);
                        if (booleanValue) {
                            gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 3.0f, keyguardPinBasedInputViewController.getResources().getDisplayMetrics()), color);
                        } else {
                            gradientDrawable.setStroke(0, color);
                        }
                    }
                }
            }
        });
        ViewGroup.LayoutParams layoutParams = passwordTextView.getLayoutParams();
        layoutParams.width = (int) getResources().getDimension(R.dimen.keyguard_pin_field_width);
        layoutParams.height = (int) getResources().getDimension(R.dimen.keyguard_pin_field_height);
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
        final int i = 0;
        final int i2 = 1;
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) this.mView;
        keyguardPinBasedInputView.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 1; i3 <= 9; i3++) {
            arrayList2.add(keyguardPinBasedInputView.mButtons[i3]);
        }
        arrayList2.add(keyguardPinBasedInputView.mDeleteButton);
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
                    int i6 = i;
                    View view2 = view;
                    switch (i6) {
                        case 0:
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
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
                    int i6 = i2;
                    View view2 = view;
                    switch (i6) {
                        case 0:
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            view2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            view2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
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
