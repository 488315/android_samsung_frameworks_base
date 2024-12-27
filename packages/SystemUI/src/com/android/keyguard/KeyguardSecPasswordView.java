package com.android.keyguard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSecPasswordView extends KeyguardPasswordView {
    public ViewGroup mContainer;
    public final int mDisappearYTranslation;
    public final Interpolator mFastOutLinearInInterpolator;
    public final Interpolator mLinearOutSlowInInterpolator;
    public ViewGroup mPasswordEntryBoxLayout;
    public boolean mTouchEnabled;

    public KeyguardSecPasswordView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputView
    public final void disableTouch() {
        this.mTouchEnabled = false;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputView
    public final void enableTouch() {
        this.mTouchEnabled = true;
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView
    public final LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPasswordOrNone(this.mPasswordEntry.getText());
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.passwordEntry;
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView
    public final int getWrongPasswordStringId() {
        return R.string.kg_incorrect_password;
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = (ViewGroup) findViewById(R.id.container);
        this.mPasswordEntryBoxLayout = (ViewGroup) findViewById(R.id.password_entry_box);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchEnabled) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        Log.d("KeyguardSecPasswordView", "Touch event intercepted");
        return true;
    }

    @Override // com.android.keyguard.KeyguardPasswordView, android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView
    public final void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.setText("");
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        ViewGroup viewGroup = this.mContainer;
        if (viewGroup != null && this.mPasswordEntryBoxLayout != null) {
            viewGroup.setVisibility(z ? 0 : 4);
            this.mPasswordEntryBoxLayout.setVisibility(z ? 0 : 4);
        }
        if (z) {
            this.mTouchEnabled = true;
        }
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
        setAlpha(0.0f);
        setTranslationY(0.0f);
        animate().alpha(1.0f).withLayer().setDuration(300L).setInterpolator(this.mLinearOutSlowInInterpolator);
    }

    @Override // com.android.keyguard.KeyguardPasswordView, com.android.keyguard.KeyguardInputView
    public final boolean startDisappearAnimation(Runnable runnable) {
        animate().alpha(0.0f).translationY(this.mDisappearYTranslation).setInterpolator(this.mFastOutLinearInInterpolator).setDuration(100L).withEndAction(runnable);
        return true;
    }

    public KeyguardSecPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchEnabled = true;
        this.mDisappearYTranslation = getResources().getDimensionPixelSize(R.dimen.disappear_y_translation);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);
        this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_linear_in);
    }
}
