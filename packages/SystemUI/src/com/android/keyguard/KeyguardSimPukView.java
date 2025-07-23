package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.util.PluralMessageFormaterKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardSimPukView extends KeyguardSimInputView {
    public KeyguardSimPukView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPasswordTextViewId() {
        return R.id.pukEntry;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public final int getPromptReasonStringRes(int i) {
        return 0;
    }

    public final String getPukPasswordErrorMessage(int i, boolean z, boolean z2) {
        String string;
        if (i == 0) {
            string = getContext().getString(R.string.kg_password_wrong_puk_code_dead);
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(getResources(), z ? R.string.kg_password_default_puk_message : R.string.kg_password_wrong_puk_code, i);
        } else {
            string = getContext().getString(z ? R.string.kg_puk_enter_puk_hint : R.string.kg_password_puk_failed);
        }
        if (z2) {
            string = getResources().getString(R.string.kg_sim_lock_esim_instructions, string);
        }
        KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i, "getPukPasswordErrorMessage: attemptsRemaining=", " displayMessage=", string, "KeyguardSimPukView");
        return string;
    }

    public KeyguardSimPukView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
