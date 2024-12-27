package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class KeyguardSimInputView extends KeyguardSecPinBasedInputView {
    public KeyguardEsimArea disableESimButton;
    public ImageView simImageView;

    public KeyguardSimInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.simImageView = (ImageView) findViewById(R.id.keyguard_sim);
        this.disableESimButton = (KeyguardEsimArea) findViewById(R.id.keyguard_esim_area);
        super.onFinishInflate();
    }
}
