package com.android.keyguard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.Flags;
import com.android.systemui.R;

public abstract class KeyguardPinBasedInputView extends KeyguardSecAbsKeyInputView {
    public final NumPadKey[] mButtons;
    public NumPadButton mDeleteButton;
    public NumPadButton mOkButton;
    public PasswordTextView mPasswordEntry;

    public KeyguardPinBasedInputView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPinOrNone(this.mPasswordEntry.getText());
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPromptReasonStringRes(int i) {
        if (i != 0) {
            return i != 1 ? i != 3 ? i != 4 ? i != 6 ? i != 9 ? i != 16 ? R.string.kg_prompt_reason_timeout_pin : R.string.kg_prompt_after_update_pin : R.string.kg_prompt_after_adaptive_auth_lock : R.string.kg_prompt_added_security_pin : R.string.kg_prompt_after_user_lockdown_pin : R.string.kg_prompt_reason_device_admin : R.string.kg_prompt_reason_restart_pin;
        }
        return 0;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getContext().getString(android.R.string.permdesc_bindConditionProviderService);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        PasswordTextView passwordTextView = (PasswordTextView) findViewById(getPasswordTextViewId());
        this.mPasswordEntry = passwordTextView;
        passwordTextView.setSelected(true);
        Flags.FEATURE_FLAGS.getClass();
        this.mOkButton = (NumPadButton) findViewById(R.id.key_enter);
        NumPadButton numPadButton = (NumPadButton) findViewById(R.id.delete_button);
        this.mDeleteButton = numPadButton;
        numPadButton.setVisibility(0);
        this.mButtons[0] = (NumPadKey) findViewById(R.id.key0);
        this.mButtons[1] = (NumPadKey) findViewById(R.id.key1);
        this.mButtons[2] = (NumPadKey) findViewById(R.id.key2);
        this.mButtons[3] = (NumPadKey) findViewById(R.id.key3);
        this.mButtons[4] = (NumPadKey) findViewById(R.id.key4);
        this.mButtons[5] = (NumPadKey) findViewById(R.id.key5);
        this.mButtons[6] = (NumPadKey) findViewById(R.id.key6);
        this.mButtons[7] = (NumPadKey) findViewById(R.id.key7);
        this.mButtons[8] = (NumPadKey) findViewById(R.id.key8);
        this.mButtons[9] = (NumPadKey) findViewById(R.id.key9);
        this.mPasswordEntry.requestFocus();
        super.onFinishInflate();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 67) {
            this.mDeleteButton.performClick();
            return true;
        }
        if (i >= 7 && i <= 16) {
            performNumberClick(i - 7);
            return true;
        }
        if (i < 144 || i > 153) {
            super.onKeyDown(i, keyEvent);
            return false;
        }
        performNumberClick(i - 144);
        return true;
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    public final void performNumberClick(int i) {
        if (i < 0 || i > 9) {
            return;
        }
        this.mButtons[i].performClick();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.reset(z, z2);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (AccessibilityManager.getInstance(getContext()).isTouchExplorationEnabled() || !z || this.mPasswordEntry.hasFocus()) {
            return;
        }
        this.mPasswordEntry.requestFocus();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (z) {
            this.mPasswordEntry.requestFocus();
        }
    }

    public KeyguardPinBasedInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButtons = new NumPadKey[10];
    }
}
