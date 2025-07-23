package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
