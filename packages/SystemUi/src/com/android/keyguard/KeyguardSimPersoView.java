package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSimPersoView extends KeyguardSecPinBasedInputView {
    public KeyguardSimPersoView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.simPersoEntry;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardSecAbsKeyInputView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardSecAbsKeyInputView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public KeyguardSimPersoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
