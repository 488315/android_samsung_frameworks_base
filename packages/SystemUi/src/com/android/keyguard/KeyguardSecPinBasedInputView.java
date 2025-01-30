package com.android.keyguard;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.samsung.android.tsp.SemTspStateManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (LsRune.SECURITY_DEAD_ZONE && this.mAttached) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("onVisibilityChanged() Visibility : ", i, "KeyguardSecPinBasedInputView");
            if (i == 0) {
                Bundle bundle = new Bundle();
                bundle.putString("deadzone_v2", "3.33%,3.33%,0%");
                SemTspStateManager.setDeadZone(getRootView(), bundle);
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
