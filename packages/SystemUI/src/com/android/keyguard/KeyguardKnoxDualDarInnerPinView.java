package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

public class KeyguardKnoxDualDarInnerPinView extends KeyguardSecPINView {
    public KeyguardKnoxDualDarInnerPinView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardPINView, com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.dualdar_inner_pinEntry;
    }

    public KeyguardKnoxDualDarInnerPinView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
