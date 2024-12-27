package com.android.keyguard;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.keyguard.KeyguardPasswordView;
import com.android.systemui.DejankUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class KeyguardPasswordView extends KeyguardSecAbsKeyInputView {
    public static final int[] DISABLE_STATE_SET = {-16842910};
    public static final int[] ENABLE_STATE_SET = {R.attr.state_enabled};
    public boolean mAlreadyUsingSplitBouncer;
    public KeyguardSecurityContainer$$ExternalSyntheticLambda2 mDisappearAnimationListener;
    public final boolean mIsLockScreenLandscapeEnabled;
    public int mLastDevicePosture;
    public TextView mPasswordEntry;
    public TextViewInputDisabler mPasswordEntryDisabler;

    public KeyguardPasswordView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPasswordOrNone(this.mPasswordEntry.getText());
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPasswordTextViewId() {
        return com.android.systemui.R.id.passwordEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPromptReasonStringRes(int i) {
        if (i != 0) {
            return i != 1 ? i != 3 ? i != 4 ? i != 6 ? i != 9 ? i != 16 ? com.android.systemui.R.string.kg_prompt_reason_timeout_password : com.android.systemui.R.string.kg_prompt_after_update_password : com.android.systemui.R.string.kg_prompt_after_adaptive_auth_lock : com.android.systemui.R.string.kg_prompt_added_security_password : com.android.systemui.R.string.kg_prompt_after_user_lockdown_password : com.android.systemui.R.string.kg_prompt_reason_device_admin : com.android.systemui.R.string.kg_prompt_reason_restart_password;
        }
        return 0;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getResources().getString(R.string.permdesc_bindCarrierMessagingService);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getWrongPasswordStringId() {
        return com.android.systemui.R.string.kg_wrong_password;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!this.mPasswordEntry.isFocused() && isVisibleToUser()) {
            this.mPasswordEntry.requestFocus();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void onDevicePostureChanged(int i) {
        if (this.mLastDevicePosture == i) {
            return;
        }
        this.mLastDevicePosture = i;
        if (this.mIsLockScreenLandscapeEnabled) {
            boolean z = i == 1 && getResources().getConfiguration().orientation == 2 && getResources().getBoolean(com.android.systemui.R.bool.update_bouncer_constraints);
            if (this.mAlreadyUsingSplitBouncer == z) {
                return;
            }
            this.mAlreadyUsingSplitBouncer = z;
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPasswordEntry = (TextView) findViewById(com.android.systemui.R.id.passwordEntry);
        this.mPasswordEntryDisabler = new TextViewInputDisabler(this.mPasswordEntry);
        if (ActivityManager.isRunningInTestHarness()) {
            this.mPasswordEntry.setCursorVisible(false);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.setText("");
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setBackgroundTintList(ColorStateList.valueOf(this.mPasswordEntry.getTextColors().getColorForState(z ? ENABLE_STATE_SET : DISABLE_STATE_SET, 0)));
        this.mPasswordEntry.setCursorVisible(z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntryDisabler.setInputEnabled(z);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public void startAppearAnimation() {
        setAlpha(0.0f);
        animate().alpha(1.0f).setDuration(300L).start();
        setTranslationY(0.0f);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public boolean startDisappearAnimation(Runnable runnable) {
        getWindowInsetsController().controlWindowInsetsAnimation(WindowInsets.Type.ime(), 100L, Interpolators.LINEAR, null, new AnonymousClass1(runnable));
        return true;
    }

    public KeyguardPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAlreadyUsingSplitBouncer = false;
        this.mIsLockScreenLandscapeEnabled = false;
        this.mLastDevicePosture = 0;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardPasswordView$1, reason: invalid class name */
    public final class AnonymousClass1 implements WindowInsetsAnimationControlListener {
        public final /* synthetic */ Runnable val$finishRunnable;

        public AnonymousClass1(Runnable runnable) {
            this.val$finishRunnable = runnable;
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public final void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
            KeyguardPasswordView keyguardPasswordView = KeyguardPasswordView.this;
            Runnable runnable = keyguardPasswordView.mOnFinishImeAnimationRunnable;
            if (runnable != null) {
                runnable.run();
                keyguardPasswordView.mOnFinishImeAnimationRunnable = null;
            }
            this.val$finishRunnable.run();
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public final void onReady(final WindowInsetsAnimationController windowInsetsAnimationController, int i) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPasswordView$1$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardPasswordView.AnonymousClass1 anonymousClass1 = KeyguardPasswordView.AnonymousClass1.this;
                    WindowInsetsAnimationController windowInsetsAnimationController2 = windowInsetsAnimationController;
                    ValueAnimator valueAnimator2 = ofFloat;
                    anonymousClass1.getClass();
                    if (windowInsetsAnimationController2.isCancelled()) {
                        return;
                    }
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    Insets add = Insets.add(windowInsetsAnimationController2.getShownStateInsets(), Insets.of(0, 0, 0, (int) (((-r2.bottom) / 4) * animatedFraction)));
                    KeyguardSecurityContainer$$ExternalSyntheticLambda2 keyguardSecurityContainer$$ExternalSyntheticLambda2 = KeyguardPasswordView.this.mDisappearAnimationListener;
                    if (keyguardSecurityContainer$$ExternalSyntheticLambda2 != null) {
                        keyguardSecurityContainer$$ExternalSyntheticLambda2.f$0.setTranslationY(-r3);
                    }
                    windowInsetsAnimationController2.setInsetsAndAlpha(add, floatValue, animatedFraction);
                    KeyguardPasswordView.this.setAlpha(floatValue);
                }
            });
            ofFloat.addListener(new C00221(windowInsetsAnimationController));
            ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            ofFloat.start();
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.keyguard.KeyguardPasswordView$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00221 extends AnimatorListenerAdapter {
            public final /* synthetic */ WindowInsetsAnimationController val$controller;

            public C00221(WindowInsetsAnimationController windowInsetsAnimationController) {
                this.val$controller = windowInsetsAnimationController;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                final WindowInsetsAnimationController windowInsetsAnimationController = this.val$controller;
                final Runnable runnable = AnonymousClass1.this.val$finishRunnable;
                DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordView$1$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardPasswordView.AnonymousClass1.C00221 c00221 = KeyguardPasswordView.AnonymousClass1.C00221.this;
                        WindowInsetsAnimationController windowInsetsAnimationController2 = windowInsetsAnimationController;
                        Runnable runnable2 = runnable;
                        c00221.getClass();
                        Trace.beginSection("KeyguardPasswordView#onAnimationEnd");
                        windowInsetsAnimationController2.finish(false);
                        KeyguardPasswordView keyguardPasswordView = KeyguardPasswordView.this;
                        Runnable runnable3 = keyguardPasswordView.mOnFinishImeAnimationRunnable;
                        if (runnable3 != null) {
                            runnable3.run();
                            keyguardPasswordView.mOnFinishImeAnimationRunnable = null;
                        }
                        runnable2.run();
                        KeyguardPasswordView.this.mDisappearAnimationListener = null;
                        Trace.endSection();
                    }
                });
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public final void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
        }
    }
}
