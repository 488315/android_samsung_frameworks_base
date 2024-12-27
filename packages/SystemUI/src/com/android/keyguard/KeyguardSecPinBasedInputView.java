package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.samsung.android.tsp.SemTspStateManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class KeyguardSecPinBasedInputView extends KeyguardPinBasedInputView {
    public boolean mAttached;
    public ViewGroup mContainer;
    public ViewGroup mPasswordEntryBoxLayout;
    public boolean mTouchEnabled;

    public KeyguardSecPinBasedInputView(Context context) {
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

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public final LockscreenCredential getEnteredCredential() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        return passwordTextView instanceof SecPasswordTextView ? LockscreenCredential.createPinOrNone(((SecPasswordTextView) passwordTextView).mText) : LockscreenCredential.createPinOrNone(passwordTextView.getText());
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (LsRune.SECURITY_DEAD_ZONE) {
            this.mAttached = true;
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (LsRune.SECURITY_DEAD_ZONE) {
            this.mAttached = false;
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = (ViewGroup) findViewById(R.id.container);
        this.mPasswordEntryBoxLayout = (ViewGroup) findViewById(R.id.password_entry_box);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchEnabled) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        Log.d("KeyguardSecPinBasedInputView", "Touch event intercepted");
        return true;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardSecAbsKeyInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(i)) {
            this.mOkButton.performClick();
            return true;
        }
        if (i == 67) {
            this.mDeleteButton.performClick();
            return true;
        }
        if (i >= 7 && i <= 16) {
            performNumberClick(i - 7);
            return true;
        }
        if (i < 144 || i > 153) {
            return super.onKeyDown(i, keyEvent);
        }
        performNumberClick(i - 144);
        return true;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (LsRune.SECURITY_DEAD_ZONE && this.mAttached) {
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "onVisibilityChanged() Visibility : ", "KeyguardSecPinBasedInputView");
            if (i == 0) {
                SemTspStateManager.setDeadZone(getRootView(), AbsAdapter$1$$ExternalSyntheticOutline0.m("deadzone_v2", "3.33%,3.33%,0%"));
            } else {
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    return;
                }
                SemTspStateManager.clearDeadZone(getRootView());
            }
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryEnabled(boolean z) {
        super.setPasswordEntryEnabled(z);
        ViewGroup viewGroup = this.mContainer;
        if (viewGroup != null && this.mPasswordEntryBoxLayout != null) {
            viewGroup.setVisibility(z ? 0 : 4);
            this.mPasswordEntryBoxLayout.setVisibility(z ? 0 : 4);
        }
        if (z) {
            this.mTouchEnabled = true;
        }
    }

    public KeyguardSecPinBasedInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchEnabled = true;
    }
}
