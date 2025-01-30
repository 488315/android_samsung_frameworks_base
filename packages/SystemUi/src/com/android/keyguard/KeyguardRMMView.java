package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardRMMView extends KeyguardSecPinBasedInputView {
    public KeyguardRMMView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.rmmEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getWrongPasswordStringId() {
        return R.string.kg_incorrect_pin;
    }

    public KeyguardRMMView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
