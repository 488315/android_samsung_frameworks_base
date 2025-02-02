package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardKnoxGuardView extends KeyguardSecAbsKeyInputView {
    public EditText mPinEditText;

    public KeyguardKnoxGuardView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final LockscreenCredential getEnteredCredential() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.keyguard_knox_guard_pin_view;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPromptReasonStringRes(int i) {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getWrongPasswordStringId() {
        return R.string.kg_remote_lock_incorrect_pin;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPinEditText = (EditText) findViewById(R.id.keyguard_knox_guard_pin_view);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void resetPasswordText(boolean z, boolean z2) {
        this.mPinEditText.setText("");
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryEnabled(boolean z) {
        this.mPinEditText.setEnabled(z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final void setPasswordEntryInputEnabled(boolean z) {
        this.mPinEditText.setClickable(z);
    }

    public KeyguardKnoxGuardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
