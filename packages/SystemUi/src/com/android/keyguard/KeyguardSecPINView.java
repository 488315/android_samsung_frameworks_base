package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        final int i = 0;
        ((LinearLayout) findViewById(R.id.split_touch_top_container)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardSecPINView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                switch (i) {
                    case 0:
                        int i2 = KeyguardSecPINView.$r8$clinit;
                        break;
                    default:
                        int i3 = KeyguardSecPINView.$r8$clinit;
                        break;
                }
                return true;
            }
        });
        final int i2 = 1;
        ((FrameLayout) findViewById(R.id.split_touch_bottom_container)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardSecPINView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                switch (i2) {
                    case 0:
                        int i22 = KeyguardSecPINView.$r8$clinit;
                        break;
                    default:
                        int i3 = KeyguardSecPINView.$r8$clinit;
                        break;
                }
                return true;
            }
        });
    }

    public KeyguardSecPINView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardPINView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
    }
}
