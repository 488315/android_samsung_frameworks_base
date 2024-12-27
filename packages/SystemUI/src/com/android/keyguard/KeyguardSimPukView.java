package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.util.PluralMessageFormaterKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + string);
        return string;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return getContext().getString(android.R.string.permdesc_bindNotificationListenerService);
    }

    public KeyguardSimPukView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
