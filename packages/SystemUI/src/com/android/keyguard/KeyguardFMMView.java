package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

public class KeyguardFMMView extends KeyguardSecPinBasedInputView {
    public KeyguardFMMView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.fmmEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getWrongPasswordStringId() {
        return R.string.kg_incorrect_pin;
    }

    public KeyguardFMMView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
