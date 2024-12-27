package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSecPINView extends KeyguardPINView {
    public static final /* synthetic */ int $r8$clinit = 0;

    public KeyguardSecPINView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardPINView, com.android.keyguard.KeyguardAbsKeyInputView
    public final int getWrongPasswordStringId() {
        return R.string.kg_incorrect_pin;
    }

    @Override // com.android.keyguard.KeyguardPINView, com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mViews = new View[][]{new View[]{findViewById(R.id.password_entry_box), null, null}, new View[]{findViewById(R.id.key1), findViewById(R.id.key2), findViewById(R.id.key3)}, new View[]{findViewById(R.id.key4), findViewById(R.id.key5), findViewById(R.id.key6)}, new View[]{findViewById(R.id.key7), findViewById(R.id.key8), findViewById(R.id.key9)}, new View[]{findViewById(R.id.delete_button), findViewById(R.id.key0), findViewById(R.id.key_enter)}, new View[]{findViewById(R.id.key_enter_text), null, this.mEcaView}};
        ((LinearLayout) findViewById(R.id.split_touch_top_container)).setOnTouchListener(new KeyguardSecPINView$$ExternalSyntheticLambda0());
        ((FrameLayout) findViewById(R.id.split_touch_bottom_container)).setOnTouchListener(new KeyguardSecPINView$$ExternalSyntheticLambda0());
    }

    public KeyguardSecPINView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardPINView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
    }
}
